package pers.dafacloud.dafaLottery;

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

    L1412("5分快3", "1412", "K3", false, 5),
    L1009("5分时时彩", "1009", "SSC", false, 5),
    L1306("5分PK10", "1306", "PK10", false, 5),
    L1305("5分六合彩", "1305", "LHC", false, 5),


    L1418("站长快3", "1418", "K3", true, 1),
    L1419("站长5分快3", "1419", "K3", true, 5),

    L1018("站长时时彩", "1018", "SSC", true, 1),
    L1019("站长5分时时彩", "1019", "SSC", true, 5),

    L1312("站长PK10", "1312", "PK10", true, 1),
    L1313("站长5分PK10", "1313", "PK10", true, 5)
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
