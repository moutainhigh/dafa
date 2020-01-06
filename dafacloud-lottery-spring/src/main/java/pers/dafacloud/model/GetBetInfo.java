package pers.dafacloud.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GetBetInfo {
    private String lotteryCode;
    private String playDetailCode;
    private String bettingNumber;
    private String bettingAmount;
    private String bettingCount;
    private String graduationCount;
    private String bettingUnit;

    private String tenantCode;
    private String username;
    private String recordCode;
    //private String bettingAmount; 已有
    private String returnAmount;
    private String gmtModified;

}
