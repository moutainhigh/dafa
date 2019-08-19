package pers.testProductBug;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.utils.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.utils.Dao.mapper.InRecordMapper;
import pers.dafacloud.utils.Dao.mapper.SummaryPaymentRecordMapper;
import pers.dafacloud.utils.Dao.pojo.InRecord;
import pers.dafacloud.utils.Dao.pojo.SummaryPaymentRecord;
import pers.utils.listUtils.TwoListDiffent;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取交易记录和报表数据差异
 */

public class PaymentRecordNotInReport {

    /**
     * dafacloud_report.in_record 只保留3天的数据
     * */
    @Test(description = "获取充值记录的record_code")
    public static void test01() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("transaction");
        SummaryPaymentRecordMapper summaryPaymentRecordMapper = sqlSessionTransaction.getMapper(SummaryPaymentRecordMapper.class);
        //获取充值记录的record_code
        List<SummaryPaymentRecord> SummaryPaymentRecords = summaryPaymentRecordMapper.getRecordCode();
        List<String> SummaryPaymentRecordsNew = new ArrayList<>();
        for (SummaryPaymentRecord summaryPaymentRecord : SummaryPaymentRecords) {
            SummaryPaymentRecordsNew.add(summaryPaymentRecord.getRecordCode());
        }
        System.out.println(SummaryPaymentRecordsNew.size());

        SqlSession sqlSessionReport = SqlSessionFactoryUtils.openSqlSession("report");
        InRecordMapper inRecordMapper = sqlSessionReport.getMapper(InRecordMapper.class);
        List<InRecord> inRecords = inRecordMapper.getId(SummaryPaymentRecordsNew);
        System.out.println(inRecords.size());
        List<String> inRecordsNew = new ArrayList<>();
        for(InRecord inRecord:inRecords){
            inRecordsNew.add(inRecord.getId());
        }
        System.out.println(SummaryPaymentRecordsNew.remove(1));
        System.out.println(SummaryPaymentRecordsNew.size());
        List<String> listNew0 = TwoListDiffent.getDifferListByLoop(SummaryPaymentRecordsNew,inRecordsNew);//a有，b没有，b遗漏的
        List<String> listNew1 = TwoListDiffent.getDifferListByLoop(inRecordsNew,SummaryPaymentRecordsNew);
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

}
