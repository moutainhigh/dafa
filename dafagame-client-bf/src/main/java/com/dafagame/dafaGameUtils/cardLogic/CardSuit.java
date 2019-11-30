/**
 * Copyright (c) 2015, http://www.wuleyou.com/ All Rights Reserved.
 */
package com.dafagame.dafaGameUtils.cardLogic;

/**
 * @Description:花色
 */
public enum CardSuit {
    FANG_KUAI("\u2666"), MEI_HUA("\u2663"), HONG_TAO("\u2665"), HEI_TAO("\u2660");

    public final String type;


    private CardSuit(String type) {
        this.type = type;
    }
}
