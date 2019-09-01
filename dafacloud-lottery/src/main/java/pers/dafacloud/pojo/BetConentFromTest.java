package pers.dafacloud.pojo;

import pers.dafacloud.Dao.pojo.GetBetInfo;

public class BetConentFromTest {

    private String bettingIssue;
    private String bettingPoint;

    private GetBetInfo getBetInfo;

    public String getBettingIssue() {
        return bettingIssue;
    }

    public void setBettingIssue(String bettingIssue) {
        this.bettingIssue = bettingIssue;
    }

    public String getBettingPoint() {
        return bettingPoint;
    }

    public void setBettingPoint(String bettingPoint) {
        this.bettingPoint = bettingPoint;
    }

    public GetBetInfo getGetBetInfo() {
        return getBetInfo;
    }

    public void setGetBetInfo(GetBetInfo getBetInfo) {
        this.getBetInfo = getBetInfo;
    }

    @Override
    public String toString() {
        return "bettingData=[{" +
                "\"lotteryCode\":\""+getBetInfo.getLotteryCode()+
                "\",\"playDetailCode\":\""+getBetInfo.getPlayDetailCode()+
                "\",\"bettingNumber\":\""+getBetInfo.getBettingNumber()+
                "\",\"bettingCount\":"+getBetInfo.getBettingCount()+
                ",\"bettingAmount\":"+getBetInfo.getBettingAmount()+
                ",\"bettingPoint\":"+bettingPoint+
                ",\"bettingUnit\":"+getBetInfo.getBettingUnit()+
                ",\"bettingIssue\":\""+bettingIssue+
                "\",\"graduationCount\":"+getBetInfo.getGraduationCount()+"}]";
    }
    //bettingData=[{"lotteryCode":"1306","playDetailCode":"1306A11","bettingNumber":"07 08 09",
    // "bettingCount":3,"bettingAmount":6,"bettingPoint":"6","bettingUnit":1,
    // "bettingIssue":"20190618201","graduationCount":1}]
}
