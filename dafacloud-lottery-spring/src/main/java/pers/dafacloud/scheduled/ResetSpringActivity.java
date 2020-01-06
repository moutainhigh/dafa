package pers.dafacloud.scheduled;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.LotteryOpenMessageMapper;
import pers.dafacloud.mapper.ResetSpringActivityMapper;

@Service
public class ResetSpringActivity {

    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    private static ResetSpringActivityMapper resetSpringActivityMapper = sqlSession.getMapper(ResetSpringActivityMapper.class);


    //@Scheduled(cron = "*/10 * * * * * ")
    public static void function01() {
        int i = resetSpringActivityMapper.updateResetBonus();
        System.out.println(i);
    }

}
