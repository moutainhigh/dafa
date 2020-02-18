package pers.dafacloud.model;

import lombok.Data;

@Data
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

}
