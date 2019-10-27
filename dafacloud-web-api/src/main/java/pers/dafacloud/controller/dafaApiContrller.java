package pers.dafacloud.controller;

import lombok.Data;
import lombok.ToString;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.dao.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.dao.pojo.ApiContent;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
public class dafaApiContrller {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    ApiContentMapper apiContentMapper = sqlSession.getMapper(ApiContentMapper.class);

    @GetMapping("/queryDafaApi")
    public Response query(@RequestParam(value = "apiName", required = false) String apiName,
                          @RequestParam(value = "path", required = false) String path,
                          @RequestParam(value = "dependApiName", required = false) String dependApiName,
                          @RequestParam(value = "module", required = false) String module,
                          @RequestParam(value = "cmsFront", required = false) String cmsFront,
                          @RequestParam(value = "project", required = false, defaultValue = "-1") int project,
                          @RequestParam(value = "owner", required = false) String owner,
                          @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", required = false, defaultValue = "1") int pageSize
                          //,@RequestParam("description") String description
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
        List<ApiContent> list = apiContentMapper.queryApi(apiContent);
        int count = apiContentMapper.queryApiCount(apiContent);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return fillResponse(jsonObject);
    }

    //配置
    @PostMapping("/addDafaApi")
    public String add(@RequestParam(value = "apiName", required = false) String apiName,
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
                      @RequestParam(value = "deReturnValue", required = false) String deReturnValue
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

        int i = apiContentMapper.addApi(apiContent);
        JSONObject jsonObject0 = new JSONObject();
        if (i == 1) {
            jsonObject0.put("code", 1);
            jsonObject0.put("data", "新增成功");
        } else {
            jsonObject0.put("code", -1);
            jsonObject0.put("data", "新增失败");
        }
        return jsonObject0.toString();
    }

    @PostMapping("/updateDafaApi")
    public String update(@RequestParam(value = "apiName", required = false) String apiName,
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

        int i = apiContentMapper.updateApi(apiContent);
        JSONObject jsonObject0 = new JSONObject();
        if (i == 1) {
            jsonObject0.put("code", 1);
            jsonObject0.put("data", "修改成功");
        } else {
            jsonObject0.put("code", -1);
            jsonObject0.put("data", "修改失败");
        }
        return jsonObject0.toString();
    }

    //请求
    @PostMapping("/testDafaApi")
    public void request(@RequestParam(value = "host", required = false) String host,
                        @RequestParam(value = "path", required = false) String path,
                        @RequestParam(value = "cookie", required = false) String cookie,
                        @RequestParam(value = "reqParametersString", required = false) String reqParametersString,
                        @RequestParam(value = "method", required = false) String method,
                        @RequestParam(value = "headerArray", required = false) String headerArray,
                        @RequestParam(value = "dePath", required = false) String dePath,
                        @RequestParam(value = "deReqParametersString", required = false) String deReqParametersString,
                        @RequestParam(value = "deMethod", required = false) String deMethod,
                        @RequestParam(value = "deReturnValue", required = false) String deReturnValue,
                        //@RequestHeader(value = "JSESSIONID") String requestCookie,
                        HttpServletResponse response, HttpServletRequest request
    ) throws Exception {
        response.setCharacterEncoding("UTF-8");
        System.out.println("host:" + host);//请求地址
        System.out.println("path:" + path);//请求地址
        System.out.println("reqParametersString:" + reqParametersString);//请求内容，get请求cookie可以是空
        System.out.println("method:" + method);//请求方法
        System.out.println("cookie:" + cookie); //请求cookie
        System.out.println("headerArray:" + headerArray);
        //System.out.println("requestCookie:" + requestCookie);
        //System.out.println(request.getCookies().length);
        System.out.println("RequestURI:" + request.getRequestURI());

        System.out.println("dePath:" + dePath);
        System.out.println("deReqParametersString:" + deReqParametersString);
        System.out.println("deMethod:" + deMethod);
        System.out.println("deReturnValue:" + deReturnValue);

        boolean isLoginReq = path.endsWith("login");//是否是登录请求

        CookieStore cookieStore = new BasicCookieStore();
        //host
        String hostNew;
        if (!host.contains("http")) {
            hostNew = "http://" + host;
        } else {
            hostNew = host;
        }

        if (!isLoginReq) {//不是登录请求才设置cookie
            //cookiec处理
            BasicClientCookie cookies = null;
            String c_sessionId = "";
            if (request.getCookies() == null) {
                c_sessionId = "";
            }
            if (StringUtils.isNotEmpty(cookie)) { //取请求参数的cookie不为空
                cookies = new BasicClientCookie("JSESSIONID", cookie);
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
                cookies.setDomain(new URL(hostNew).getHost());//设置范围
                cookies.setVersion(0);
                cookies.setPath("/");
                cookieStore.addCookie(cookies);
            }
        }
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);

