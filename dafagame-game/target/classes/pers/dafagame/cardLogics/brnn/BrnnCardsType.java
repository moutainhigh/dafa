package pers.dafagame.cardLogics.brnn;

public enum BrnnCardsType {
    MEI_NIU(0,1,1),			 	//没牛
    NIU_1(1,1,1),               //牛1
    NIU_2(2,1,2),               //牛2
    NIU_3(3,1,3),               //牛3
    NIU_4(4,1,4),               //牛4
    NIU_5(5,1,5),               //牛5
    NIU_6(6,1,6),               //牛6
    NIU_7(7,2,7),               //牛7
    NIU_8(8,2,8),               //牛8
    NIU_9(9,3,9),               //牛9
    NIU_NIU(10,4,10),             //牛牛
    SI_HUA(11,4,10),              //四花
    WU_HUA(12,4,10),              //五花
    SI_ZHA(13,4,10),              //四炸
    WU_XIAO(14,4,10);             //五小
    ;
    //牌型
    public final int niu;

    public final int odd4;

    public final int odd10;


    BrnnCardsType(int niu, int odd4, int odd10) {
        this.niu = niu;
        this.odd4 = odd4;
        this.odd10 = odd10;
    }

    /**
     * @param niu
     * @return
     * @Description:根据牌的id获取牌
     */
    public static BrnnCardsType geCardsType(int niu) {
        for (BrnnCardsType cardsType : BrnnCardsType.values()) {
            if (cardsType.niu == niu) {
                return cardsType;
            }
        }

        return null;
    }
}
