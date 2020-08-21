package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.bean.UserParametersMap;
import pers.dafacloud.entity.User;
import pers.dafacloud.entity.UserParameters;
import pers.dafacloud.server.UserParametersServer;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserParametersController {

    @Autowired
    UserParametersServer userParametersServer;

    @GetMapping("/getUserParametersList")
    public Response getUserParametersList(String username) {
        List<UserParameters> list = userParametersServer.getUserParametersList(username);
        int total = userParametersServer.getUserParametersCount(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("total", total);
        return Response.success(jsonObject);
    }

    @PostMapping("/addUserParameters")
    public Response addUserParameters(UserParameters userParameters, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        userParameters.setUsername(userInfo.getUsername());
        int count = userParametersServer.addUserParameters(userParameters);
        if (count == 1) {
            UserParametersMap.map.put((userParameters.getParameterType() == 1 ? "sys" : userParameters.getUsername()) + "_" + userParameters.getParameterName()
                    , userParameters.getParameterValue());
        }
        return Response.success("新增成功");
    }

    @PostMapping("/updateUserParameters")
    public Response updateUserParameters(UserParameters userParameters, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        UserParameters userParameters0 = userParametersServer.getUserParametersById(userParameters.getId());
        if (userParameters0 == null) {
            return Response.fail("数据已不存在，请重新获取");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (!userInfo.getUsername().equals(userParameters0.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }
        userParameters.setUsername(userInfo.getUsername());
        int count = userParametersServer.updateUserParameters(userParameters);
        if (count == 1) {
            UserParametersMap.map.put((userParameters.getParameterType() == 1 ? "sys" : userParameters.getUsername()) + "_" + userParameters.getParameterName()
                    , userParameters.getParameterValue());
        }
        return Response.success("修改成功");
    }

    @PostMapping("/deleteUserParameters")
    public Response deleteUserParameters(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        UserParameters userParameters = userParametersServer.getUserParametersById(id);
        if (userParameters == null) {
            return Response.fail("数据已不存在，请重新获取");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (!userInfo.getUsername().equals(userParameters.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }

        int count = userParametersServer.deleteUserParameters(id);
        if (count == 1) {
            UserParametersMap.map.remove((userParameters.getParameterType() == 1 ? "sys" : userParameters.getUsername()) + "_" + userParameters.getParameterName());
        }
        return Response.success("修改成功");
    }
}
