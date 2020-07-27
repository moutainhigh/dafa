package pers.dafacloud.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.config.Audience;
import pers.dafacloud.entity.User;
import pers.dafacloud.server.UserService;
import pers.dafacloud.utils.JwtTokenUtil;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private Audience audience;


    @PostMapping("/login")
    public Response login(String username, String password, HttpServletRequest request) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Response.returnData("账号或密码不能为空", -1, null);
        }
        System.out.println(username + "," + password);
        User user = userService.findByUsername(username);
        if (user == null) {
            return Response.returnData("用户不存在", -1, null);
        }
        if (!password.equals(user.getPassword())) {
            return Response.returnData("输入密码不正确", -1, null);
        }

        request.getSession().setAttribute("user",user);//用户名存入该用户的session 中

        JSONObject jsonObject = new JSONObject();
        String token = JwtTokenUtil.createJWT(user.getUserId(), username, "admin", audience);
        jsonObject.put("x-token", token);
        return Response.returnData("登录成功", 1, jsonObject);
    }
}
