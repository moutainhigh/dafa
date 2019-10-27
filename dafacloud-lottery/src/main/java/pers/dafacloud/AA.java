package pers.dafacloud;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.getBetInfo.GetBetInfoMapper;
import pers.dafacloud.Dao.pojo.GetBetInfo;
import pers.dafacloud.pojo.BetConentFromTest;
import pers.utils.fileUtils.FileUtil;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AA {

    public static void main(String[] args) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("betting");
        GetBetInfoMapper getBetInfoMapper = sqlSession.getMapper(GetBetInfoMapper.class);
        List<String> tenantcodes = FileUtil.readFile(AA.class.getResourceAsStream("/tenantCodePro.txt"));
        List<String> resultAll = new ArrayList<>();
        //for (String tenantCode : tenantcodes) {
        for (int i = 0; i < tenantcodes.size(); i++) {
            System.out.println(i);
            List<GetBetInfo> list = getBetInfoMapper.getRecord(tenantcodes.get(i));
            List<BetConentFromTest> betConentFromTestList = new ArrayList<>();
            for (GetBetInfo getBetInfo : list) {
                BetConentFromTest betConentFromTest = new BetConentFromTest();
                betConentFromTest.setGetBetInfo(getBetInfo);
                //System.out.println();
                String s = getBetInfo.getTenantCode() + "," +
                        getBetInfo.getUsername() + "," +
                        getBetInfo.getRecordCode() + "," +
                        getBetInfo.getBettingAmount() + "," +
                        //getBetInfo.getReturnAmount()+","+
                        getBetInfo.getGmtModified();
                //System.out.println(betConentFromTest);
                betConentFromTestList.add(betConentFromTest);
                resultAll.add(s);
            }
        }
        FileOutputStream fos = new FileOutputStream("/Users/duke/Documents/resultAll.txt", false);
        for (String s : resultAll) {
            fos.write(s.getBytes());
            fos.write("\r".getBytes());
        }
        fos.close();
    }

}
