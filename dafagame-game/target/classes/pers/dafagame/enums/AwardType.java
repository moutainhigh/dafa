package pers.dafagame.enums;

import java.math.BigDecimal;

public enum AwardType {

    thank(BigDecimal.ZERO),
    special(new BigDecimal("24.6875")),  //特殊奖
    ordinary(new BigDecimal("3.8277")), //普通奖
    ;

    public BigDecimal odds;

    AwardType(BigDecimal odds) {
        this.odds = odds;
    }

}
