package pers.dafacloud.server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dafacloud.bean.HttpConfigHandle;
import pers.dafacloud.enums.TestApiResultEnum;
import pers.dafacloud.model.ApiManage;
import pers.dafacloud.model.TestApiResult;
import pers.dafacloud.utils.Response;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TestRequestApiServer0 {
    private static ExecutorService executes = Executors.newFixedThreadPool(6);
    private static Logger logger = LoggerFactory.getLogger(TestRequestApiServer.class);

    @Autowired
    TestApiResultServer testApiResultServer;

    @Autowired
    ApiManageServer apiManageServer;

    /**
     * 单用例测试
     */
    public Response testApiOne(int id, String host, String cookie) {
        String httpHost = host.contains("http") ? host : String.format("%s%s", "http://",
                host.replace("/", "").replace("?", ""));

        ApiManage apiManage = apiManageServer.getApiById(id);
        if (StringUtils.isEmpty(cookie) && !apiManage.getPath().endsWith("login")) {
            return Response.fail("请设置cookie");
        }
        HttpConfigHandle httpConfigHandle = HttpConfigHandle.custom();
        //设置HttpConfig，httpHost，cookie
        httpConfigHandle
                .setHttpConfig(HttpConfig.custom())
                .setHttpHost(httpHost)
                .setCookie(cookie);
        return taskRequest(apiManage, httpConfigHandle);
    }

    /**
     * 单用例测试
     */
    private Response taskRequest(ApiManage apiManage, HttpConfigHandle httpConfigHandle) {
        return taskRequest(apiManage, httpConfigHandle, "");
    }

    /**
     * 批量用例测试
     */
    private Response taskRequest(ApiManage apiManage, HttpConfigHandle httpConfigHandle, String testBatch) {
        TestApiResult testApiResult = new TestApiResult();
        testApiResult.setHost(httpConfigHandle.getHttpHost());
        testApiResult.setApiName(apiManage.getApiName());
        testApiResult.setApiPath(apiManage.getPath());
        testApiResult.setApiMethod(apiManage.getMethod());
        testApiResult.setCmsFront(apiManage.getCmsFront());
        testApiResult.setTestExecutor(apiManage.getOwner());
        testApiResult.setTestBatch(testBatch);

        try {
            //重新设置header
            httpConfigHandle.setHeader(apiManage.getRequestHeader());
        } catch (Exception e) {
            testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            testApiResult.setTestResult("设置header异常：" + e.getMessage());
            testApiResultServer.addTestApiResult(testApiResult);
            return Response.fail("设置header异常：" + e.getMessage());
        }

        //调用依赖接口
        String deValue = "";
        String deResult = "";
        try {
            if (StringUtils.isNotEmpty(apiManage.getDependentApi())) {
                JSONArray dependentApiJa = JSONArray.fromObject(apiManage.getDependentApi());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < dependentApiJa.size(); i++) { //多个依赖接口
                    JSONArray dependentApiJa0 = dependentApiJa.getJSONArray(i);//dependentApiJa 0/id 1/rule
                    int deId = dependentApiJa0.getInt(0);
                    ApiManage apiManageDependent = apiManageServer.getApiById(deId);
                    deResult = httpConfigHandle
                            .setRequestMethod(apiManageDependent.getMethod())
                            .setRequestPath(apiManageDependent.getPath())
                            .setRequestParameters(apiManageDependent.getRequestParameters())
                            .doRequest();

                    if (JSONObject.fromObject(deResult).getInt("code") != 1)
                        throw new Exception("依赖接口异常");

                    if (StringUtils.isNotEmpty(dependentApiJa0.getString(1))) {//有提取规则
                        //提取依赖数据
                        // GET  : listName,1 stateName,2 stateValue,3 id （orderList,betNum,双,id）
                        // GET  : orderList,id
                        // POST : 1 getName
                        String[] getRule = dependentApiJa0.getString(1).split(",");

                        JSONObject deResultoJo = JSONObject.fromObject(deResult);
                        int ruleLength = getRule.length;
                        if (ruleLength == 4 || ruleLength == 2) { //GET : 取 list
                            JSONArray deResultoJa = deResultoJo.getJSONObject("data").getJSONArray(getRule[0]);
                            if (deResultoJa.size() == 0) {
                                throw new Exception("依赖接口List空");//有提取规则却提取空
                            }
                            if (ruleLength == 4) {
                                for (int j = 0; j < deResultoJa.size(); j++) {
                                    JSONObject liistJo = deResultoJa.getJSONObject(j);
                                    if (getRule[2].equals(liistJo.getString(getRule[1]))) {
                                        deValue = liistJo.getString(getRule[3]);
                                        break;
                                    }
                                }
                            } else {
                                deValue = deResultoJa.getJSONObject(0).getString(getRule[1]);
                            }
                        } else if (ruleLength == 1) {// POST : 取 code
                            deValue = deResultoJo.getJSONObject("data").getString(getRule[0]);
                        }
                        sb.append(returnResultHandle(StringUtils.isEmpty(deValue) ? deResult : deValue, httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters()))
                                .append("\n");
                        //替换参数
                        if (StringUtils.isNotEmpty(deValue)) {
                            apiManage.setRequestParameters(apiManage.getRequestParameters().replace(String.format("{data%s}", i + 1), deValue));
                        } else {
                            throw new Exception("依赖提取结果空");//有提取规则却提取空
                        }
                    }//没有读取规则
                }
                testApiResult.setDependentResult1(sb.toString());
            }
        } catch (Exception e) {
            if (e.getMessage().contains("空"))
                testApiResult.setIsPass(TestApiResultEnum.DE_NO_DATA.getCode());
            else
                testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
            testApiResult.setDependentResult1(returnResultHandle(StringUtils.isEmpty(deValue) ? deResult : deValue, httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters(), e.getMessage()));
            testApiResultServer.addTestApiResult(testApiResult);
            return Response.fail("依赖接口错误：" + e.getMessage());
        }

        String result = "";
        try {
            result = httpConfigHandle
                    .setRequestMethod(apiManage.getMethod())
                    .setRequestPath(apiManage.getPath())
                    .setRequestParameters(apiManage.getRequestParameters())
                    .doRequest();
            //响应结果断言
            int AssertType = Integer.parseInt(apiManage.getAssertType());
            if (AssertType == 1) { //code
                if (JSONObject.fromObject(result).getInt("code") == Integer.parseInt(apiManage.getAssertContent())) {
                    testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
                } else {
                    testApiResult.setIsPass(TestApiResultEnum.FAIL.getCode());
                }
                testApiResult.setAssertContent("响应code=" + apiManage.getAssertContent());
            } else if (AssertType == 2) { //内容包含
                if (result.contains(apiManage.getAssertContent())) {
                    testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
                } else {
                    testApiResult.setIsPass(TestApiResultEnum.FAIL.getCode());
                }
                testApiResult.setAssertContent("响应内容包含" + apiManage.getAssertContent());
            } else { //默认
                if (JSONObject.fromObject(result).getInt("code") == 1) {
                    testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
                } else {
                    testApiResult.setIsPass(TestApiResultEnum.FAIL.getCode());
                }
                testApiResult.setAssertContent("响应code = 1 ");
            }
            testApiResult.setTestResult(returnResultHandle(result, httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters()));
            testApiResultServer.addTestApiResult(testApiResult);
            return com.alibaba.fastjson.JSONObject.parseObject(result, Response.class);
        } catch (Exception e) {
            testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            testApiResult.setTestResult(returnResultHandle(result, httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters(), e.getMessage()));
            testApiResultServer.addTestApiResult(testApiResult);
            return Response.fail("请求异常：" + e.getMessage());
        }
    }

    /**
     * 请求返回数据封装 返回结果+请求路径+请求参数
     *
     * @param result              测试结果
     * @param path                请求路径
     * @param reqParametersString 请求参数
     */
    private String returnResultHandle(String result, String path, String reqParametersString) {
        return String.format("【%s】\n【%s】\n【%s】", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString);
    }

    /**
     * 重载
     */
    private String returnResultHandle(String result, String path, String reqParametersString, String e) {
        return String.format("【%s】\n【%s】\n【%s】\n【%s】", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString, e);
    }

    public Response apiManageBatchTest(String host, String cookie, String testBatch, String groupsApi, String owner) throws Exception {
        if (StringUtils.isEmpty(host))
            return Response.error("host不能为空");
        if (StringUtils.isEmpty(cookie))
            return Response.error("cookie不能为空");
        if (StringUtils.isEmpty(testBatch))
            return Response.error("批量执行不能缺少执行批次，执行批次用来统计批量运行测试结果");
        if (StringUtils.isEmpty(groupsApi))
            return Response.error("缺少参数用例分组");
        if (StringUtils.isEmpty(owner))
            return Response.error("缺少参数用例owner");

        String host0 = host.contains("http") ? host : String.format("%s%s", "http://",
                host.replace("/", "").replace("?", ""));

        logger.info("groupsApi:" + groupsApi);

        List<ApiManage> apiManages = apiManageServer.getBatchTestApiList(groupsApi, owner);

        if (apiManages.size() == 0)
            return Response.fail("获取执行用例数：0");

        //logger.info("批量执行用例数量：" + apiManages.size());

        executes.execute(() -> {
            try {
                task(host0, cookie, testBatch, apiManages);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return Response.success("用例执行中,批量执行用例数量：" + apiManages.size());
    }

    /**
     * 批量运行task
     */
    private void task(String httpHost, String cookie, String testBatch, List<ApiManage> apiManages) throws Exception {
        HttpConfigHandle httpConfigHandle = HttpConfigHandle.custom();
        //设置HttpConfig，httpHost，cookie
        httpConfigHandle
                .setHttpConfig(HttpConfig.custom())
                .setHttpHost(httpHost)
                .setCookie(cookie);
        for (int i = 0; i < apiManages.size(); i++) {
            ApiManage apiManage = apiManages.get(i);
            //apiManageTask(apiManage, url, cookie, testBatch, httpConfig);
            taskRequest(apiManage, httpConfigHandle, testBatch);
        }
    }

}
