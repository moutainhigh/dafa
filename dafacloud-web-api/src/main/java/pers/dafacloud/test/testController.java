package pers.dafacloud.test;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.model.ApiContent;
import pers.dafacloud.utils.SqlSessionFactoryUtils;

import java.util.List;

public class testController {

    public static void main(String[] args) {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        ApiContentMapper apiContentMapper = sqlSession.getMapper(ApiContentMapper.class);
    }
}
