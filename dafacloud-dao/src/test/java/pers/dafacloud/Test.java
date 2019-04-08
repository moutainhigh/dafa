package pers.dafacloud;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import pers.dafacloud.mapper.UsersMapper;
import pers.dafacloud.pojo.HongHei;

public class Test {
    // 获得sqlSession
    //SqlSession sqlSession = getSqlSession();
    @Autowired
    JdbcTemplate jdbcTemplate;


    @org.junit.Test
    public void updatePassWord(){

        jdbcTemplate.update("update users set password = qweqwsczcz where user_name = dafai0000");

    }
}
