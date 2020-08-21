package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.entity.ApiManage;
import pers.dafacloud.entity.User;
import pers.dafacloud.server.ApiManageServer;
import pers.dafacloud.server.TestRequestApiServer0;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ApiRequestController {

    @Autowired
    TestRequestApiServer0 testRequestApiServer;

    @GetMapping("/apiManageBatchTest")
    public Response queryApiBatchTest(String hostCms, String hostFront, String frontUserName, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        return testRequestApiServer.apiManageBatchTest(hostCms, hostFront, frontUserName, userInfo.getUsername());
    }

    @PostMapping("/testRequestApi")
    public Response testRequestApi(int id, String host, String cookie, HttpServletRequest request) {
        //request.getCookies();
        //if (StringUtils.isEmpty(cookie)) {
        //    if (request.getCookies() != null) {
        //        for (javax.servlet.http.Cookie requestCookie0 : request.getCookies()) {
        //            if ("JSESSIONID".equals(requestCookie0.getName())) {
        //                cookie = requestCookie0.getValue();
        //            }
        //        }
        //    }
        //}
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        return testRequestApiServer.testApiOne(id, host, cookie, userInfo.getUsername());
    }
}
