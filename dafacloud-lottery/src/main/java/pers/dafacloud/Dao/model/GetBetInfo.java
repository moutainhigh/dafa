package pers.dafacloud.Dao.model;

public class GetBetInfo {

    private String lotteryCode;
    private String playDetailCode;
    private String bettingNumber;
    private String bettingAmount;
    private String bettingCount;
    private String graduationCount;
    private String bettingUnit;

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRecordCode() {
        return recordCode;
    }

    public void setRecordCode(String recordCode) {
        this.recordCode = recordCode;
    }

    public String getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(String returnAmount) {
        this.returnAmount = returnAmount;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    private String tenantCode;
    private String username;
    private String recordCode;
    //private String bettingAmount; 已有
    private String returnAmount;
    private  String gmtModified;






    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getPlayDetailCode() {
        return playDetailCode;
    }

    public void setPlayDetailCode(String playDetailCode) {
        this.playDetailCode = playDetailCode;
    }

    public String getBettingNumber() {
        return bettingNumber;
    }

    public void setBettingNumber(String bettingNumber) {
        this.bettingNumber = bettingNumber;
    }

    public String getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(String bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    public String getBettingCount() {
        return bettingCount;
    }

    public void setBettingCount(String bettingCount) {
        this.bettingCount = bettingCount;
    }

    public String getGraduationCount() {
        return graduationCount;
    }

    public void setGraduationCount(String graduationCount) {
        this.graduationCount = graduationCount;
    }

    public String getBettingUnit() {
        return bettingUnit;
    }

    public void setBettingUnit(String bettingUnit) {
        this.bettingUnit = bettingUnit;
    }


    @Override
    public String toString() {
        return "GetBetInfo{" +
                "lotteryCode='" + lotteryCode + '\'' +
                ", playDetailCode='" + playDetailCode + '\'' +
                ", bettingNumber='" + bettingNumber + '\'' +
                ", bettingAmount='" + bettingAmount + '\'' +
                ", bettingCount='" + bettingCount + '\'' +
                ", graduationCount='" + graduationCount + '\'' +
                ", bettingUnit='" + bettingUnit + '\'' +
                '}';
    }
}
