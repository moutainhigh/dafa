package pers.dafagame.utils.Dao.pojo;

public class LotteryConfigPojo {

    private String lotteryCode;
    private String lotteryClass;
    private String lotteryClassName;
    private String lotteryType;
    private String lotteryName;
    private String lotteryExplain;
    private String lotteryIntro;
    private String lotteryContent;
    private String totalOpenDaily;
    private String imagePath;
    private String startIssue;
    private String startTime;
    private String isStop;
    private String gmtCreated;
    private String gmtModified;
//类型和字段


    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getLotteryClass() {
        return lotteryClass;
    }

    public void setLotteryClass(String lotteryClass) {
        this.lotteryClass = lotteryClass;
    }

    public String getLotteryClassName() {
        return lotteryClassName;
    }

    public void setLotteryClassName(String lotteryClassName) {
        this.lotteryClassName = lotteryClassName;
    }

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

    public String getLottreyName() {
        return lotteryName;
    }

    public void setLottreyName(String lottreyName) {
        this.lotteryName = lotteryName;
    }

    public String getLotteryExplain() {
        return lotteryExplain;
    }

    public void setLotteryExplain(String lotteryExplain) {
        this.lotteryExplain = lotteryExplain;
    }

    public String getLotteryIntro() {
        return lotteryIntro;
    }

    public void setLotteryIntro(String lotteryIntro) {
        this.lotteryIntro = lotteryIntro;
    }

    public String getLotteryContent() {
        return lotteryContent;
    }

    public void setLotteryContent(String lotteryContent) {
        this.lotteryContent = lotteryContent;
    }

    public String getTotalOpenDaily() {
        return totalOpenDaily;
    }

    public void setTotalOpenDaily(String totalOpenDaily) {
        this.totalOpenDaily = totalOpenDaily;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getStartIssue() {
        return startIssue;
    }

    public void setStartIssue(String startIssue) {
        this.startIssue = startIssue;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop;
    }

    public String getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(String gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "LotteryConfigPojo{" +
                "lotteryCode='" + lotteryCode + '\'' +
                ", lotteryClass='" + lotteryClass + '\'' +
                ", lotteryClassName='" + lotteryClassName + '\'' +
                ", lotteryType='" + lotteryType + '\'' +
                ", lotteryName='" + lotteryName + '\'' +
                ", lotteryExplain='" + lotteryExplain + '\'' +
                ", lotteryIntro='" + lotteryIntro + '\'' +
                ", lotteryContent='" + lotteryContent + '\'' +
                ", totalOpenDaily='" + totalOpenDaily + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", startIssue='" + startIssue + '\'' +
                ", startTime='" + startTime + '\'' +
                ", isStop='" + isStop + '\'' +
                ", gmtCreated='" + gmtCreated + '\'' +
                ", gmtModified='" + gmtModified + '\'' +
                '}';
    }
}
