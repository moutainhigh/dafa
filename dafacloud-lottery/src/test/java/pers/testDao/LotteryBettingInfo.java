package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.lotteryBettingInfo.LotteryBettingInfoMapper;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotteryBettingInfo {
    private static ExecutorService excutors = Executors.newFixedThreadPool(300);

    /**
     * 获取数据量
     */
    public static void lotteryBettingInfoCount() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBetingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);
        List<String> tenantCodes = FileUtil.readFile(LotteryBettingInfo.class.getResourceAsStream("/tenantCode.txt"));
        long l = 0;
        for (int i = 0; i < tenantCodes.size(); i++) {
            int total = lotteryBetingInfoMapper.getLotteryBetingInfoCount(tenantCodes.get(i));
            l = l + total;
            System.out.println(l + " - " + total);
        }
    }

    /**
     * 查询所有，直接导入
     */
    private static void getInsetLotteryBettingInfo() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBettingInfoMapper lotteryBettingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBettingInfoMapper lotteryBettingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(LotteryBettingInfo.class.getResourceAsStream("/tenantCode.txt"));
        System.out.println("tenantCodes：" + tenantCodes);
        for (int i = 0; i < tenantCodes.size(); i++) {
            System.out.println("当前：" + i);
            String tenantCode = tenantCodes.get(i);
            List<Map> list = lotteryBettingInfoMapper.getLotteryBetingInfo(tenantCode);
            System.out.println(tenantCode + "查询数据量" + list.size());
            if (list.size() == 0) {
                System.out.println(tenantCode + " -  " + list.size());
                continue;
            }
            int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
            System.out.println(tenantCode + "- 写入数据量 - " + result + " - ");
        }

    }


    /**
     * 分站，分天，每10000条写入一次
     */
    public static void getInsetLotteryBettingInfoLoop(String date) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        //SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("bettingHistory");
        LotteryBettingInfoMapper lotteryBettingInfoMapper = sqlSessionTransaction.getMapper(LotteryBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBettingInfoMapper lotteryBettingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(LotteryBettingInfo.class.getResourceAsStream("/tenantCode.txt"));
        System.out.println("数据量：" + tenantCodes.size());
        for (int i = 0; i < tenantCodes.size(); i++) {
            String tenantCode = tenantCodes.get(i);
            System.out.println(tenantCode + " - " + date + " 当前: " + i);
            String maxId = "0";
            for (int j = 0; j < 10000; j++) {
                System.out.println("maxId :" + maxId);
                List<Map> list = lotteryBettingInfoMapper.getLotteryBetingInfoDx(tenantCode, date, maxId);
                System.out.println(date + " - " + tenantCode + " - 查询数据 " + list.size());
                if (list.size() == 0) {
                    break;
                }
                if (list.size() < 10000) {
                    int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
                    System.out.println(tenantCode + " - " + date + " 写入尾数- " + result);
                    list.clear();
                    break;
                } else {
                    int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(list);
                    System.out.println(tenantCode + " - " + date + " 写入整数 - " + result);
                }
                maxId = list.get(list.size() - 1).get("id").toString();
                list.clear();
            }
            //List<List<Map>> lists = ListSplit.split(list, 10000);
            //for (int i = 0; i < lists.size(); i++) {
            //    int result = lotteryBettingInfoMapper2.insertLotteryBetingInfo(lists.get(i));
            //    System.out.println(date + "写入-" + result + "-" + tenantCode);
            //}
        }


    }

    public static void main(String[] args) {
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-11"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-10"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-09"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-08"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-07"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-06"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-05"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-04"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-03"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-02"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-01"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-04-17"));
        excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-06-24"));

        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-31"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-30"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-29"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-28"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-27"));

        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-26"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-25"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-24"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-23"));
        //excutors.execute(() -> getInsetLotteryBettingInfoLoop("2020-03-22"));


        //getInsetLotteryBettingInfo();

    }
}
