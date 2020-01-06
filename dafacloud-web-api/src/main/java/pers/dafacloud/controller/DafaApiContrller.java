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
import pers.dafacloud.server.TestApiServer;
import pers.dafacloud.utils.Response;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class DafaApiContrller {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    ApiContentServer apiContentServer;

    @Autowired
    TestApiResultServer testApiResultServer;

    @Autowired
    TestApiServer testApiServer;


    @GetMapping("/1queryApiBatchTest")
    public Response queryApiBatchTest(String host, String cookie, String testBatch, String groupsApi, String owner) throws Exception {
        return testApiServer.testApiBatch(host, cookie, testBatch, groupsApi, owner);
    }

    @GetMapping("/1queryDafaApi")
    public Response query(ApiContent apiContent) {
        apiContent.setPageNum((apiContent.getPageNum() - 1) * apiContent.getPageSize());
        List<ApiContent> list = apiContentServer.apiContentList(apiContent);
        int count = apiContentServer.apiContentCount(apiContent);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return Response.success(jsonObject);
    }


    @PostMapping("/1addDafaApi")
    public Response add(ApiContent apiContent) {
        int i = apiContentServer.addApiContent(apiContent);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/1cloneApi")
    public Response cloneApi(int id) {
        int i = apiContentServer.cloneApi(id);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/1updateDafaApi")
    public Response update(ApiContent apiContent) {
        int i = apiContentServer.updateApiContent(apiContent);
        if (i == 1) {
            return Response.success("修改成功");
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/1testDafaApi")
    public void request(ApiContent apiContent, HttpServletResponse response, HttpServletRequest request) throws Exception {
        apiContent.setHost(apiContent.getHost().contains("http") ? apiContent.getHost() : String.format("%s%s", "http://",
                apiContent.getHost().replace("/", "").replace("?", "")));

        apiContent.setLoginReq(apiContent.getPath().endsWith("login"));//是否是登录请求
        HttpClientContext context = new HttpClientContext();
        String result = testApiServer.testApiOne(apiContent, request.getCookies(), context);
        logger.info("apiContent:" + apiContent.toString());

        //responset添加cookie,header添加JSESSIONID---------------------------------------------------------------------
        if (apiContent.isLoginReq()) {//
            //1.从context获取的cookie
            String responseCookie = "";
            List<Cookie> cookie1 = context.getCookieStore().getCookies();
            for (Cookie cookie2 : cookie1) {
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
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(result);//写入结果
    }

}
