package pers.dafacloud.betDafa;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.pojo.BetConentFromTest;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.GetBetInfoMapper;
import pers.dafacloud.Dao.pojo.GetBetInfo;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.timeUtils.TimeUtil;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GetBetDataFromPro {

    public static void main(String[] args) throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("betting");
        GetBetInfoMapper getBetInfoMapper = sqlSession.getMapper(GetBetInfoMapper.class);
        List<GetBetInfo> list = getBetInfoMapper.getBetInfo();
        List<BetConentFromTest> betConentFromTestList = new ArrayList<>();
        FileOutputStream fos = new FileOutputStream("/Users/duke/Documents/1407.txt", true);
        for (GetBetInfo getBetInfo : list) {
            BetConentFromTest betConentFromTest = new BetConentFromTest();
            betConentFromTest.setGetBetInfo(getBetInfo);
            System.out.println(
            );
            String s = getBetInfo.getLotteryCode() + "`" +
                    getBetInfo.getPlayDetailCode() + "`" +
                    getBetInfo.getBettingNumber() + "`" +
                    getBetInfo.getBettingAmount() + "`" +
                    getBetInfo.getBettingCount() + "`" +
                    getBetInfo.getGraduationCount() + "`" +
                    getBetInfo.getBettingUnit();
            System.out.println(betConentFromTest);
            betConentFromTestList.add(betConentFromTest);
            fos.write(s.getBytes());
            fos.write("\r".getBytes());
        }
        fos.close();
    }

    @Test(description = "幸运飞艇凌晨撤单数据统计700个站")
    public static void test01() throws Exception {
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("betting");
        GetBetInfoMapper getBetInfoMapper = sqlSession.getMapper(GetBetInfoMapper.class);
        List<String> tenantcodes = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/tenantCodePro.txt");
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

    @Test(description = "测试")
    public static void test02() {
        long now = System.currentTimeMillis();

        long lcMillTime = 0;
        String currentDate = "";
        try {
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
            lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
            Date date = new Date();
            currentDate = sdfOne.format(date);
            //System.out.println(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int second = (int) (now - lcMillTime) / 1000;//距离今日凌晨秒数
        int issueOneNum = second / 60 + 1;//期数

        int issueFiveNum = second / (60 * 5) + 1;//期数
        System.out.println(issueFiveNum);
        System.out.println(issueOneNum);
    }

    @Test(description = "测试")
    public static void test03() {
        String getServerTimeMillisecond = "http://52.76.195.164:8020/v1/betting/getServerTimeMillisecond";
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("x-user-id", "50352461")
                .other("x-tenant-code", "shalv010")
                .other("x-user-name", "shalv010a0002")
                .other("x-source-Id", "1")
                .other("Origin", "http://52.76.195.164")
                .build();
        String result = DafaRequest.get(getServerTimeMillisecond);

        System.out.println(TimeUtil.millSecondToDateString(JSONObject.fromObject(result).getLong("data")));
        System.out.println(result);
    }

    @Test
    public void test() throws  Exception{
        String dateTime = "2019-08-28 13:43:24";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
        long now = calendar.getTimeInMillis();
        System.out.println("毫秒：" + now);

        long lcMillTime = 0;
        String currentDate = "";
        try {
            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
            lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
            Date date = new Date();
            currentDate = sdfOne.format(date);
            //System.out.println(currentDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int second = (int) (now - lcMillTime) / 1000;//距离今日凌晨秒数
        int issueOneNum = second / 60 + 1;//期数

        int issueFiveNum = second / (60 * 5) + 1;//期数
        System.out.println(issueFiveNum);
        System.out.println(issueOneNum);
    }
}
