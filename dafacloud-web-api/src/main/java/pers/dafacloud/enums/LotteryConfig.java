package pers.dafacloud.enums;

public enum LotteryConfig {
    //K3--------------
    //1407 大发快3
    //1413 3分快3
    //1412 5分快3
    //1418 站长快3
    //1419 站长5分快3
    //SSC-------------
    //1008 大发时时彩
    //1010 3分时时彩
    //1009 5分时时彩
    //1018 站长时时彩
    //1019 站长5分时时彩
    //PK10-------------
    //1304 大发PK10
    //1314 3分PK10
    //1306 5分PK10
    //1312 站长PK10
    //1313 站长5分PK10
    //LHC-------------
    //1305 大发六合彩
    //1300 5分六合彩
    L1407("大发快3", "1407", "K3", false, 1),
    L1008("大发时时彩", "1008", "SSC", false, 1),
    L1304("大发PK10", "1304", "PK10", false, 1),
    L1300("大发六合彩", "1300", "LHC", false, 1),

    L1413("3分快3", "1413", "K3", false, 3),
    L1010("3分时时彩", "1010", "SSC", false, 3),
    L1314("3分PK10", "1314", "PK10", false, 3),

    L1107("3分11选5", "1107", "SYX5", false, 3),
    L1208("3分3D", "1208", "FC3D", false, 3),
    L1210("3分排列3", "1210", "PL35", false, 3),
    L1316("3分六合彩", "1316", "LHC", false, 3),
    L1320("3分快乐8", "1320", "KL8", false, 3),

    L1412("5分快3", "1412", "K3", false, 5),
    L1009("5分时时彩", "1009", "SSC", false, 5),
    L1306("5分PK10", "1306", "PK10", false, 5),
    L1305("5分六合彩", "1305", "LHC", false, 5),

    L1414("10分快3", "1414", "K3", false, 10),
    L1011("10分时时彩", "1011", "SSC", false, 10),
    L1317("10分PK10", "1317", "PK10", false, 10),

    L1106("10分11选5", "1106", "SYX5", false, 10),
    L1207("10分3D", "1207", "FC3D", false, 10),
    L1209("10分排列3", "1209", "PL35", false, 10),
    L1318("10分六合彩", "1318", "LHC", false, 10),
    L1319("10分快乐8", "1319", "KL8", false, 10),

    L1418("站长快3", "1418", "K3", true, 1),
    L1419("站长5分快3", "1419", "K3", true, 5),

    L1018("站长时时彩", "1018", "SSC", true, 1),
    L1019("站长5分时时彩", "1019", "SSC", true, 5),

    L1312("站长PK10", "1312", "PK10", true, 1),
    L1313("站长5分PK10", "1313", "PK10", true, 5),



    ;
    public String name;
    public String code;
    public String type;
    public boolean isTenant;
    public int timeType;

    LotteryConfig(String name, String code, String type, boolean isTenant, int timeType) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.isTenant = isTenant;
        this.timeType = timeType;
    }


    public static LotteryConfig getLottery(String lotteryCode) {
        for (LotteryConfig lotteryConfig : values()) {
            if (lotteryCode.equals(lotteryConfig.code))
                return lotteryConfig;
        }
        return null;
    }

}
