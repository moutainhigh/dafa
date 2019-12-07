package pers.testProductBug;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.InRecord.InRecordMapper;
import pers.dafacloud.Dao.mapper.gameBettingInfo.GameBetingInfoMapper;
import pers.dafacloud.Dao.mapper.lotteryBettingInfo.LotteryBetingInfoMapper;
import pers.dafacloud.Dao.mapper.summaryPaymentRecord.SummaryPaymentRecordMapper;
import pers.dafacloud.Dao.pojo.InRecord;
import pers.dafacloud.Dao.pojo.SummaryPaymentRecord;
import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.TwoListDiffent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 对比获取交易记录和报表数据差异
 * 需要修改sql
 */

public class PaymentRecordNotInReport {

    /**
     * dafacloud_report.in_record 只保留3天的数据
     * 充值数据和报表的数据对不上，大部分原因是队列丢失，
     * 需要修改sql
     */
    @Test(description = "获取充值记录的record_code")
    public static void test01() {
        //交易库
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("transaction");
        SummaryPaymentRecordMapper summaryPaymentRecordMapper = sqlSessionTransaction.getMapper(SummaryPaymentRecordMapper.class);
        //获取充值记录的record_code
        List<SummaryPaymentRecord> SummaryPaymentRecords = summaryPaymentRecordMapper.getRecordCode();
        List<String> SummaryPaymentRecordsNew = new ArrayList<>();//只取record
        for (SummaryPaymentRecord summaryPaymentRecord : SummaryPaymentRecords) {
            SummaryPaymentRecordsNew.add(summaryPaymentRecord.getRecordCode());
        }
        System.out.println(SummaryPaymentRecordsNew.size());
        //报表库
        SqlSession sqlSessionReport = SqlSessionFactoryUtils.openSqlSession("report");
        InRecordMapper inRecordMapper = sqlSessionReport.getMapper(InRecordMapper.class);
        //报表dafacloud_report.in_record 表
        List<InRecord> inRecords = inRecordMapper.getId(SummaryPaymentRecordsNew);
        System.out.println(inRecords.size());
        List<String> inRecordsNew = new ArrayList<>();//只取id，对应record
        for (InRecord inRecord : inRecords) {
            inRecordsNew.add(inRecord.getId());
        }
        System.out.println(SummaryPaymentRecordsNew.remove(1));//测试
        System.out.println(SummaryPaymentRecordsNew.size());
        List<String> listNew0 = TwoListDiffent.getDifferListByLoop(SummaryPaymentRecordsNew, inRecordsNew);//a有，b没有，b遗漏的
        List<String> listNew1 = TwoListDiffent.getDifferListByLoop(inRecordsNew, SummaryPaymentRecordsNew);
        System.out.println(listNew0);
        System.out.println(listNew1);
    }

    @Test(description = "测试")
    public static void test02() {
        List<String> list = new ArrayList<>();
        list.add("RK380176541967801601");
        list.add("22");
        SqlSession sqlSessionReport = SqlSessionFactoryUtils.openSqlSession("report");
        InRecordMapper inRecordMapper = sqlSessionReport.getMapper(InRecordMapper.class);
        List<InRecord> inRecords = inRecordMapper.getId(list);
        System.out.println(inRecords.size());

    }

    public static void main(String[] args) {

        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBetingInfoMapper lotteryBetingInfoMapper = sqlSessionTransaction.getMapper(LotteryBetingInfoMapper.class);
        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        long l = 0;
        for (int i = 0; i < tenantCodes.size(); i++) {
            int total = lotteryBetingInfoMapper.getLotteryBetingInfoCount(tenantCodes.get(i));
            l = l + total;
            System.out.println(l + " - " + total);
        }


    }

    /**
     * 线上彩票数据 导入到数据库
     */
    public static void LotteryRecordInfo() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("betting");
        LotteryBetingInfoMapper lotteryBetingInfoMapper = sqlSessionTransaction.getMapper(LotteryBetingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        LotteryBetingInfoMapper lotteryBetingInfoMapper2 = sqlSessionTransaction2.getMapper(LotteryBetingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        for (int i = 0; i < tenantCodes.size(); i++) {
            System.out.print("当前：" + i);
            getInsetLotteryBettingInfo(lotteryBetingInfoMapper, lotteryBetingInfoMapper2, tenantCodes.get(i));
        }
    }

    /**
     * 线上棋牌数据 导入到数据库
     */
    public static void gameRecordInfo() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("lotteryGame");
        GameBetingInfoMapper gameBetingInfoMapper = sqlSessionTransaction.getMapper(GameBetingInfoMapper.class);
        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        GameBetingInfoMapper gameBetingInfoMapper2 = sqlSessionTransaction2.getMapper(GameBetingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(PaymentRecordNotInReport.class.getResourceAsStream("/tenantCode.txt"));
        for (int i = 0; i < tenantCodes.size(); i++) {
            System.out.print(i);
            insert(gameBetingInfoMapper, gameBetingInfoMapper2, tenantCodes.get(i));
        }
        //System.out.println(tenantCodes);
    }

    /**
     * 查询
     */
    public static void insert(GameBetingInfoMapper gameBetingInfoMapper,
                              GameBetingInfoMapper gameBetingInfoMapper2, String tenantCode) {
        List<Map> list = gameBetingInfoMapper.getGameBetingInfo(tenantCode);
        System.out.print("-查询数据" + list.size());
        if (list.size() == 0) {
            System.out.println(list.size() + "-" + tenantCode);
            return;
        }
        int result = gameBetingInfoMapper2.insertGameBetingInfo(list);
        System.out.println("-" + result + "-" + tenantCode);

    }

    /**
     * 查询
     */
    public static void getInsetLotteryBettingInfo(LotteryBetingInfoMapper lotteryBetingInfoMapper,
                                                  LotteryBetingInfoMapper lotteryBetingInfoMapper2, String tenantCode) {
        List<Map> list = lotteryBetingInfoMapper.getLotteryBetingInfo(tenantCode);
        System.out.print("-查询数据" + list.size());
        if (list.size() == 0) {
            System.out.println(list.size() + "-" + tenantCode);
            return;
        }
        int result = lotteryBetingInfoMapper2.insertLotteryBetingInfo(list);
        System.out.println("-" + result + "-" + tenantCode);

    }

}
