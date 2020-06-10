package pers.dafacloud.enums;

public enum BcbmCodeEmu {

    b_bsj("1,9,17,25", "大保捷", 1),
    b_bm("2,10,18,26", "大宝马", 1),
    b_bc("3,11,19,27", "大奔驰", 1),
    b_dz("4,12,20,28", "大大众", 1),

    s_bsj("5,13,21,29", "小保捷", 0),
    s_bm("6,14,22,30", "小宝马", 0),
    s_bc("7,15,23,31", "小奔驰", 0),
    s_dz("8,16,24,32", "小大众", 0),
    ;

    public String code;
    public String name;
    public int isBig;

    BcbmCodeEmu(String code, String name, int isBig) {
        this.code = code;
        this.name = name;
        this.isBig = isBig;
    }

    public static BcbmCodeEmu getNameByNum(String num) {
        out:
        for (BcbmCodeEmu bcbmCode : BcbmCodeEmu.values()) {
            for (String s : bcbmCode.code.split(",")) {
                if (s.equals(num)) {
                    return bcbmCode;
                }
            }

        }
        return null;
    }
}
