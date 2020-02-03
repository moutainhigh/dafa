package pers.testProductBug;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.InRecord.InRecordMapper;
import pers.dafacloud.Dao.mapper.summaryPaymentRecord.SummaryPaymentRecordMapper;
import pers.dafacloud.Dao.model.InRecord;
import pers.dafacloud.Dao.model.SummaryPaymentRecord;
import pers.utils.listUtils.TwoListDiffent;

import java.util.ArrayList;
import java.util.List;

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


}
