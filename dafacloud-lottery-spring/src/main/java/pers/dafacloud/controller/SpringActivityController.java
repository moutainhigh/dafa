package pers.dafacloud.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.ResetSpringActivityMapper;
import pers.dafacloud.server.ResetSpringActivityServer;


@RestController
@RequestMapping("/v1")
public class SpringActivityController {
    //private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    //private static ResetSpringActivityMapper resetSpringActivityMapper = sqlSession.getMapper(ResetSpringActivityMapper.class);

    //@Resource
    //ResetSpringActivityMapper resetSpringActivityMapper;
    //@Autowired
    //ResetSpringActivityServer resetSpringActivityServer;

    @GetMapping("/resetSpringActivity")
    public String function01() {
        //resetSpringActivityServer.resetSpringActivity();
        //int i1 = resetSpringActivityServer.updateResetBonus();
        //int i2 = resetSpringActivityServer.updateResetLog();
        //int i3 = resetSpringActivityServer.updateResetAvailableNmber();
        return String.format("重置成功: updateResetBonus[%s],updateResetLog[%s],updateResetAvailableNmber[%s]", 1, 2, 3);


    }


}
