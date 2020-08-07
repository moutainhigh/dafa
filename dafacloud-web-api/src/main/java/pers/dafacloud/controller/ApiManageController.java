package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
public class ApiManageController {

    @Autowired
    ApiManageServer apiManageServer;

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
        request.getCookies();
        if (StringUtils.isEmpty(cookie)) {
            if (request.getCookies() != null) {
                for (javax.servlet.http.Cookie requestCookie0 : request.getCookies()) {
                    if ("JSESSIONID".equals(requestCookie0.getName())) {
                        cookie = requestCookie0.getValue();
                    }
                }
            }
        }
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


    @GetMapping("/getApiList")
    public Response getApiList(ApiManage apiManage) {
        apiManage.setPageNum((apiManage.getPageNum() - 1) * apiManage.getPageSize());
        List<ApiManage> list = apiManageServer.getApiList(apiManage);
        int count = apiManageServer.getApiListCount(apiManage);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", count);
        jsonObject.put("list", list);
        return Response.success(jsonObject);
    }

    @GetMapping("/getApiById")
    public Response query(int id) {
        ApiManage apiManage = apiManageServer.getApiById(id);
        return Response.success(apiManage.toString());
    }


    @PostMapping("/addApi")
    public Response add(ApiManage apiManage, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        apiManage.setOwner(userInfo.getUsername());
        int i = apiManageServer.addApi(apiManage);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/cloneApi")
    public Response cloneApi(int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        int i = apiManageServer.cloneApi(id, userInfo.getUsername());
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/updateApi")
    public Response update(ApiManage apiManage, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (StringUtils.isEmpty(apiManage.getOwner()) || !apiManage.getOwner().equals(userInfo.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }

        int i = apiManageServer.updateApi(apiManage);
        if (i == 1) {
            return Response.success("修改成功");
        } else {
            return Response.fail("修改失败");
        }
    }

    @PostMapping("/deleteApi")
    public Response deleteApi(int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        ApiManage apiManage = apiManageServer.getApiById(id);

        if (apiManage == null) {
            return Response.fail("数据不存在，请重新获取数据");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (StringUtils.isEmpty(apiManage.getOwner()) || !apiManage.getOwner().equals(userInfo.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }
        //if (!userInfo.getUsername().equals("duke")) {
        //}
        int i = apiManageServer.deleteApi(id);
        if (i == 1) {
            return Response.returnData("删除成功", 1, null);
        } else {
            return Response.fail("数据不存在");
        }
    }


}
