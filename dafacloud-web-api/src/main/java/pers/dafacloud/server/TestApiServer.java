package pers.dafacloud.server;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.dafacloud.enums.TestApiResultEnum;
import pers.dafacloud.model.ApiContent;
import pers.dafacloud.model.TestApiResult;
import pers.dafacloud.utils.Response;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TestApiServer {

    private static Logger logger = LoggerFactory.getLogger(TestApiServer.class);
    private static ExecutorService excutors = Executors.newFixedThreadPool(6);

    @Autowired
    TestApiResultServer testApiResultServer;

    @Autowired
    ApiContentServer apiContentServer;

    public String testApiOne(ApiContent apiContent, javax.servlet.http.Cookie[] reqCookies, HttpClientContext context) throws Exception {
        TestApiResult testApiResult = new TestApiResult();
        testApiResult.setHost(apiContent.getHost());
        testApiResult.setApiName(apiContent.getApiName());
        testApiResult.setApiPath(apiContent.getPath());
        testApiResult.setApiMethod(1);
        testApiResult.setCmsFront(apiContent.getCmsFront());
        testApiResult.setTestExecutor(apiContent.getOwner());

        //上下文
        getHttpClientContext(reqCookies, apiContent, context);

        //获取请求头，以及请求头带的cookie
        Header[] headers = getSetHeader(apiContent);
        //String reqParametersString = "";
        //依赖接口1
        if (StringUtils.isNotEmpty(apiContent.getDePath())) {
            try {
                String depResp = getDepResp(apiContent.getHost(), apiContent.getDePath(), apiContent.getDeMethod(),
                        apiContent.getDeReqParametersString(), apiContent.getDeReturnValue(), headers, context, testApiResult);
                testApiResult.setDependentResult1(returnResultHandle(depResp, apiContent.getDePath(), apiContent.getDeReqParametersString()));
                if (StringUtils.isNotEmpty(depResp))
                    apiContent.setReqParametersString(apiContent.getReqParametersString().replace("{data1}", depResp));//替换
            } catch (Exception e) {
                testApiResult.setDependentResult1(returnResultHandle(e.getMessage(), apiContent.getDePath(), apiContent.getDeReqParametersString()));
                testApiResultServer.addTestApiResult(testApiResult);
                return "依赖接口1返回错误: " + e.getMessage();
            }
        }

        //依赖接口2
        if (StringUtils.isNotEmpty(apiContent.getDePath2())) {
            try {
                String depResp2 = getDepResp(apiContent.getHost(), apiContent.getDePath2(), apiContent.getDeMethod2(),
                        apiContent.getDeReqParametersString2(), apiContent.getDeReturnValue2(), headers, context, testApiResult);
                testApiResult.setDependentResult2(returnResultHandle(depResp2, apiContent.getDePath2(), apiContent.getDeReqParametersString2()));
                if (StringUtils.isNotEmpty(depResp2))
                    apiContent.setReqParametersString(apiContent.getReqParametersString().replace("{data2}", depResp2));//替换
            } catch (Exception e) {
                testApiResult.setDependentResult2(returnResultHandle(e.getMessage(), apiContent.getDePath2(), apiContent.getDeReqParametersString2()));
                testApiResultServer.addTestApiResult(testApiResult);
                return "依赖接口2返回错误: " + e.getMessage();
            }
        }
        logger.info("reqParametersStringNew:" + apiContent.getReqParametersString());
        //正式请求---------------------------------------------------------------------
        String result;
        if (apiContent.getMethod().equals("1")) { //GET
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(apiContent.getHost() + apiContent.getPath() + "?" + URLEncoder.encode(apiContent.getReqParametersString(), "utf-8"))
                    .context(context);
            result = DafaRequest.get(httpConfig);
        } else if (apiContent.getMethod().equals("2")) {//POST
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(apiContent.getHost() + apiContent.getPath())
                    .context(context)
                    .body(apiContent.getReqParametersString());
            result = DafaRequest.post(httpConfig);
        } else {
            testApiResult.setDependentResult1(apiContent.getReqParametersString() + "\n" + "请求方法错误，目前只支持get和post请求");
            testApiResultServer.addTestApiResult(testApiResult);
            return "请求方法错误，目前只支持get和post请求";
        }
        testApiResult.setTestResult(returnResultHandle(apiContent.getPath(), apiContent.getReqParametersString(), result));
        if (JSONObject.fromObject(result).getInt("code") == 1) {
            testApiResult.setIsPass(TestApiResultEnum.SUCCESS.getCode());
        } else {
            testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
        }
        testApiResultServer.addTestApiResult(testApiResult);


        return result;
    }


    /**
     * 请求返回数据封装 返回结果+请求路径+请求参数
     */
    private String returnResultHandle(String result, String path, String reqParametersString) {
        String s = String.format("%s\n%s\n%s", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString);
        return s;
    }

    /**
     * 1.登录请求：设置带cookie的上下文
     * 2.不是登录请求：设置空的上下文
     *
     * @return HttpClientContext 上下文
     */
    private static void getHttpClientContext(javax.servlet.http.Cookie[] reqCookies, ApiContent apiContent, HttpClientContext context) throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        if (!apiContent.isLoginReq()) {//不是登录请求才设置cookie
            BasicClientCookie cookies = null;
            String c_sessionId = "";
            if (StringUtils.isNotEmpty(apiContent.getCookie())) { //取请求参数的cookie
                cookies = new BasicClientCookie("JSESSIONID", apiContent.getCookie());
            } else { //取获取连接的请求的cookie
                for (javax.servlet.http.Cookie requestCookie0 : reqCookies) {
                    if ("JSESSIONID".equals(requestCookie0.getName())) {
                        c_sessionId = requestCookie0.getValue();
                    }
                }
                if (StringUtils.isNotEmpty(c_sessionId))
                    cookies = new BasicClientCookie("JSESSIONID", c_sessionId);
                else
                    logger.info("c_sessionId返回null:" + c_sessionId);
            }
            if (cookies != null) {
                cookies.setDomain(new URL(apiContent.getHost()).getHost());//设置范围
                cookies.setVersion(0);
                cookies.setPath("/");
                cookieStore.addCookie(cookies);
            }
        }
        context.setCookieStore(cookieStore);
    }

    /**
     * 获取请求头，以及请求头带的cookie
     *
     * @return Header[] 请求头数组
     */
    private Header[] getSetHeader(ApiContent apiContent) {
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                //.other("Content-Type","application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Session-Id", apiContent.getCookie());//棋牌系统前台使用
        if (StringUtils.isNotEmpty(apiContent.getHeaderArray())) { //header不为空则添加header
            String[] headerArrayNew = apiContent.getHeaderArray().split("&");
            for (String headerValue : headerArrayNew) {
                String[] headerValueNew = headerValue.split("=");
                httpHeader.other(headerValueNew[0], headerValueNew[1]);
            }
        }
        Header[] headers = httpHeader.build();
        return headers;
    }

    private Header[] setHeader(ApiContent apiContent, HttpHeader httpHeader) {
        if (StringUtils.isNotEmpty(apiContent.getHeaderArray())) { //header不为空则添加header
            String[] headerArrayNew = apiContent.getHeaderArray().split("&");
            for (String headerValue : headerArrayNew) {
                String[] headerValueNew = headerValue.split("=");
                httpHeader.other(headerValueNew[0], headerValueNew[1]);
            }
        }
        Header[] headers = httpHeader.build();
        return headers;
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
                    testApiResult.setIsPass(TestApiResultEnum.ERROR.getCode());
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


    public Response testApiBatch(String host, String cookie, String testBatch, String groupsApi, String owner) throws Exception {
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
        List<ApiContent> apiContentList = apiContentServer.queryApiBatchTest(groupsApi, owner);

        if (apiContentList.size() == 0)
            return Response.error("获取执行用例数：0");

        logger.info("批量执行用例数量：" + apiContentList.size());

        excutors.execute(() -> {
            try {
                testApiBatchTask(host0, cookie, testBatch, apiContentList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return Response.success("用例执行中");
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
            Header[] headers = setHeader(apiContent, httpHeader);
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
