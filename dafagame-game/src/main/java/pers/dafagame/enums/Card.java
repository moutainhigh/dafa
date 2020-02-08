package pers.dafagame.enums;

/**
 * @Description:0-12：方块，13-25：梅花，26-38：红桃，39-51：黑桃，52：小王，53：大王
 */
public enum Card {

    /*
     * 方块 A - K,0-12
     */
    FANG_KUAI_A(14, CardSuit.FANG_KUAI, 4, 'A'),
    FANG_KUAI_ER(15, CardSuit.FANG_KUAI, 8, '2'),
    FANG_KUAI_SAN(3, CardSuit.FANG_KUAI, 12, '3'),
    FANG_KUAI_SI(4, CardSuit.FANG_KUAI, 16, '4'),
    FANG_KUAI_WU(5, CardSuit.FANG_KUAI, 20, '5'),
    FANG_KUAI_LIU(6, CardSuit.FANG_KUAI, 24, '6'),
    FANG_KUAI_QI(7, CardSuit.FANG_KUAI, 28, '7'),
    FANG_KUAI_BA(8, CardSuit.FANG_KUAI, 32, '8'),
    FANG_KUAI_JIU(9, CardSuit.FANG_KUAI, 36, '9'),
    FANG_KUAI_SHI(10, CardSuit.FANG_KUAI, 40, 'S'),
    FANG_KUAI_J(11, CardSuit.FANG_KUAI, 44, 'J'),
    FANG_KUAI_Q(12, CardSuit.FANG_KUAI, 48, 'Q'),
    FANG_KUAI_K(13, CardSuit.FANG_KUAI, 52, 'K'),

    /*
     * 梅花 A - K,13-25
     */
    MEI_HUA_A(14, CardSuit.MEI_HUA, 5, 'A'),
    MEI_HUA_ER(15, CardSuit.MEI_HUA, 9, '2'),
    MEI_HUA_SAN(3, CardSuit.MEI_HUA, 13, '3'),
    MEI_HUA_SI(4, CardSuit.MEI_HUA, 17, '4'),
    MEI_HUA_WU(5, CardSuit.MEI_HUA, 21, '5'),
    MEI_HUA_LIU(6, CardSuit.MEI_HUA, 25, '6'),
    MEI_HUA_QI(7, CardSuit.MEI_HUA, 29, '7'),
    MEI_HUA_BA(8, CardSuit.MEI_HUA, 33, '8'),
    MEI_HUA_JIU(9, CardSuit.MEI_HUA, 37, '9'),
    MEI_HUA_SHI(10, CardSuit.MEI_HUA, 41, 'S'),
    MEI_HUA_J(11, CardSuit.MEI_HUA, 45, 'J'),
    MEI_HUA_Q(12, CardSuit.MEI_HUA, 49, 'Q'),
    MEI_HUA_K(13, CardSuit.MEI_HUA, 53, 'K'),

    /*
     * 红桃 A - K，26-38
     */
    HONG_TAO_A(14, CardSuit.HONG_TAO, 6, 'A'),
    HONG_TAO_ER(15, CardSuit.HONG_TAO, 10, '2'),
    HONG_TAO_SAN(3, CardSuit.HONG_TAO, 14, '3'),
    HONG_TAO_SI(4, CardSuit.HONG_TAO, 18, '4'),
    HONG_TAO_WU(5, CardSuit.HONG_TAO, 22, '5'),
    HONG_TAO_LIU(6, CardSuit.HONG_TAO, 26, '6'),
    HONG_TAO_QI(7, CardSuit.HONG_TAO, 30, '7'),
    HONG_TAO_BA(8, CardSuit.HONG_TAO, 34, '8'),
    HONG_TAO_JIU(9, CardSuit.HONG_TAO, 38, '9'),
    HONG_TAO_SHI(10, CardSuit.HONG_TAO, 42, 'S'),
    HONG_TAO_J(11, CardSuit.HONG_TAO, 46, 'J'),
    HONG_TAO_Q(12, CardSuit.HONG_TAO, 50, 'Q'),
    HONG_TAO_K(13, CardSuit.HONG_TAO, 54, 'K'),

    //黑桃 A - K,39-51
    HEI_TAO_A(14, CardSuit.HEI_TAO, 7, 'A'),
    HEI_TAO_ER(15, CardSuit.HEI_TAO, 11, '2'),
    HEI_TAO_SAN(3, CardSuit.HEI_TAO, 15, '3'),
    HEI_TAO_SI(4, CardSuit.HEI_TAO, 19, '4'),
    HEI_TAO_WU(5, CardSuit.HEI_TAO, 23, '5'),
    HEI_TAO_LIU(6, CardSuit.HEI_TAO, 27, '6'),
    HEI_TAO_QI(7, CardSuit.HEI_TAO, 31, '7'),
    HEI_TAO_BA(8, CardSuit.HEI_TAO, 35, '8'),
    HEI_TAO_JIU(9, CardSuit.HEI_TAO, 39, '9'),
    HEI_TAO_SHI(10, CardSuit.HEI_TAO, 43, 'S'),
    HEI_TAO_J(11, CardSuit.HEI_TAO, 47, 'J'),
    HEI_TAO_Q(12, CardSuit.HEI_TAO, 51, 'Q'),
    HEI_TAO_K(13, CardSuit.HEI_TAO, 55, 'K'),

    /*
     * 小王,大王 52,53
     */
    XIAO_WANG(16, null, 56, 'w'),
    DA_WANG(17, null, 57, 'W');


    /**
     * 3-k(3-13),A:14,2:15,王:16
     * */
    public final int num;
    /**
     * 牌的类型(0-5)
     * 0:方块,1:梅花,2:红桃,3:黑桃,4:小王,5:大王
     * */
    public final CardSuit type;

    /**
     * 牌型 4-55 56 57
     */
    public final int extNum;

    public final char shown;

    public int getNum() {
        return num;
    }

    private Card(int num, CardSuit type, int extNum, char shown) {
        this.num = num;
        this.type = type;
        this.extNum = extNum;
        this.shown = shown;
    }

//	/**
//	 * @Description:根据牌的id获取牌
//	 * @param cardId 0-53
//	 * @return
//	 */
//	public static Card card(int cardId) {
//		if (cardId < 0 || cardId >= values().length) {
//			throw new IllegalArgumentException("carId必须在[0,53]范围内");
//		}
//		return values()[cardId];
//	}


    public static Card getCard(int extNum) {
        for (Card value : values()) {
            if (value.extNum == extNum) {
                return value;
            }
        }

        return null;
    }

    public static Card getCard(int num, CardSuit type) {
        for (Card value : values()) {
            if (value.num == num && value.type == type) {
                return value;
            }
        }

        return null;
    }
}
