package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.lotteryBettingInfo.LotteryBettingInfoMapper;
import pers.testProductBug.PaymentRecordNotInReport;
import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.ListSplit;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotteryBettingInfo {
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    /**
     * 线上彩票下注记录，获取数据量
     */
    public static void lotteryBettingInfoCount() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBetingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);
        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        long l = 0;
        for (int i = 0; i < tenantCodes.size(); i++) {
            int total = lotteryBetingInfoMapper.getLotteryBetingInfoCount(tenantCodes.get(i));
            l = l + total;
            System.out.println(l + " - " + total);
        }
    }

    /**
     * 线上彩票下注记录，导入到数据库
     */
    public static void lotteryBettingInfo() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBettingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBettingInfoMapper lotteryBettingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        for (int i = 0; i < tenantCodes.size(); i++) {
            System.out.println("当前：" + i);
            getInsetLotteryBettingInfo(lotteryBettingInfoMapper, lotteryBettingInfoMapper2, tenantCodes.get(i));
        }
    }


    /**
     * 彩票下注记录，先查询线上数据再写入测试库
     *
     * @param lotteryBettingInfoMapper  线上库
     * @param lotteryBettingInfoMapper2 dev1库
     */
    public static void getInsetLotteryBettingInfo(LotteryBettingInfoMapper lotteryBettingInfoMapper,
                                                  LotteryBettingInfoMapper lotteryBettingInfoMapper2, String tenantCode) {
        List<Map> list = lotteryBettingInfoMapper.getLotteryBetingInfo(tenantCode);
        System.out.println("查询数据" + list.size());
        if (list.size() == 0) {
            System.out.println(list.size() + "-" + tenantCode);
            return;
        }
        int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
        System.out.println("-" + result + "-" + tenantCode);
    }

    /**
     * 线上彩票下注记录，导入到数据库
     */
    public static void lotteryBettingInfo2(String date) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBettingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBettingInfoMapper lotteryBettingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        for (int i = 0; i < 1; i++) {
            System.out.println("当前：" + i);
            getInsetLotteryBettingInfoLoop(lotteryBettingInfoMapper, lotteryBettingInfoMapper2, "huicai", date);
        }
    }

    /**
     * 彩票下注记录，先查询线上数据再写入测试库，每10000条写入一次
     *
     * @param lotteryBettingInfoMapper  线上库
     * @param lotteryBettingInfoMapper2 dev1库
     */
    public static void getInsetLotteryBettingInfoLoop(LotteryBettingInfoMapper lotteryBettingInfoMapper,
                                                      LotteryBettingInfoMapper lotteryBettingInfoMapper2, String tenantCode, String date) {
        String maxId = "0";
        for (int i = 0; i < 10000; i++) {
            System.out.println(maxId);
            List<Map> list = lotteryBettingInfoMapper.getLotteryBetingInfoDx(tenantCode, date, maxId);
            System.out.println(date + " - " + tenantCode + " - 查询数据" + list.size());
            if(list.size()==0){
                return;
            }
            if (list.size() < 10000 ) {
                int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
                System.out.println(date + "写入尾数-" + result + "-" + tenantCode);
                return;
            } else {
                int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
                System.out.println(date + "写入-" + result + "-" + tenantCode);
            }
            maxId = list.get(list.size()-1).get("id").toString();
        }


        //List<List<Map>> lists = ListSplit.split(list, 10000);
        //for (int i = 0; i < lists.size(); i++) {
        //    int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(lists.get(i));
        //    System.out.println(date + "写入-" + result + "-" + tenantCode);
        //}
    }

    public static void main(String[] args) {
        excutors.execute(() -> lotteryBettingInfo2("2019-11-29"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-11-30"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-01"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-02"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-03"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-04"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-05"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-06"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-07"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-08"));
        //excutors.execute(() -> lotteryBettingInfo2("2019-12-09"));
    }
}
