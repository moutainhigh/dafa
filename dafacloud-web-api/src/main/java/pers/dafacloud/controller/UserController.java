package pers.dafacloud.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.userMapper.UserMapper;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    //请求
    @GetMapping(value = "/login")
    public String login(String userName,String password) {
        Map map = new HashMap();
        map.put("username",userName);
        userMapper.getUser(map);
        return "";
    }

}
