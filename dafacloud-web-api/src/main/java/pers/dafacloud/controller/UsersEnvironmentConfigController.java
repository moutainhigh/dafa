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
import pers.dafacloud.entity.UsersEnvironmentConfig;
import pers.dafacloud.server.UserParametersServer;
import pers.dafacloud.server.UsersEnvironmentConfigServer;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1")
public class UsersEnvironmentConfigController {

    @Autowired
    UsersEnvironmentConfigServer usersEnvironmentConfigServer;

    @GetMapping("/getUsersEnvironmentConfigList")
    public Response getUserParametersList(String username) {
        List<UsersEnvironmentConfig> list = usersEnvironmentConfigServer.getUsersEnvironmentConfigList(username);
        int total = usersEnvironmentConfigServer.getUsersEnvironmentConfigListCount(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        jsonObject.put("total", total);
        return Response.success(jsonObject);
    }

    @PostMapping("/addUsersEnvironmentConfig")
    public Response addUserParameters(UsersEnvironmentConfig usersEnvironmentConfig, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        usersEnvironmentConfig.setUsername(userInfo.getUsername());
        usersEnvironmentConfig.setEvState(0);
        int count = usersEnvironmentConfigServer.addUsersEnvironmentConfig(usersEnvironmentConfig);
        if (count == 1) {
            //UserParametersMap.map.put((userParameters.getParameterType() == 1 ? "sys" : userParameters.getUsername()) + "_" + userParameters.getParameterName()
            //        , userParameters.getParameterValue());
        }
        return Response.success("新增成功");
    }

    @PostMapping("/updateUsersEnvironmentConfig")
    public Response updateUserParameters(UsersEnvironmentConfig usersEnvironmentConfig, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        UsersEnvironmentConfig usersEnvironmentConfig0 = usersEnvironmentConfigServer.getUsersEnvironmentConfigById(usersEnvironmentConfig.getId());
        if (usersEnvironmentConfig0 == null) {
            return Response.fail("数据已不存在，请重新获取");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (!userInfo.getUsername().equals(usersEnvironmentConfig0.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }
        if (usersEnvironmentConfig0.getEvState() == 1) {
            UserParametersMap.evMap.put(usersEnvironmentConfig0.getUsername() + "_" + usersEnvironmentConfig.getEvName(), usersEnvironmentConfig);
        }
        usersEnvironmentConfig.setUsername(userInfo.getUsername());
        int count = usersEnvironmentConfigServer.updateUsersEnvironmentConfig(usersEnvironmentConfig);
        if (count == 1) {

        }
        return Response.success("修改成功");
    }

    @PostMapping("/updateUsersEnvironmentConfigState")
    public Response updateUsersEnvironmentConfigState(String id, int evState, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        UsersEnvironmentConfig usersEnvironmentConfig0 = usersEnvironmentConfigServer.getUsersEnvironmentConfigById(id);
        if (usersEnvironmentConfig0 == null) {
            return Response.fail("数据已不存在，请重新获取");
        }
        if (!userInfo.getUsername().equals("duke")) {
            if (!userInfo.getUsername().equals(usersEnvironmentConfig0.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }
        int resetCount = -1;
        int count = -1;
        if (evState == 1) {
            long start = System.currentTimeMillis();
            resetCount = usersEnvironmentConfigServer.resetUsersEnvironmentConfigState(usersEnvironmentConfig0.getUsername(), usersEnvironmentConfig0.getEvName());
            count = usersEnvironmentConfigServer.updateUsersEnvironmentConfigState(id, evState);
            System.out.println("it consumes " + (System.currentTimeMillis() - start) + "ms");
        } else if (evState == 0) {
            count = usersEnvironmentConfigServer.updateUsersEnvironmentConfigState(id, evState);
        }
        System.out.println("重置" + resetCount + "条,设置" + count + "条");
        if (count == 1) {
            UserParametersMap.evMap.put(usersEnvironmentConfig0.getUsername() + "_" + usersEnvironmentConfig0.getEvName(), usersEnvironmentConfig0);
        }
        return Response.success("修改成功");
    }

    @PostMapping("/deleteUsersEnvironmentConfig")
    public Response deleteUserParameters(String id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        UsersEnvironmentConfig usersEnvironmentConfig = usersEnvironmentConfigServer.getUsersEnvironmentConfigById(id);
        if (usersEnvironmentConfig == null) {
            return Response.fail("数据已不存在，请重新获取");
        }

        if (!userInfo.getUsername().equals("duke")) {

            if (!userInfo.getUsername().equals(usersEnvironmentConfig.getUsername())) {
                return Response.fail("您无权限操作此数据");
            }
        }
        int count = usersEnvironmentConfigServer.deleteUsersEnvironmentConfig(id);
        if (usersEnvironmentConfig.getEvState() == 1) {
            UserParametersMap.evMap.remove(usersEnvironmentConfig.getUsername() + "_" + usersEnvironmentConfig.getEvName());
        }
        return Response.success("修改成功");
    }
}
