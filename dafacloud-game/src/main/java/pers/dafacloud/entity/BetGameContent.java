package pers.dafacloud.entity;

import lombok.Data;

@Data
public class BetGameContent {

    private String proto;
    private String issue;
    private String bettingPoint;
    private String betReqInfo;
    private String pos;
    private String bettingAmount;

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
}
