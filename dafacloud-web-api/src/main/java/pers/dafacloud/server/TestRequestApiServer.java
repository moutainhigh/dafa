package pers.dafacloud.server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dafacloud.enums.TestApiResultEnum;
import pers.dafacloud.model.ApiContent;
import pers.dafacloud.model.ApiManage;
import pers.dafacloud.model.TestApiResult;
import pers.dafacloud.utils.Response;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.urlUtils.UrlBuilder;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TestRequestApiServer {

    private static Logger logger = LoggerFactory.getLogger(TestRequestApiServer.class);
    private static ExecutorService executes = Executors.newFixedThreadPool(6);

    @Autowired
    TestApiResultServer testApiResultServer;

    @Autowired
    ApiContentServer apiContentServer;

    @Autowired
    ApiManageServer apiManageServer;


    /**
     * 单用例测试
     */
    public Response testApiOne(int id, String host, String cookie) throws Exception {
        String url = host.contains("http") ? host : String.format("%s%s", "http://",
                host.replace("/", "").replace("?", ""));

        HttpConfig httpConfig = HttpConfig.custom();


        TestApiResult testApiResult = new TestApiResult();
        ApiManage apiManage = apiManageServer.getApiById(id);

        apiManage.setLoginReq(apiManage.getPath().endsWith("login"));

        // ------------------------------请求httpclient----------------------------------------------
        HttpCookies httpCookies = HttpCookies.custom();
        //if (!apiManage.isLoginReq()) {//不是登录请求才设置cookie
        if (StringUtils.isNotEmpty(cookie)) {
            //return Response.fail("非登录请求，且没有设置cookie");
            BasicClientCookie cookies = new BasicClientCookie("JSESSIONID", cookie);
            cookies.setDomain(new URL(url).getHost());//设置范围
            cookies.setVersion(0);
            cookies.setPath("/");
            httpCookies.getCookieStore().addCookie(cookies);
        }
        httpConfig.context(httpCookies.getContext());

        testApiResult.setHost(url);
        testApiResult.setApiName(apiManage.getApiName());
        testApiResult.setApiPath(apiManage.getPath());
        testApiResult.setApiMethod(apiManage.getMethod());
        testApiResult.setCmsFront(apiManage.getCmsFront());
        testApiResult.setTestExecutor(apiManage.getOwner());

        // ---------------------------------依赖接口请求--------------------------------------------
        String dependentApi = apiManage.getDependentApi();
        String requestParametersNew = "";
        if (StringUtils.isNotEmpty(dependentApi)) {// 依赖不为空
            try {
                requestParametersNew = deRequestHandle(url, cookie, httpConfig, testApiResult, dependentApi, apiManage.getRequestParameters());
            } catch (Exception e) {
                if (StringUtils.isEmpty(testApiResult.getIsPass()))
                    testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                testApiResultServer.addTestApiResult(testApiResult);
                return Response.fail("依赖接口请求异常：" + e.getMessage());
            }
        }
        // --------------------------------正式接口请求----------------------------------------------
        if (StringUtils.isNotEmpty(requestParametersNew))
            apiManage.setRequestParameters(requestParametersNew);

        try {
            String result = requestHandle(apiManage, url, cookie, httpConfig, testApiResult, false);//正式请求返回

            if (JSONObject.fromObject(result).getInt("code") == 1) {
                testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
            } else {
                testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            }
            try {
                return   com.alibaba.fastjson.JSONObject.parseObject(result, Response.class);
            }catch (Exception e){
                throw new RuntimeException(result);
            }

            //testApiResultServer.addTestApiResult(testApiResult);
            //com.alibaba.fastjson.JSONObject jo = com.alibaba.fastjson.JSONObject.parseObject(result);
            //return Response.returnData(jo.getString("msg"), jo.getInteger("code"), StringUtils.isEmpty(jo.get("data").toString()) ? null : jo.get("data"));
        } catch (Exception e) {
            testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            testApiResultServer.addTestApiResult(testApiResult);
            return Response.fail("请求异常：" + e.getMessage());
        }
    }

    /**
     * 批量运行task
     */
    public void task(String host, String cookie, String testBatch, List<ApiManage> apiManages) throws Exception {
        String url = host.contains("http") ? host : String.format("%s%s", "http://",
                host.replace("/", "").replace("?", ""));

        HttpConfig httpConfig = HttpConfig.custom();
        HttpCookies httpCookies = HttpCookies.custom();
        BasicClientCookie cookies = new BasicClientCookie("JSESSIONID", cookie);
        cookies.setDomain(new URL(url).getHost());//设置范围
        cookies.setVersion(0);
        cookies.setPath("/");
        httpCookies.getCookieStore().addCookie(cookies);

        httpConfig.context(httpCookies.getContext());

        for (int i = 0; i < apiManages.size(); i++) {
            ApiManage apiManage = apiManages.get(i);
            apiManageTask(apiManage, url, cookie, testBatch, httpConfig);
        }
    }

    private Response apiManageTask(ApiManage apiManage, String url, String cookie, String testBatch, HttpConfig httpConfig) {
        TestApiResult testApiResult = new TestApiResult();

        apiManage.setLoginReq(apiManage.getPath().endsWith("login"));

        testApiResult.setHost(url);
        testApiResult.setApiName(apiManage.getApiName());
        testApiResult.setApiPath(apiManage.getPath());
        testApiResult.setApiMethod(apiManage.getMethod());
        testApiResult.setCmsFront(apiManage.getCmsFront());
        testApiResult.setTestExecutor(apiManage.getOwner());
        testApiResult.setTestBatch(testBatch);

        // ---------------------------------依赖接口请求--------------------------------------------
        String dependentApi = apiManage.getDependentApi();
        String requestParametersNew = "";
        if (StringUtils.isNotEmpty(dependentApi)) {// 依赖不为空
            try {
                requestParametersNew = deRequestHandle(url, cookie, httpConfig, testApiResult, dependentApi, apiManage.getRequestParameters());
            } catch (Exception e) {
                if (StringUtils.isEmpty(testApiResult.getIsPass()))
                    testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                testApiResultServer.addTestApiResult(testApiResult);
                return Response.fail("依赖接口请求异常：" + e.getMessage());
            }
        }
        // --------------------------------正式接口请求----------------------------------------------
        if (StringUtils.isNotEmpty(requestParametersNew))
            apiManage.setRequestParameters(requestParametersNew);

        try {
            String result = requestHandle(apiManage, url, cookie, httpConfig, testApiResult, false);//正式请求返回

            if (JSONObject.fromObject(result).getInt("code") == 1) {
                testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
            } else {
                testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            }
            testApiResultServer.addTestApiResult(testApiResult);
            JSONObject jo = JSONObject.fromObject(result);
            return Response.returnData(jo.getString("msg"), jo.getInt("code"), jo.getJSONObject("data").isEmpty() ? null : jo.getJSONObject("data"));
        } catch (Exception e) {
            testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
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
        String s = String.format("%s\n%s\n%s", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString);
        return s;
    }

    /**
     * 处理请求头
     *
     * @param cookie        header的Session-Id
     * @param requestHeader header字符数组
     * @return Header[] 请求头
     */
    public static Header[] headerHandle(String cookie, String requestHeader) throws Exception {
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                //.other("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Session-Id", cookie);//cocos使用
        if (StringUtils.isNotEmpty(requestHeader)) { //header不为空则添加header
            JSONArray headersArray;
            try {
                headersArray = JSONArray.fromObject(requestHeader);
            } catch (Exception e) {
                throw new Exception("请求头非json格式：" + requestHeader);
            }
            for (int i = 0; i < headersArray.size(); i++) {
                JSONArray headersArray0 = headersArray.getJSONArray(i);
                httpHeader.other(headersArray0.getString(0), headersArray0.getString(1));
            }
        }
        return httpHeader.build();
    }

    /**
     * 处理GET 和POST 参数
     *
     * @param requestParameters 请求参数
     * @param url               请求域名
     * @param isGet             true:get,false:post
     */
    private static String requestParametersHandle(String requestParameters, String url, boolean isGet) throws Exception {
        if (StringUtils.isEmpty(requestParameters))
            return "";

        JSONArray requestParametersJa;
        try {
            requestParametersJa = JSONArray.fromObject(requestParameters);
        } catch (Exception e) {
            throw new Exception("请求参数非json格式:" + requestParameters);
        }
        UrlBuilder urlBuilder = UrlBuilder.custom();
        for (int i = 0; i < requestParametersJa.size(); i++) {
            JSONArray requestParametersArray0 = requestParametersJa.getJSONArray(i);
            urlBuilder.addBuilder(requestParametersArray0.getString(0), requestParametersArray0.getString(1));
        }
        String s = isGet ? urlBuilder.url(url).fullUrl() : urlBuilder.fullBody();
        System.out.println(s);
        return s;
    }

    /**
     * 请求，包括依赖接口的请求
     *
     * @param isDependent 是否是依赖接口
     */
    private String requestHandle(ApiManage apiManage, String url, String cookie, HttpConfig httpConfig, TestApiResult testApiResult, boolean isDependent) throws Exception {
        // ------------------------------请求头----------------------------------------------
        Header[] headers = headerHandle(cookie, apiManage.getRequestHeader());
        httpConfig.headers(headers);
        // ------------------------------请求参数----------------------------------------------
        String requestParameters = requestParametersHandle(apiManage.getRequestParameters(), url + apiManage.getPath(), apiManage.getMethod() == 1);
        // ------------------------------请求发送----------------------------------------------
        String result = apiManage.getMethod() == 1 ? DafaRequest.get(httpConfig.url(requestParameters))
                : DafaRequest.post(httpConfig.url(url + apiManage.getPath()).body(requestParameters));
        if (!isDependent) {
            testApiResult.setTestResult(returnResultHandle(result, apiManage.getPath(), requestParameters)); //正式接口
        }
        return result;
    }

    /**
     * 依赖接口请求
     */
    private String deRequestHandle(String url, String cookie, HttpConfig httpConfig, TestApiResult testApiResult, String dependentApi, String requestParameters) throws Exception {
        JSONArray dependentApiJa = JSONArray.fromObject(dependentApi);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dependentApiJa.size(); i++) { //多个依赖接口
            JSONArray dependentApiJa0 = dependentApiJa.getJSONArray(i);//dependentApiJa 0/id 1/rule
            int deId = dependentApiJa0.getInt(0);
            ApiManage apiManage = apiManageServer.getApiById(deId);

            //请求依赖接口
            String deResult = requestHandle(apiManage, url, cookie, httpConfig, testApiResult, true);

            if (StringUtils.isNotEmpty(dependentApiJa0.getString(1))) {//有提取规则
                // GET  : 0 listName,1 stateName,2 stateValue,3 id
                // POST : 0 getName
                String[] getRule = dependentApiJa0.getString(1).split(",");
                String deValue = "";
                try {
                    JSONObject deResultoJo = JSONObject.fromObject(deResult);
                    if (getRule.length == 4) { //GET : 取 list
                        JSONArray deResultoJa = null;
                        deResultoJa = deResultoJo.getJSONObject("data").getJSONArray(getRule[0]);
                        for (int j = 0; j < deResultoJa.size(); j++) {
                            JSONObject liistJo = deResultoJa.getJSONObject(i);
                            if (getRule[2].equals(liistJo.getString(getRule[1]))) {
                                deValue = liistJo.getString(getRule[3]);
                                break;
                            }
                        }
                    } else if (getRule.length == 1) {// POST : 取 code
                        deValue = deResultoJo.getJSONObject("data").getString(getRule[1]);
                    }
                } catch (Exception e) {
                    sb.append(returnResultHandle(StringUtils.isEmpty(deValue) ? deResult : deValue, apiManage.getPath(), apiManage.getRequestParameters()))
                            .append("\n");
                    testApiResult.setDependentResult1(sb.toString());//依赖接口
                    testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                    throw new Exception("依赖接口返回值解析或者提取失败：" + deResult);
                }
                sb.append(returnResultHandle(StringUtils.isEmpty(deValue) ? deResult : deValue, apiManage.getPath(), apiManage.getRequestParameters()))
                        .append("\n");
                if (StringUtils.isNotEmpty(deValue))
                    requestParameters = requestParameters.replace(String.format("{data%s}", i), deValue);
                else {
                    testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                    throw new Exception("依赖提取结果空");//有提取规则却提取空
                }
            }
        }
        testApiResult.setDependentResult1(sb.toString());//依赖接口结果
        return requestParameters;
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
}
