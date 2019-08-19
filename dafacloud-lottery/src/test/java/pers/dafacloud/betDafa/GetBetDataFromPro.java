package pers.dafacloud.betDafa;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.pojo.BetConentFromTest;
import pers.dafacloud.utils.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.utils.Dao.mapper.GetBetInfoMapper;
import pers.dafacloud.utils.Dao.mapper.LotteryConfigMapper;
import pers.dafacloud.utils.Dao.pojo.GetBetInfo;
import pers.dafacloud.utils.Dao.pojo.LotteryConfigPojo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GetBetDataFromPro {

    public static void main(String[] args) throws  Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        GetBetInfoMapper getBetInfoMapper = sqlSession.getMapper(GetBetInfoMapper.class);
        List<GetBetInfo> list = getBetInfoMapper.getBetInfo();
        List<BetConentFromTest> betConentFromTestList = new ArrayList<>();
        FileOutputStream fos = new FileOutputStream("/Users/duke/Documents/1412.txt",true);
        for (GetBetInfo getBetInfo : list) {
            BetConentFromTest betConentFromTest = new BetConentFromTest();
            betConentFromTest.setGetBetInfo(getBetInfo);
            System.out.println(
                    );
            String s = getBetInfo.getLotteryCode()+"`"+
                    getBetInfo.getPlayDetailCode()+ "`"+
                    getBetInfo.getBettingNumber()+"`"+
                    getBetInfo.getBettingAmount()+"`"+
                    getBetInfo.getBettingCount()+"`"+
                    getBetInfo.getGraduationCount()+"`"+
                    getBetInfo.getBettingUnit();
            System.out.println(betConentFromTest);
            betConentFromTestList.add(betConentFromTest);
            fos.write(s.getBytes());
            fos.write("\r".getBytes());
        }
        fos.close();

    }
}
