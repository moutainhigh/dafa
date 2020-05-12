package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.model.ApiManage;
import pers.dafacloud.server.ApiManageServer;
import pers.dafacloud.server.TestRequestApiServer;
import pers.dafacloud.server.TestRequestApiServer0;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ApiManageController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ApiManageServer apiManageServer;

    @Autowired
    TestRequestApiServer0 testRequestApiServer;

    @GetMapping("/apiManageBatchTest")
    public Response queryApiBatchTest(String host, String cookie, String testBatch, String groupsApi, String owner) throws Exception {
        return testRequestApiServer.apiManageBatchTest(host, cookie, testBatch, groupsApi, owner);
    }

    @PostMapping("/testRequestApi")
    public Response testRequestApi(int id, String host, String cookie, HttpServletRequest request) throws Exception {
        request.getCookies();
        if (StringUtils.isEmpty(cookie)) {
            if (request.getCookies() != null) {
                for (javax.servlet.http.Cookie requestCookie0 : request.getCookies()) {
                    if ("JSESSIONID".equals(requestCookie0.getName())) {
                        cookie = requestCookie0.getValue(); //c_sessionId
                    }
                }
            }
        }
        return testRequestApiServer.testApiOne(id, host, cookie);
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
    public Response add(ApiManage apiManage) {
        int i = apiManageServer.addApi(apiManage);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/cloneApi")
    public Response cloneApi(int id) {
        int i = apiManageServer.cloneApi(id);
        if (i == 1) {
            return Response.success("新增成功");
        } else {
            return Response.fail("新增失败");
        }
    }

    @PostMapping("/updateApi")
    public Response update(ApiManage apiManage) {
        int i = apiManageServer.updateApi(apiManage);
        if (i == 1) {
            return Response.success("修改成功");
        } else {
            return Response.fail("修改失败");
        }
    }


}