        //header
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0")
                .other("Origin", hostNew)
                .other("Session-Id", cookie) //棋牌系统前台使用
                .build();


        //如果有依赖 ============================================
        //String newReqParametersString = "";
        if (StringUtils.isNotEmpty(dePath)) {
            String[] deReturn = deReturnValue.split(",");
            //1.通过ID获取依赖api信息，入参和提取数据
            //请求获取返回数据
            if ("1".equals(deMethod)) {//GET
                HttpConfig httpConfig = HttpConfig.custom().
                        headers(headers)
                        .url(hostNew + dePath + "?" + deReqParametersString)//依赖的数据，日期5-
                        .context(context);
                String dependentResult = DafaRequest.get(httpConfig);
                JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
                if (dependentResultJson.getInt("code") != 1) {
                    response.getWriter().write("依赖接口返回错误:" + dependentResult);//依赖接口返回错误，直接返回
                    return;
                }
                String dependentData = "";
                if (deReturn.length == 3) {//list
                    dependentData = dependentResultJson.getJSONObject("data")
                            .getJSONArray(deReturn[1]).getJSONObject(0).getString(deReturn[2]);
                } else if (deReturn.length == 2) {//code
                    dependentData = JSONObject.fromObject(dependentResult).getJSONObject("data").getString(deReturn[1]);
                }
                System.out.println(dependentResult);
                reqParametersString = reqParametersString.replace("{data}", dependentData);//替换
            } else if ("2".equals(deMethod)) {//POST
                HttpConfig httpConfig = HttpConfig.custom().
                        headers(headers)
                        .url(hostNew + dePath) //
                        .context(context)
                        .body(deReqParametersString);//依赖的参数
                String dependentResult = DafaRequest.post(httpConfig);

                JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
                if (dependentResultJson.getInt("code") != 1) {
                    response.getWriter().write("依赖接口返回错误:" +dependentResult);//依赖接口返回错误，直接返回
                    return;
                }
                String dependentData = "";
                if (deReturn.length == 3) {//list
                    dependentData = dependentResultJson.getJSONObject("data")
                            .getJSONArray(deReturn[1]).getJSONObject(0).getString(deReturn[2]);
                } else if (deReturn.length == 2) {//code
                    dependentData = JSONObject.fromObject(dependentResult).getJSONObject("data").getString(deReturn[1]);
                }
                System.out.println(dependentResult);
                reqParametersString = reqParametersString.replace("{data}", dependentData);//替换
            } else {
                response.getWriter().write("依赖接口的请求方法错误，目前只支持get和post请求");
                return;
            }
        }

        System.out.println("NEW-reqParametersString:" + reqParametersString);
        //请求
        String result;
        if (method.equals("1")) { //GET
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(hostNew + path + "?" + reqParametersString)
                    .context(context);
            result = DafaRequest.get(httpConfig);
        } else if (method.equals("2")) { //POST
            HttpConfig httpConfig = HttpConfig.custom().
                    headers(headers)
                    .url(hostNew + path)
                    .context(context)
                    .body(reqParametersString);
            result = DafaRequest.post(httpConfig);
        } else {
            response.getWriter().write("请求方法错误，目前只支持get和post请求");
            return;
        }

