package pers.dafacloud.controller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.ResetSpringActivityMapper;

import javax.annotation.Resource;


@RestController
@RequestMapping("/v1")
public class SpringActivityController {
    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    private static ResetSpringActivityMapper resetSpringActivityMapper = sqlSession.getMapper(ResetSpringActivityMapper.class);

    //@Resource
    //ResetSpringActivityMapper resetSpringActivityMapper;

    @GetMapping("/resetSpringActivity")
    public String function01() {
        int i1 = resetSpringActivityMapper.updateResetBonus();
        int i2 = resetSpringActivityMapper.updateResetLog();
        int i3 = resetSpringActivityMapper.updateResetAvailableNmber();
        return String.format("重置成功: updateResetBonus[%s],updateResetLog[%s],updateResetAvailableNmber[%s]",i1,i2,i3) ;


    }


}
