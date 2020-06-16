package pers.dafacloud.enums;

public enum BcbmCodeEmu {

    b_bsj("1,9,17,25", "大保捷", 1, "保时捷(大)", 40),
    b_bm("2,10,18,26", "大宝马", 1, "宝马(大)", 30),
    b_bc("3,11,19,27", "大奔驰", 1, "奔驰(大)", 20),
    b_dz("4,12,20,28", "大大众", 1, "大众(大)", 10),

    s_bsj("5,13,21,29", "小保捷", 0, "保时捷(小)", 5),
    s_bm("6,14,22,30", "小宝马", 0, "宝马(小)", 5),
    s_bc("7,15,23,31", "小奔驰", 0, "奔驰(小)", 5),
    s_dz("8,16,24,32", "小大众", 0, "大众(小)", 5),
    ;

    public String code;
    public String name;
    public int isBig;
    public String nameN;
    public int bonus;

    BcbmCodeEmu(String code, String name, int isBig, String nameN, int bonus) {
        this.code = code;
        this.name = name;
        this.isBig = isBig;
        this.nameN = nameN;
        this.bonus = bonus;
    }

    public static BcbmCodeEmu getNameByNum(String num) {
        for (BcbmCodeEmu bcbmCode : BcbmCodeEmu.values()) {
            for (String s : bcbmCode.code.split(",")) {
                if (s.equals(num)) {
                    return bcbmCode;
                }
            }

        }
        return null;
    }

    public static BcbmCodeEmu getBcbmBynameN(String nameN) {
        for (BcbmCodeEmu bcbmCode : BcbmCodeEmu.values()) {
            if (bcbmCode.nameN.equals(nameN)) {
                return bcbmCode;
            }
        }
        return null;
    }
}
