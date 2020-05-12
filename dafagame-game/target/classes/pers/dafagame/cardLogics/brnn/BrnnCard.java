package pers.dafagame.cardLogics.brnn;


public enum BrnnCard {
    /*
     * 方块 A - K,0-12
     */
    FANG_KUAI_A(1, 1, CardSuit.FANG_KUAI, 4),
    FANG_KUAI_ER(2, 2, CardSuit.FANG_KUAI, 8),
    FANG_KUAI_SAN(3, 3, CardSuit.FANG_KUAI, 12),
    FANG_KUAI_SI(4, 4, CardSuit.FANG_KUAI, 16),
    FANG_KUAI_WU(5, 5, CardSuit.FANG_KUAI, 20),
    FANG_KUAI_LIU(6, 6, CardSuit.FANG_KUAI, 24),
    FANG_KUAI_QI(7, 7, CardSuit.FANG_KUAI, 28),
    FANG_KUAI_BA(8, 8, CardSuit.FANG_KUAI, 32),
    FANG_KUAI_JIU(9, 9, CardSuit.FANG_KUAI,36),
    FANG_KUAI_SHI(10, 10, CardSuit.FANG_KUAI, 40),
    FANG_KUAI_J(11, 10, CardSuit.FANG_KUAI, 44),
    FANG_KUAI_Q(12, 10, CardSuit.FANG_KUAI, 48),
    FANG_KUAI_K(13, 10, CardSuit.FANG_KUAI, 52),

    /*
     * 梅花 A - K,13-25
     */
    MEI_HUA_A(1, 1, CardSuit.MEI_HUA, 5),
    MEI_HUA_ER(2, 2, CardSuit.MEI_HUA, 9),
    MEI_HUA_SAN(3, 3, CardSuit.MEI_HUA, 13),
    MEI_HUA_SI(4, 4, CardSuit.MEI_HUA, 17),
    MEI_HUA_WU(5, 5, CardSuit.MEI_HUA, 21),
    MEI_HUA_LIU(6, 6, CardSuit.MEI_HUA, 25),
    MEI_HUA_QI(7, 7, CardSuit.MEI_HUA, 29),
    MEI_HUA_BA(8, 8, CardSuit.MEI_HUA, 33),
    MEI_HUA_JIU(9, 9, CardSuit.MEI_HUA, 37),
    MEI_HUA_SHI(10, 10, CardSuit.MEI_HUA, 41),
    MEI_HUA_J(11, 10, CardSuit.MEI_HUA, 45),
    MEI_HUA_Q(12, 10, CardSuit.MEI_HUA, 49),
    MEI_HUA_K(13, 10, CardSuit.MEI_HUA, 53),

    /*
     * 红桃 A - K，26-38
     */
    HONG_TAO_A(1, 1, CardSuit.HONG_TAO, 6),
    HONG_TAO_ER(2, 2, CardSuit.HONG_TAO, 10),
    HONG_TAO_SAN(3, 3, CardSuit.HONG_TAO, 14),
    HONG_TAO_SI(4, 4, CardSuit.HONG_TAO, 18),
    HONG_TAO_WU(5, 5, CardSuit.HONG_TAO, 22),
    HONG_TAO_LIU(6, 6, CardSuit.HONG_TAO, 26),
    HONG_TAO_QI(7, 7, CardSuit.HONG_TAO, 30),
    HONG_TAO_BA(8, 8, CardSuit.HONG_TAO, 34),
    HONG_TAO_JIU(9, 9, CardSuit.HONG_TAO, 38),
    HONG_TAO_SHI(10, 10, CardSuit.HONG_TAO, 42),
    HONG_TAO_J(11, 10, CardSuit.HONG_TAO, 46),
    HONG_TAO_Q(12, 10, CardSuit.HONG_TAO, 50),
    HONG_TAO_K(13, 10, CardSuit.HONG_TAO, 54),

    //黑桃 A - K,39-51
    HEI_TAO_A(1, 1, CardSuit.HEI_TAO, 7),
    HEI_TAO_ER(2, 2, CardSuit.HEI_TAO, 11),
    HEI_TAO_SAN(3, 3, CardSuit.HEI_TAO, 15),
    HEI_TAO_SI(4, 4, CardSuit.HEI_TAO, 19),
    HEI_TAO_WU(5, 5, CardSuit.HEI_TAO, 23),
    HEI_TAO_LIU(6, 6, CardSuit.HEI_TAO, 27),
    HEI_TAO_QI(7, 7, CardSuit.HEI_TAO, 31),
    HEI_TAO_BA(8, 8, CardSuit.HEI_TAO, 35),
    HEI_TAO_JIU(9, 9, CardSuit.HEI_TAO, 39),
    HEI_TAO_SHI(10, 10, CardSuit.HEI_TAO, 43),
    HEI_TAO_J(11, 10, CardSuit.HEI_TAO, 47),
    HEI_TAO_Q(12, 10, CardSuit.HEI_TAO, 51),
    HEI_TAO_K(13, 10, CardSuit.HEI_TAO, 55),

    /*
     * 小王,大王 52,53
     */
    XIAO_WANG(14, 0, null, 56),
    DA_WANG(15, 0, null, 57);


    /**
     * A-K(1-13),小王:14,大王:15
     * */
    public final int power;
    /**
     *	牛牛牌的数字
     */
    public final int num;
    /**
     * 牌的类型(0-5)
     * 0:方块,1:梅花,2:红桃,3:黑桃,4:小王,5:大王
     * */
    public final CardSuit type;

    public final int index;

    private BrnnCard(int power, int num, CardSuit type, int index) {
        this.power = power;
        this.num = num;
        this.type = type;
        this.index = index;
    }

    /**
     * @Description:根据牌的id获取牌
     * @param cardId 0-53
     * @return
     */
    public static BrnnCard card(int cardId) {
        if (cardId < 0 || cardId >= values().length) {
            throw new IllegalArgumentException("carId必须在[0,53]范围内");
        }

        return values()[cardId];
    }

}
