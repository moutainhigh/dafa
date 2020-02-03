package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.mapper.ResetSpringActivityMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;

@Service
public class ResetSpringActivityServer {

    @Resource
    ResetSpringActivityMapper resetSpringActivityMapper;

    /**
     * 重置春节抽奖数据
     */
    @MyDataSource("dev1")
    public void resetSpringActivity() {
        resetSpringActivityMapper.updateResetBonus();
        resetSpringActivityMapper.updateResetAvailableNmber();
        resetSpringActivityMapper.updateResetLog();
    }


}
