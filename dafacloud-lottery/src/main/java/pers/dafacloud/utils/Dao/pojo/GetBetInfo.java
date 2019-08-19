package pers.dafacloud.utils.Dao.pojo;

public class GetBetInfo {

    private String lotteryCode;
    private String playDetailCode;
    private String bettingNumber;
    private String bettingAmount;
    private String bettingCount;
    private String graduationCount;
    private String bettingUnit;


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
