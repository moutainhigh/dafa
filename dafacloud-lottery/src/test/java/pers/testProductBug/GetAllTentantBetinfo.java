package pers.testProductBug;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.lotteryConfig.LotteryConfigMapper;
import pers.dafacloud.Dao.pojo.LotteryConfigPojo;

import java.util.List;

public class GetAllTentantBetinfo {

    SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("betting");

    LotteryConfigMapper lotteryConfigMapper = sqlSession.getMapper(LotteryConfigMapper.class);
    List<LotteryConfigPojo> list = lotteryConfigMapper.getLotteryConfig();




}
