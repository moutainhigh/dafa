package pers.dafagame.enums;

import java.math.BigDecimal;

public enum PrizeType {

    THANK(1000, BigDecimal.ZERO),
    ORDINARY(990, new BigDecimal("7.158")), //连线奖
    OVERALL(5, new BigDecimal("408.9966")),  //全盘奖
    MARY(5, new BigDecimal("79.9689"));     //必须中小玛丽

   public Integer weight;
   public BigDecimal odds;
    PrizeType(Integer weight, BigDecimal odds){
        this.weight = weight;
        this.odds = odds;
    }

}
