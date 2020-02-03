package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.lotteryBettingInfo.LotteryBettingInfoMapper;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;

public class GetLotterybettingInfo {


    /**
     * 查询pro -> 写入test
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBetingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBettingInfoMapper lotteryBettingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(LotteryBettingInfo.class.getResourceAsStream("/tenantCode.txt"));
        for (String tenantCode : tenantCodes) {
            System.out.println(tenantCode);
            List<Map> maps = lotteryBetingInfoMapper.getLotteryBetingInfo(tenantCode);
            System.out.println(maps.size());
            if (maps.size() == 0) {
                //System.out.println("---------------------------------");
                continue;
            }
            int count = lotteryBettingInfoMapper2.insertLotteryBetingInfo(maps);
            System.out.println("insert :" + count);
            //System.out.println("---------------------------------");
        }
    }
}
