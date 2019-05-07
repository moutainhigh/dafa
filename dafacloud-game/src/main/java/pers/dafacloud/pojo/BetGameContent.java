package pers.dafacloud.pojo;

import java.util.List;

public class BetGameContent {

    private String proto;
    private String issue;
    private String bettingPoint;
    private String betReqInfo;
    private String pos;
    private String bettingAmount;

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBettingPoint() {
        return bettingPoint;
    }

    public void setBettingPoint(String bettingPoint) {
        this.bettingPoint = bettingPoint;
    }

    public String getBetReqInfo() {
        return betReqInfo;
    }

    public void setBetReqInfo(String betReqInfo) {
        this.betReqInfo = betReqInfo;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getBettingAmount() {
        return bettingAmount;
    }

    public void setBettingAmount(String bettingAmount) {
        this.bettingAmount = bettingAmount;
    }

    @Override
    public String toString() {
        return "{\"" +
                "proto\":\"" + proto + "\"" +
                ", \"data\":{\"issue\":\"" + issue + "\"" +
                ", \"bettingPoint\":" + bettingPoint  +
                ", \"betReqInfo\":" +
                " [{\"pos\":" + pos +
                ", \"bettingAmount\":[" + bettingAmount  +
                "]}]}}";
    }
    //{"proto" : 700,"data" : { "issue":"%s", "bettingPoint": %s,"betReqInfo": [{"pos" :1, "bettingAmount": [%s] }]}}
}
