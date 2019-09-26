package pers.dafacloud.dao;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.user.UserMapper;
import pers.dafacloud.Dao.pojo.User;

import java.util.List;

public class GetUser {

    @Test(description = "读取测试环境数据用户")
    public static void test01() {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        String name = "shalv";
        for (int i = 0; i < 10; i++) {
            List<User> users = userMapper.getUser(String.format("%s%03d", name, i), 100);
            System.out.println(users);
        }

    }



}