        if (isLoginReq) {//登录请求添加cookie
            //打印cookie
            String responseCookie = "";
            List<Cookie> cookie1 = context.getCookieStore().getCookies();
            for (Cookie cookie2 : cookie1) {
                System.out.println(cookie2.getName() + "==" + cookie2.getValue());
                if ("JSESSIONID".equals(cookie2.getName())) {
                    responseCookie = cookie2.getValue();
                }
            }
            //System.out.println(result);
            if (StringUtils.isNotEmpty(responseCookie)) {
                javax.servlet.http.Cookie cookienew = new javax.servlet.http.Cookie("JSESSIONID", responseCookie);
                cookienew.setVersion(0);
                cookienew.setPath("/");
                cookienew.setDomain(new URL(request.getRequestURL().toString()).getHost());
                response.addCookie(cookienew);
            }
        }
        response.getWriter().write(result);//写入结果

    }


    @PostMapping("/delete")
    public String delete(@RequestParam("id") int id) {
        int delete = apiContentMapper.deleteApi(id);
        if (delete == 0)
            return "删除失败";
        else
            return "删除成功";
    }

    public static String getDependentData(String dePath,String deMethod,String deReqParametersString, Header[] headers ){

        //如果有依赖 ============================================
        //String newReqParametersString = "";
        //if (StringUtils.isNotEmpty(dePath)) {
        //    String[] deReturn = deReturnValue.split(",");
        //    //1.通过ID获取依赖api信息，入参和提取数据
        //    //请求获取返回数据
        //    if ("1".equals(deMethod)) {//GET
        //        HttpConfig httpConfig = HttpConfig.custom().
        //                headers(headers)
        //                .url(hostNew + dePath + "?" + deReqParametersString)//依赖的数据，日期5-
        //                .context(context);
        //        String dependentResult = DafaRequest.get(httpConfig);
        //        JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
        //        if (dependentResultJson.getInt("code") != 1) {
        //            response.getWriter().write("依赖接口返回错误:" + dependentResult);//依赖接口返回错误，直接返回
        //            return;
        //        }
        //        String dependentData = "";
        //        if (deReturn.length == 3) {//list
        //            dependentData = dependentResultJson.getJSONObject("data")
        //                    .getJSONArray(deReturn[1]).getJSONObject(0).getString(deReturn[2]);
        //        } else if (deReturn.length == 2) {//code
        //            dependentData = JSONObject.fromObject(dependentResult).getJSONObject("data").getString(deReturn[1]);
        //        }
        //        System.out.println(dependentResult);
        //        reqParametersString = reqParametersString.replace("{data}", dependentData);//替换
        //    } else if ("2".equals(deMethod)) {//POST
        //        HttpConfig httpConfig = HttpConfig.custom().
        //                headers(headers)
        //                .url(hostNew + dePath) //
        //                .context(context)
        //                .body(deReqParametersString);//依赖的参数
        //        String dependentResult = DafaRequest.post(httpConfig);
        //
        //        JSONObject dependentResultJson = JSONObject.fromObject(dependentResult);
        //        if (dependentResultJson.getInt("code") != 1) {
        //            response.getWriter().write("依赖接口返回错误:" +dependentResult);//依赖接口返回错误，直接返回
        //            return;
        //        }
        //        String dependentData = "";
        //        if (deReturn.length == 3) {//list
        //            dependentData = dependentResultJson.getJSONObject("data")
        //                    .getJSONArray(deReturn[1]).getJSONObject(0).getString(deReturn[2]);
        //        } else if (deReturn.length == 2) {//code
        //            dependentData = JSONObject.fromObject(dependentResult).getJSONObject("data").getString(deReturn[1]);
        //        }
        //        System.out.println(dependentResult);
        //        reqParametersString = reqParametersString.replace("{data}", dependentData);//替换
        //    } else {
        //        response.getWriter().write("依赖接口的请求方法错误，目前只支持get和post请求");
        //        return;
        //    }
        //}

        return null;
    }


    public final static Response fillResponse(Object data) {
        Response response = new Response();
        response.code = 1;
        response.data = data;
        return response;
    }


    public static void main(String[] args) {
        /*List<JSONObject> list = new ArrayList<>();
        JSONObject json = new JSONObject();
        json.put("aa", "bb");
        list.add(json);
        Response res = fillResponse(list);
        System.out.println(res);*/
        String s = "a{data}c";
        s = s.replace("{data}", "b");
        System.out.println(s);
    }

    @Data
    @ToString
    public static class Response {
        private int code;
        private Object data;
    }


}
