package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.config.Audience;
import pers.dafacloud.entity.User;
import pers.dafacloud.server.UserService;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class LoginController {
    private static Map<String, HttpSession> userMap = new HashMap<>();

    @Autowired
    UserService userService;

    @Autowired
    private Audience audience;


    @PostMapping("/login")
            public Response login(String username, String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Response.returnData("账号或密码不能为空", -1, null);
        }
        //System.out.println(username + "," + password);
        User user = userService.findByUsername(username);
        if (user == null) {
            return Response.returnData("用户不存在", -1, null);
        }
        if (!password.equals(user.getPassword())) {
            return Response.returnData("输入密码不正确", -1, null);
        }

        //request.getSession().setAttribute("user", user);//用户名存入该用户的session 中
        HttpSession httpSession = request.getSession();
        //System.out.println("1:"+httpSession.getId());
        if (userMap.containsKey(user.getUserId())) {
            HttpSession httpSession0 = userMap.remove(user.getUserId());
            if (!httpSession0.getId().equals(httpSession.getId())) {
                try {
                    httpSession0.invalidate();
                } catch (Exception e) {
                    System.out.println("历史session已经过期");
                }


                //System.out.println("2:"+httpSession0.getId());
                //Object userInfo = httpSession0.getAttribute("user");
                //if (userInfo != null) {
                //    httpSession0.invalidate();//原session失效
                //}
            }
        }
        httpSession.setAttribute("user", user);
        userMap.put(user.getUserId(), httpSession);
        //JSONObject jsonObject = new JSONObject();
        //String token = JwtTokenUtil.createJWT(user.getUserId(), username, "admin", audience);
        //jsonObject.put("x-token", token);
        return Response.returnData("登录成功", 1, user);
    }

    @GetMapping("/findMenuRole")
    public Response findMenuRole(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        String menuDetail;
        try {
            menuDetail = userService.findMenuRole(userInfo.getRoleId());
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取权限失败");
        }
        return Response.returnData("获取成功", 1, menuDetail);
    }

    @PostMapping("/updateUserPassword")
    public Response updateUserPassword(HttpServletRequest request, String password) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        int menuDetail;
        try {
            menuDetail = userService.updateUserPassword(userInfo.getUserId(), password);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("修改密码失败");
        }
        HttpSession session0 = userMap.remove(userInfo.getUserId());
        session0.invalidate();
        return Response.returnData("修改成功", 1, menuDetail);
    }

    @PostMapping("/loginOut")
    public Response loginOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        HttpSession session0 = userMap.remove(userInfo.getUserId());
        session0.invalidate();
        return Response.returnData("退出成功", 1, null);
    }

    @GetMapping("/getOwnerOpt")
    public Response getOwnerOpt() {
        List<Map> list = userService.getOwnerOpt();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        return Response.success(jsonObject);
        //return Response.returnData("获取成功", 1, list);
    }

    @GetMapping("/getUserList")
    public Response getUserList(String username) {
        List<Map> list = userService.getUserList(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", list);
        return Response.success(jsonObject);
    }

    @PostMapping("/addUser")
    public Response addUser(String username, String password, String roleId, String isTest) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(roleId) || StringUtils.isEmpty(isTest)) {
            return Response.fail("参数不能为空");
        }

        User user0 = userService.findByUsername(username);
        if (user0 != null) {
            return Response.fail("用户已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRoleId(roleId);
        user.setIsTest(isTest);
        int count = userService.addUser(user);
        if (count == 0) {
            return Response.success("新增失败");
        }
        return Response.success("新增成功");
    }

    @PostMapping("/resetPassword")
    public Response resetPassword(String userId, String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        if (!"1".equals(userInfo.getRoleId())) {
            return Response.fail("您无权限此操作");
        }
        int count = userService.resetPassword(userId, password);
        return Response.success("重置成功");
    }

}
