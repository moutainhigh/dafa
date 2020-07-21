package pers.dafacloud.server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dafacloud.bean.HttpConfigHandle;
import pers.dafacloud.entity.ApiManage;
import pers.dafacloud.entity.TestApiResult;
import pers.dafacloud.enums.TestApiResultEnum;
import pers.dafacloud.utils.Response;
import pers.utils.dafaCloud.DafaCloudLogin;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
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
        String httpHost = host.contains("http") ?
                host : String.format("%s%s", "http://", host.replace("/", "").replace("?", ""));

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
        httpConfigHandle.setXToken(cookie);
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
                List<String> deValueList = new ArrayList<>();
                for (int i = 0; i < dependentApiJa.size(); i++) { //多个依赖接口
                    JSONArray dependentApiJa0 = dependentApiJa.getJSONArray(i);
                    int deId = dependentApiJa0.getInt(0);
                    String ruleString = dependentApiJa0.getString(1);
                    ApiManage apiManageDependent = apiManageServer.getApiById(deId);
                    deResult = httpConfigHandle
                            .setRequestMethod(apiManageDependent.getMethod())
                            .setRequestPath(apiManageDependent.getPath())
                            .setRequestParameters(apiManageDependent.getRequestParameters())
                            .doRequest();

                    if (JSONObject.fromObject(deResult).getInt("code") != 1)
                        throw new Exception("依赖接口异常");

                    if (StringUtils.isNotEmpty(ruleString)) {//有提取规则
                        //提取依赖数据
                        // GET  : listName#stateName1,stateValue1;stateName2,stateValue2#id,name
                        // GET  : orderList#id,name
                        // POST : 1 getName
                        //{"msg":"数据正确","code":1,"data":{"token":"xxxx"}}
                        //{"msg":"获取成功","code":1,"data":[{"id":"1403"}]}
                        //{"msg":"数据正确","code":1,"data":{"rows":[{"a":"1"},{"a":"1"}]}}
                        String[] getRule = ruleString.split("#");

                        JSONObject deResultJo = JSONObject.fromObject(deResult);
                        Object data = deResultJo.get("data");
                        if (getRule.length == 3) {
                            String ruleListName = getRule[0];//listName
                            JSONArray deResultList = getJSONArray(data, ruleListName);
                            String[] ruleFilterArray = getRule[1].split(";"); //stateName1,stateValue1;stateName2,stateValue2
                            for (int j = 0; j < deResultList.size(); j++) {//遍历请求结果
                                boolean isNeed = true;
                                JSONObject listObj = deResultList.getJSONObject(j);
                                for (String s : ruleFilterArray) {
                                    String[] ruleFilterOne = s.split(",");//stateName1,stateValue1
                                    if (!ruleFilterOne[1].equals(listObj.getString(ruleFilterOne[0]))) {
                                        isNeed = false;//这条数据不是需要
                                    }
                                }
                                if (isNeed) {//满足条件的数据才提取
                                    String[] need = getRule[2].split(",");//id,name
                                    for (String s0 : need) {
                                        deValue = listObj.getString(s0);
                                        if (StringUtils.isEmpty(deValue)) {
                                            logger.info("deValue is null " + deValue + ",提取字段" + s0);
                                            continue;
                                        }
                                        deValueList.add(deValue);
                                    }
                                }
                            }
                        } else if (getRule.length == 2) {
                            JSONArray deResultoJa = getJSONArray(data, getRule[0]);
                            String[] need0 = getRule[1].split(",");
                            JSONObject jsonObject0 = deResultoJa.getJSONObject(0);
                            for (String s : need0) {
                                deValue = jsonObject0.getString(s);//orderList#id
                                if (StringUtils.isEmpty(deValue)) {
                                    logger.info("deValue is null " + deValue);
                                    continue;
                                }
                                deValueList.add(deValue);
                            }
                        } else if (getRule.length == 1) {
                            deValue = deResultJo.getJSONObject("data").getString(getRule[0]);
                            deValueList.add(deValue);
                        }

                        sb.append(returnResultHandle(deValueList.isEmpty() ? deResult : deValueList.toString(), httpConfigHandle.getRequestPath(), httpConfigHandle.getRequestParameters()))
                                .append("\n");
                        //替换参数
                        if (!deValueList.isEmpty()) {
                            for (int j = 0; j < deValueList.size(); j++) {
                                apiManage.setRequestParameters(apiManage.getRequestParameters().replace(String.format("{data%s}", j + 1), deValueList.get(j)));
                            }
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

    private JSONArray getJSONArray(Object data, String rule) throws Exception {
        JSONArray deResultJa;
        if (data instanceof JSONArray) {
            //{"code":1,"msg":"获取成功","data":[{"id":"1403"}]}
            deResultJa = JSONArray.fromObject(data);
        } else {
            deResultJa = JSONObject.fromObject(data).getJSONArray(rule);
        }
        if (deResultJa.size() == 0) {
            throw new Exception("依赖接口List空");//有提取规则却提取空
        }
        return deResultJa;
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

    public Response apiManageBatchTest(String hostCms, String hostFront, String frontUsername) {
        if (StringUtils.isEmpty(hostCms))
            return Response.error("后台域名不能为空");
        if (StringUtils.isEmpty(hostFront))
            return Response.error("前台域名不能为空");
        //if (StringUtils.isEmpty(testBatch))
        //    return Response.error("批量执行不能缺少执行批次，执行批次用来统计批量运行测试结果");
        //if (StringUtils.isEmpty(groupsApi))
        //    return Response.error("缺少参数用例分组");
        //if (StringUtils.isEmpty(owner))
        //    return Response.error("缺少参数用例owner");

        String hostCms0 = hostCms.contains("http") ? hostCms : String.format("%s%s", "http://",
                hostCms.replace("/", "").replace("?", ""));

        String hostFront0 = hostFront.contains("http") ? hostFront : String.format("%s%s", "http://",
                hostFront.replace("/", "").replace("?", ""));

        //logger.info("groupsApi:" + groupsApi);

        List<ApiManage> apiManages = apiManageServer.getBatchTestApiList("sys", "duke");

        if (apiManages.size() == 0)
            return Response.fail("获取执行用例数：0");

        logger.info("批量执行用例数量：" + apiManages.size());
        executes.execute(() -> {
            try {
                task(hostCms0, hostFront0, frontUsername, apiManages);
            } catch (Exception e) {
                logger.info(e.toString());
                e.printStackTrace();
            }
        });
        return Response.success("用例执行中,批量执行用例数量：" + apiManages.size());
    }

    /**
     * 批量运行task
     */
    private void task(String hostCms, String hostFront, String frontUsername, List<ApiManage> apiManages) throws Exception {
        String testBatch = RandomStringUtils.randomAlphanumeric(10);
//--------------------------------------front HttpConfigHandle----------------------------------------------------------
        HttpConfigHandle httpConfigHandleFront = HttpConfigHandle.custom();
        httpConfigHandleFront.setHttpConfig(HttpConfig.custom())
                .setHttpHost(hostFront)
                .setFrontUsername(frontUsername);
//--------------------------------------cms HttpConfigHandle----------------------------------------------------------
        HttpConfigHandle httpConfigHandleCms = HttpConfigHandle.custom();
        httpConfigHandleCms
                .setHttpConfig(HttpConfig.custom())
                .setHttpHost(hostCms);
//------------------------------------------------------------------------------------------------
        for (int i = 0; i < apiManages.size(); i++) {
            ApiManage apiManage = apiManages.get(i);
            if (apiManage.getCmsFront() == 1) {
                taskRequest(apiManage, httpConfigHandleFront, testBatch);
            } else if (apiManage.getCmsFront() == 2) {
                taskRequest(apiManage, httpConfigHandleCms, testBatch);
            }
            if (apiManage.getPath().contains("rechargeFrontPaymentRecord") || apiManage.getPath().contains("saveFrontWithdrawRecord")) {//充值接口
                Thread.sleep(6000);
            }
            if (httpConfigHandleFront.isStop() || httpConfigHandleCms.isStop()) {
                break;
            }
            if (apiManage.getPath().endsWith("users/login")) {
                httpConfigHandleCms.setFrontUsername(httpConfigHandleFront.getFrontUsername());
            }
        }
    }

    public boolean loginFront(HttpConfigHandle httpConfigHandleFront, String host, String testBatch, String username) {
        JSONObject passwordJson = DafaCloudLogin.getPassword(username, "123qwe");
        String body = UrlBuilder.custom().addBuilder("userName", username)
                .addBuilder("password", passwordJson.getString("password"))
                .addBuilder("random", passwordJson.getString("random")).fullBody();
        httpConfigHandleFront.setRequestPath("/v1/users/login");
        httpConfigHandleFront.setRequestMethod(2);
        httpConfigHandleFront.getHttpConfig().body(body);
        String result = httpConfigHandleFront.doRequest();
        TestApiResult testApiResult = new TestApiResult();
        testApiResult.setHost(host);
        testApiResult.setApiName("登录前台");
        testApiResult.setApiPath("/v1/users/login");
        testApiResult.setApiMethod(2);
        testApiResult.setCmsFront(1);
        testApiResult.setTestExecutor("sys");
        testApiResult.setTestBatch(testBatch);
        testApiResult.setTestResult(result);
        if (!result.contains("成功")) {
            logger.info("登录失败");
            testApiResult.setIsPass(TestApiResultEnum.FAIL.getCode());
            testApiResultServer.addTestApiResult(testApiResult);
            return false;
        } else {
            httpConfigHandleFront.setXToken(JSONObject.fromObject(result).getJSONObject("data").getString("token"));
            testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
            testApiResultServer.addTestApiResult(testApiResult);
            return true;
        }

    }


}
