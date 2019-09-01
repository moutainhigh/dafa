package pers.dafacloud.Dao.pojo;

public class SummaryPaymentRecord {

    String recordCode;


    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }


    @Override
    public String toString() {
        return "SummaryPaymentRecord{" +
                "recordCode='" + recordCode + '\'' +
                '}';
    }
}
