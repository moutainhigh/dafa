package pers.dafacloud.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.model.ApiContent;
import pers.dafacloud.model.TestApiResult;
import pers.dafacloud.server.ApiContentServer;
import pers.dafacloud.server.TestApiResultServer;
import pers.dafacloud.utils.Response;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class DafaApiContrller {

    Logger logger = LoggerFactory.getLogger(getClass());

    //SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    //ApiContentMapper apiContentMapper = sqlSession.getMapper(ApiContentMapper.class);

    @Autowired
    ApiContentServer apiContentServer;

    @Autowired
    TestApiResultServer testApiResultServer;


    @GetMapping("/queryDafaApi")
    public Response query(@RequestParam(value = "apiName", required = false) String apiName,
                          @RequestParam(value = "path", required = false) String path,
                          @RequestParam(value = "dependApiName", required = false) String dependApiName,
                          @RequestParam(value = "module", required = false) String module,
                          @RequestParam(value = "cmsFront", required = false) String cmsFront,
                          @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                          @RequestParam(value = "owner", required = false) String owner,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") int pageSize,
                          @RequestParam(value = "groupsApi", required = false) String groupsApi,
                          @RequestParam(value = "sort", required = false) String sort
    ) {
        ApiContent apiContent = new ApiContent();
        apiContent.setApiName(apiName);
        apiContent.setPath(path);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setOwner(owner);
        apiContent.setPageNum((pageNum - 1) * pageSize);
        apiContent.setPageSize(pageSize);
        apiContent.setGroupsApi(groupsApi);
        apiContent.setSort(sort);

        List<ApiContent> list = apiContentServer.apiContentList(apiContent);
        int count = apiContentServer.apiContentCount(apiContent);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return Response.success(jsonObject);
    }


    @PostMapping("/addDafaApi")
    public Response add(@RequestParam(value = "apiName", required = false) String apiName,
                        @RequestParam(value = "path", required = false) String path,
                        @RequestParam(value = "method", required = false) String method,
                        @RequestParam(value = "reqParametersArray", required = false) String reqParametersArray,
                        @RequestParam(value = "headerArray", required = false) String headerArray,
                        @RequestParam(value = "dependApiName", required = false) String dependApiName,
                        @RequestParam(value = "module", required = false) String module,
                        @RequestParam(value = "cmsFront", required = false) String cmsFront,
                        @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                        @RequestParam(value = "description", required = false) String description,
                        @RequestParam(value = "owner", required = false) String owner,
                        @RequestParam(value = "responseBody", required = false) String responseBody,
                        @RequestParam(value = "dePath", required = false) String dePath,
                        @RequestParam(value = "deMethod", required = false) String deMethod,
                        @RequestParam(value = "deReqParametersArray", required = false) String deReqParametersArray,
                        @RequestParam(value = "deReturnValue", required = false) String deReturnValue,

                        @RequestParam(value = "dePath2", required = false) String dePath2,
                        @RequestParam(value = "deMethod2", required = false) String deMethod2,
                        @RequestParam(value = "deReqParametersArray2", required = false) String deReqParametersArray2,
                        @RequestParam(value = "deReturnValue2", required = false) String deReturnValue2,

                        @RequestParam(value = "groupsApi", required = false) String groupsApi,
                        @RequestParam(value = "sort", required = false, defaultValue = "-1") String sort
    ) {
        System.out.println("headerArray:" + headerArray);
        ApiContent apiContent = new ApiContent();
        apiContent.setApiName(apiName);
        apiContent.setPath(path);
        apiContent.setMethod(method);
        apiContent.setReqParametersArray(reqParametersArray);
        apiContent.setHeaderArray(headerArray);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setDescription(description);
        apiContent.setOwner(owner);
        apiContent.setResponseBody(responseBody);

        apiContent.setDePath(dePath);
        apiContent.setDeMethod(deMethod);
        apiContent.setDeReqParametersArray(deReqParametersArray);
        apiContent.setDeReturnValue(deReturnValue);

        apiContent.setDePath2(dePath2);
        apiContent.setDeMethod2(deMethod2);
        apiContent.setDeReqParametersArray2(deReqParametersArray2);
        apiContent.setDeReturnValue2(deReturnValue2);

        apiContent.setSort(sort);

        apiContent.setGroupsApi(groupsApi);

        int i = apiContentServer.addApiContent(apiContent);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/cloneApi")
    public Response cloneApi(int id) {
        int i = apiContentServer.cloneApi(id);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/updateDafaApi")
    public Response update(@RequestParam(value = "apiName", required = false) String apiName,
                           @RequestParam(value = "path", required = false) String path,
                           @RequestParam(value = "method", required = false) String method,
                           @RequestParam(value = "reqParametersArray", required = false) String reqParametersArray,
                           @RequestParam(value = "headerArray", required = false) String headerArray,
                           @RequestParam(value = "dependApiName", required = false) String dependApiName,
                           @RequestParam(value = "module", required = false) String module,
                           @RequestParam(value = "cmsFront", required = false) String cmsFront,
                           @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "owner", required = false) String owner,
                           @RequestParam(value = "responseBody", required = false) String responseBody,
                           @RequestParam(value = "dePath", required = false) String dePath,
                           @RequestParam(value = "deMethod", required = false) String deMethod,
                           @RequestParam(value = "deReqParametersArray", required = false) String deReqParametersArray,
                           @RequestParam(value = "deReturnValue", required = false) String deReturnValue,

                           @RequestParam(value = "dePath2", required = false) String dePath2,
                           @RequestParam(value = "deMethod2", required = false) String deMethod2,
                           @RequestParam(value = "deReqParametersArray2", required = false) String deReqParametersArray2,
                           @RequestParam(value = "deReturnValue2", required = false) String deReturnValue2,

                           @RequestParam(value = "groupsApi", required = false) String groupsApi,
                           @RequestParam(value = "sort", required = false, defaultValue = "-1") String sort,
                           int id) {
        ApiContent apiContent = new ApiContent();
        apiContent.setId(id);
        apiContent.setApiName(apiName);
        apiContent.setPath(path);
        apiContent.setMethod(method);
        apiContent.setReqParametersArray(reqParametersArray);
        apiContent.setHeaderArray(headerArray);
        apiContent.setDependApiName(dependApiName);
        apiContent.setModule(module);
        apiContent.setCmsFront(cmsFront);
        apiContent.setProject(project);
        apiContent.setDescription(description);
        apiContent.setOwner(owner);
        apiContent.setResponseBody(responseBody);

        apiContent.setDePath(dePath);
        apiContent.setDeMethod(deMethod);
        apiContent.setDeReqParametersArray(deReqParametersArray);
        apiContent.setDeReturnValue(deReturnValue);

        apiContent.setDePath2(dePath2);
        apiContent.setDeMethod2(deMethod2);
        apiContent.setDeReqParametersArray2(deReqParametersArray2);
        apiContent.setDeReturnValue2(deReturnValue2);

        apiContent.setGroupsApi(groupsApi);

        apiContent.setSort(sort);

        int i = apiContentServer.updateApiContent(apiContent);
        if (i == 1) {
            return Response.success("修改成功");
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/testDafaApi")
    public void request(String host, String cookie, String apiName, String path, int cmsFront, String owner,
                        String reqParametersString, String method, String headerArray, String dePath,
                        String deReqParametersString, String deMethod, String deReturnValue, String dePath2,
                        String deMethod2, String deReqParametersString2, String deReturnValue2,
                        HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        System.out.println("-----------------参数----------------------");
        response.setCharacterEncoding("UTF-8");
        System.out.println("host:" + host);//请求地址
        System.out.println("cookie:" + cookie); //请求cookie
        System.out.println("path:" + path);//请求地址
        System.out.println("reqParametersString:" + reqParametersString);//请求内容，get请求cookie可以是空
        System.out.println("method:" + method);//请求方法
        System.out.println("headerArray:" + headerArray);

        System.out.println("dePath:" + dePath);
        System.out.println("deReqParametersString:" + deReqParametersString);
        System.out.println("deMethod:" + deMethod);
        System.out.println("deReturnValue:" + deReturnValue);

        System.out.println("dePath2:" + dePath2);
        System.out.println("deReqParametersString2:" + deReqParametersString2);
        System.out.println("deMethod2:" + deMethod2);
        System.out.println("deReturnValue2:" + deReturnValue2);
        System.out.println("-----------------参数----------------------");

        ApiContent apiContent = new ApiContent();
        apiContent.setHost(host.contains("http") ? host : String.format("%s%s", "http://",
                host.replace("/", "").replace("?", "")));
        apiContent.setCookie(cookie);

        apiContent.setPath(path);
        apiContent.setReqParametersString(reqParametersString);
        apiContent.setMethod(method);
        apiContent.setHeaderArray(headerArray);

        apiContent.setDePath(dePath);
        apiContent.setDeReqParametersString(deReqParametersString);
        apiContent.setDeMethod(deMethod);
        apiContent.setDeReturnValue(deReturnValue);

        apiContent.setDePath2(dePath2);
        apiContent.setDeReqParametersString2(deReqParametersString2);
        apiContent.setDeMethod2(deMethod2);
        apiContent.setDeReturnValue2(deReturnValue2);
        apiContent.setLoginReq(path.endsWith("login"));////是否是登录请求

        logger.info(apiContent.toString());

        TestApiResult testApiResult = new TestApiResult();
        testApiResult.setHost(host);
        testApiResult.setApiName(apiName);
        testApiResult.setApiPath(path);
        testApiResult.setApiMethod(method);
        testApiResult.setCmsFront(cmsFront);
        testApiResult.setTestExecutor(owner);

        //上下文
        HttpClientContext context = getHttpClientContext(request, apiContent);

        //获取请求头，以及请求头带的cookie
        Header[] headers = getSetHeader(apiContent);

        //依赖接口1
        if (StringUtils.isNotEmpty(dePath)) {
            try {
                String depResp = getDepResp(apiContent.getHost(), apiContent.getDePath(), apiContent.getDeMethod(),
                        apiContent.getDeReqParametersString(), apiContent.getDeReturnValue(), headers, context, testApiResult);
                testApiResult.setDependentResult1(returnResultHandle(apiContent.getDePath(), apiContent.getDeReqParametersString(), depResp));
                if (StringUtils.isNotEmpty(depResp))
                    reqParametersString = reqParametersString.replace("{data1}", depResp);//替换
            } catch (Exception e) {
                testApiResult.setDependentResult1(returnResultHandle(apiContent.getDePath(), apiContent.getDeReqParametersString(), e.getMessage()));
                testApiResultServer.addTestApiResult(testApiResult);
                response.getWriter().write("\n依赖接口1返回错误: " + e.getMessage());
                return;
            }
        }

        //依赖接口2
        if (StringUtils.isNotEmpty(dePath2)) {
            try {
                String depResp2 = getDepResp(apiContent.getHost(), apiContent.getDePath2(), apiContent.getDeMethod2(),
                        apiContent.getDeReqParametersString2(), apiContent.getDeReturnValue2(), headers, context, testApiResult);
                testApiResult.setDependentResult2(returnResultHandle(apiContent.getDePath2(), apiContent.getDeReqParametersString2(), depResp2));
                if (StringUtils.isNotEmpty(depResp2))
                    reqParametersString = reqParametersString.replace("{data2}", depResp2);//替换
            } catch (Exception e) {
                testApiResult.setDependentResult2(returnResultHandle(apiContent.getDePath2(), apiContent.getDeReqParametersString2(), e.getMessage()));
                testApiResultServer.addTestApiResult(testApiResult);
                response.getWriter().write(e.getMessage());
                return;
            }
        }

        System.out.println("reqParametersString-2:" + reqParametersString);

        //正式请求---------------------------------------------------------------------
        String result;
        if (method.equals("1")) { //GET
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(apiContent.getHost() + path + "?" + URLEncoder.encode(reqParametersString, "utf-8"))
                    .context(context);
            result = DafaRequest.get(httpConfig);
        } else if (method.equals("2")) {//POST
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(apiContent.getHost() + path)
                    .context(context)
                    .body(reqParametersString);
            result = DafaRequest.post(httpConfig);
        } else {
            response.getWriter().write("请求方法错误，目前只支持get和post请求");
            testApiResult.setDependentResult1(apiContent.getReqParametersString() + "\n" + "请求方法错误，目前只支持get和post请求");
            testApiResultServer.addTestApiResult(testApiResult);
            return;
        }
        testApiResult.setTestResult(returnResultHandle(apiContent.getPath(), apiContent.getReqParametersString(), result));
        if (JSONObject.fromObject(result).getInt("code") == 1) {
            testApiResult.setIsPass("1");
        } else {
            testApiResult.setIsPass("-11");
        }

        testApiResultServer.addTestApiResult(testApiResult);

        //responset添加cookie,header添加JSESSIONID---------------------------------------------------------------------
        if (apiContent.isLoginReq()) {//
            //1.从context获取的cookie
            String responseCookie = "";
            List<Cookie> cookie1 = context.getCookieStore().getCookies();
            for (Cookie cookie2 : cookie1) {
                //System.out.println(cookie2.getName() + "==" + cookie2.getValue());
                if ("JSESSIONID".equals(cookie2.getName())) {
                    responseCookie = cookie2.getValue();
                }
            }
            //2.从返回结果中获取cookie，棋牌系统前台(cocos)的cookie是登录返回body中获取，然后再添加到header
            if (StringUtils.isEmpty(responseCookie)) {
                JSONObject loginResult = JSONObject.fromObject(result);
                if (loginResult.getInt("code") == 1) {
                    responseCookie = loginResult.getJSONObject("data").getString("sessionId");
                }
            }
            System.out.println(responseCookie);
            //3.response设置cookie，header添加JSESSIONID
            if (StringUtils.isNotEmpty(responseCookie)) { //cookie添加到response
                javax.servlet.http.Cookie cookienew = new javax.servlet.http.Cookie("JSESSIONID", responseCookie);
                cookienew.setVersion(0);
                cookienew.setPath("/");
                cookienew.setDomain(new URL(request.getRequestURL().toString()).getHost());
                response.addCookie(cookienew);
                response.addHeader("JSESSIONID", responseCookie);//添加属性，不会覆盖原来的属性
                //response.setHeader("JSESSIONID","bbbbbb"); //会覆盖掉原来的属性
            }
        }
        response.getWriter().write(result);//写入结果
    }

    /**
     * 请求返回数据封装 请求路径+请求参数+返回结果
     */
    public String returnResultHandle(String path, String reqParametersString, String result) {
        String s = String.format("%s\n%s\n%s", StringUtils.isEmpty(result) ? "--" : result, path, StringUtils.isEmpty(reqParametersString) ? "--" : reqParametersString);
        return s;
    }

    /**
     * 1.登录请求：设置带cookie的上下文
     * 2.不是登录请求：设置空的上下文
     *
     * @return HttpClientContext 上下文
     */
    public static HttpClientContext getHttpClientContext(HttpServletRequest request, ApiContent apiContent) throws Exception {
        CookieStore cookieStore = new BasicCookieStore();
        if (!apiContent.isLoginReq()) {//不是登录请求才设置cookie
            //cookiec处理
            BasicClientCookie cookies = null;
            String c_sessionId = "";
            if (request.getCookies() == null) {
                c_sessionId = "";
            }
            if (StringUtils.isNotEmpty(apiContent.getCookie())) { //取请求参数的cookie不为空
                cookies = new BasicClientCookie("JSESSIONID", apiContent.getCookie());
            } else if (StringUtils.isNotEmpty(c_sessionId)) { //取获取连接的请求的cookie
                for (javax.servlet.http.Cookie requestCookie0 : request.getCookies()) {
                    if ("JSESSIONID".equals(requestCookie0.getName())) {
                        c_sessionId = requestCookie0.getValue();
                    }
                }
                cookies = new BasicClientCookie("JSESSIONID", c_sessionId);
            }
            //response.getWriter().write("没有获取cookie，请先调用登录接口，或者手动填入cookie");
            if (cookies != null) {
                cookies.setDomain(new URL(apiContent.getHost()).getHost());//设置范围
                cookies.setVersion(0);
                cookies.setPath("/");
                cookieStore.addCookie(cookies);
            }
        }
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        return context;
    }


    /**
     * 获取请求头，以及请求头带的cookie
     *
     * @return Header[] 请求头数组
     */
    public static Header[] getSetHeader(ApiContent apiContent) {
        HttpHeader httpHeader = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Origin", apiContent.getHost())
                .other("Session-Id", apiContent.getCookie()); //棋牌系统前台使用

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
    public String getDepResp(String host, String dePath, String deMethod, String deReqParametersString, String deReturnValue, Header[] headers, HttpClientContext context, TestApiResult testApiResult) throws Exception {
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
                testApiResult.setIsPass("-12");
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
                    testApiResult.setIsPass("-13");
                    throw new Exception("依赖接口未获取到对应数据:" + dependentResult);
                }
            } else if (deReturn.length == 2) { //code
                depResp = dependentResultJson.getString(deReturn[1]);
            }
            System.out.println(dependentResult);
        } else if ("2".equals(deMethod)) {//POST
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(host + dePath) //
                    .context(context)
                    .body(deReqParametersString);//依赖的参数
            dependentResult = DafaRequest.post(httpConfig);
            JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
            if (dependentResultJson.getInt("code") != 1) {
                testApiResult.setIsPass("-12");
                throw new Exception(dependentResult);
            }
            if (deReturn.length == 2) {//code
                depResp = dependentResultJson.getString(deReturn[1]);
            }
            System.out.println(dependentResult);
        } else {
            throw new Exception("依赖接口的请求方法错误，目前只支持get和post请求");
        }
        return StringUtils.isEmpty(depResp) ? dependentResult : depResp;
    }


}
