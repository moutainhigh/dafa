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
     * 批量运行task
     */
    public void task(String host, String cookie,String testBatch, List<ApiManage> apiManages) throws Exception {
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
            apiManageTask(apiManage, url, cookie,testBatch, httpConfig);
        }
    }

    private Response apiManageTask(ApiManage apiManage, String url, String cookie,String testBatch, HttpConfig httpConfig) {
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
            urlBuilder//.url(host)
                    .addBuilder(requestParametersArray0.getString(0), requestParametersArray0.getString(1));
        }
        return isGet ? urlBuilder.fullUrl() : urlBuilder.fullBody();
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
        String requestParameters = requestParametersHandle(apiManage.getRequestParameters(), url, apiManage.getMethod() == 1);

        // ------------------------------请求发送----------------------------------------------
        String result = apiManage.getMethod() == 1 ? DafaRequest.get(httpConfig.url(url + apiManage.getPath() + "?" + requestParameters))
                : DafaRequest.post(
                httpConfig.url(url + apiManage.getPath())
                        .body(requestParameters));
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
        for (int i = 0; i < dependentApiJa.size(); i++) {
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
        testApiResult.setDependentResult1(sb.toString());//依赖接口
        return requestParameters;
    }

    /**
     * 依赖接口调用
     */
    private String getDepResp(String host, String dePath, String deMethod, String deReqParametersString, String deReturnValue, Header[] headers, HttpClientContext context, TestApiResult testApiResult) throws Exception {
        String depResp = "";
        String dependentResult = "";
        String[] deReturn = deReturnValue.split(",");//获取返回值的规则
        //请求获取返回数据
        if ("1".equals(deMethod)) {//GET
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(host + dePath + "?" + URLEncoder.encode(deReqParametersString, "utf-8"))//依赖的数据，日期
                    .context(context);
            dependentResult = DafaRequest.get(httpConfig);
            JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
            if (dependentResultJson.getInt("code") != 1) {//依赖接口返回错误，直接返回
                testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                throw new Exception(dependentResult);
            }
            if (deReturn.length == 5) {//取list里的code数据，1,userBankCardList,isDisable,false,id
                JSONArray jsonArray = dependentResultJson.getJSONObject("data").getJSONArray(deReturn[1]);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (deReturn[3].equals(jsonObject.getString(deReturn[2]))) {
                        depResp = jsonObject.getString(deReturn[4]);
                    }
                }
                if (StringUtils.isEmpty(depResp)) {
                    testApiResult.setIsPass(TestApiResultEnum.DE_NO_DATA.getCode());
                    throw new Exception("依赖接口未获取到对应数据:" + dependentResult);
                }
            } else if (deReturn.length == 2) { //code
                depResp = dependentResultJson.getString(deReturn[1]);
            }

        } else if ("2".equals(deMethod)) {//POST
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(host + dePath)
                    .context(context)
                    .body(deReqParametersString);//依赖的参数
            dependentResult = DafaRequest.post(httpConfig);
            JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
            if (dependentResultJson.getInt("code") != 1) {
                testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                throw new Exception(dependentResult);
            }
            if (deReturn.length == 2) {//code
                depResp = dependentResultJson.getString(deReturn[1]);
            }
        } else {
            throw new Exception("依赖接口的请求方法错误，目前只支持get和post请求");
        }
        logger.info("dependentResult:" + dependentResult);
        return StringUtils.isEmpty(depResp) ? dependentResult : depResp;
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

    void testApiBatchTask() {


    }

    /**
     * 批量执行用例
     */
    private void testApiBatchTask(String host, String cookie, String testBatch, List<ApiContent> apiContentList) throws Exception {

        HttpConfig httpConfig = HttpConfigHandle(cookie, host);

        for (int i = 0; i < apiContentList.size(); i++) {
            ApiContent apiContent = apiContentList.get(i);

            TestApiResult testApiResult = new TestApiResult();
            testApiResult.setHost(host);
            testApiResult.setApiName(apiContent.getApiName());
            testApiResult.setApiPath(apiContent.getPath());
            testApiResult.setApiMethod(1);
            testApiResult.setCmsFront(apiContent.getCmsFront());
            testApiResult.setTestExecutor(apiContent.getOwner());
            testApiResult.setTestBatch(testBatch);

            HttpHeader httpHeader = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0")
                    .other("Session-Id", cookie);

            //获取请求头，以及请求头带的cookie
            Header[] headers = null;//setHeader(apiContent, httpHeader);
            httpConfig.headers(headers);
            String reqParametersString = parametersArrayHandle(apiContent.getReqParametersArray());

            if (StringUtils.isNotEmpty(apiContent.getDePath())) {
                String deParametersString = parametersArrayHandle(apiContent.getDeReqParametersArray());
                try {
                    String depResponse = drawDepApiValue(host, apiContent.getDePath(), apiContent.getDeMethod(),
                            deParametersString, apiContent.getDeReturnValue(), httpConfig, testApiResult);
                    testApiResult.setDependentResult1(returnResultHandle(depResponse, apiContent.getDePath(), deParametersString));
                    if (StringUtils.isNotEmpty(depResponse))
                        reqParametersString = reqParametersString.replace("{data1}", depResponse);//替换
                } catch (Exception e) {
                    testApiResult.setDependentResult1(returnResultHandle(e.getMessage(), apiContent.getDePath(), deParametersString));
                    testApiResultServer.addTestApiResult(testApiResult);
                    continue;
                }
            }
            if (StringUtils.isNotEmpty(apiContent.getDePath2())) {
                String deParametersString2 = parametersArrayHandle(apiContent.getDeReqParametersArray2());
                try {
                    String depResponse2 = drawDepApiValue(host, apiContent.getDePath2(), apiContent.getDeMethod2(),
                            deParametersString2, apiContent.getDeReturnValue2(), httpConfig, testApiResult);
                    testApiResult.setDependentResult2(returnResultHandle(depResponse2, apiContent.getDePath2(), deParametersString2));
                    if (StringUtils.isNotEmpty(depResponse2))
                        reqParametersString = reqParametersString.replace("{data2}", depResponse2);//替换
                } catch (Exception e) {
                    testApiResult.setDependentResult2(returnResultHandle(e.getMessage(), apiContent.getDePath2(), deParametersString2));
                    testApiResultServer.addTestApiResult(testApiResult);
                    continue;
                }
            }

            logger.info("reqParametersString:" + reqParametersString);
            //正式请求---------------------------------------------------------------------
            String result;
            apiContent.setReqParametersString(reqParametersString);
            if (apiContent.getMethod().equals("1")) { //GET
                httpConfig.url(host + apiContent.getPath() + "?" + URLEncoder.encode(reqParametersString, "utf-8"));
                result = DafaRequest.get(httpConfig);
            } else if (apiContent.getMethod().equals("2")) {//POST
                httpConfig.url(host + apiContent.getPath())
                        .body(apiContent.getReqParametersString());
                result = DafaRequest.post(httpConfig);
            } else {
                testApiResult.setTestResult(apiContent.getReqParametersString() + "：请求方法错误，目前只支持get和post请求");
                testApiResultServer.addTestApiResult(testApiResult);
                continue;
            }

            testApiResult.setTestResult(returnResultHandle(result, apiContent.getPath(), apiContent.getReqParametersString()));
            try {
                if (JSONObject.fromObject(result).getInt("code") == 1) {
                    testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
                } else {
                    if (result.contains("未登陆")) {
                        return;
                    }
                    testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
                }
            } catch (Exception e) {
                testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
            }

            testApiResultServer.addTestApiResult(testApiResult);
            Thread.sleep(3 * 100);
        }

    }

    /**
     * 提取依赖api 返回数据
     */
    private String drawDepApiValue(String host, String dePath, String deMethod, String deParametersString, String deReturnValue,
                                   HttpConfig httpConfig, TestApiResult testApiResult) throws Exception {
        String depDrawValue = "";
        String[] deReturn = deReturnValue.split(",");
        if ("1".equals(deMethod)) { //GET
            httpConfig.url(host + dePath + "?" + deParametersString);
            String deResult = DafaRequest.get(httpConfig);
            JSONObject dependentResultJson = null;
            try {
                dependentResultJson = JSONObject.fromObject(deResult);
            } catch (Exception e) {
                throw new Exception("依赖接口返回数据非json:" + dependentResultJson);
            }
            if (dependentResultJson.getInt("code") != 1) {
                testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                throw new Exception(deResult);
            }

            //提起返回值数据
            if (deReturn.length == 5) {//取list里的code数据，1,userBankCardList,isDisable,false,id
                JSONArray jsonArray = dependentResultJson.getJSONObject("data").getJSONArray(deReturn[1]);
                for (int l = 0; l < jsonArray.size(); l++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(l);
                    if (deReturn[3].equals(jsonObject.getString(deReturn[2]))) {
                        depDrawValue = jsonObject.getString(deReturn[4]);
                    }
                }
                if (StringUtils.isEmpty(deReturnValue)) {
                    testApiResult.setIsPass(TestApiResultEnum.DE_NO_DATA.getCode());
                    throw new Exception("依赖接口未获取到对应数据:" + deReturnValue);
                }
            } else if (deReturn.length == 2) { //code
                depDrawValue = dependentResultJson.getString(deReturn[1]);
            }

        } else if ("2".equals(deMethod)) {//POST
            httpConfig.url(host + dePath).body(deParametersString);//依赖的参数
            String deResult = DafaRequest.post(httpConfig);
            JSONObject dependentResultJson = null;
            try {
                dependentResultJson = JSONObject.fromObject(deResult);
            } catch (Exception e) {
                throw new Exception("依赖接口返回数据非json:" + dependentResultJson);
            }
            if (dependentResultJson.getInt("code") != 1) {
                testApiResult.setIsPass(TestApiResultEnum.DE_ERROR.getCode());
                throw new Exception(deResult);
            }
            if (deReturn.length == 2) {//code
                depDrawValue = dependentResultJson.getString(deReturn[1]);
            }
        } else {
            throw new Exception("依赖接口的请求方法错误，目前只支持get和post请求");
        }
        return depDrawValue;
    }

    /**
     *
     */
    private HttpConfig HttpConfigHandle(String cookie, String host) throws Exception {
        HttpClientContext httpClientContext = new HttpClientContext();
        HttpConfig httpConfig = HttpConfig.custom().context(httpClientContext);
        BasicCookieStore basicCookieStore = new BasicCookieStore();
        BasicClientCookie cookies = new BasicClientCookie("JSESSIONID", cookie);
        cookies.setDomain(new URL(host).getHost());//设置范围
        cookies.setVersion(0);
        cookies.setPath("/");
        basicCookieStore.addCookie(cookies);
        httpClientContext.setCookieStore(basicCookieStore);
        return httpConfig;
    }

    /**
     * 请求数组参数处理
     */
    private String parametersArrayHandle(String parametersArray) {
        if (StringUtils.isEmpty(parametersArray))
            return "";
        JSONArray ja = JSONArray.fromObject(parametersArray);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < ja.size(); j++) {
            JSONArray ja1 = ja.getJSONArray(j);
            sb.append(ja1.getString(0)).append("=").append(ja1.getString(1)).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
