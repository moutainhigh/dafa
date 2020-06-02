package pers.dafacloud.enums;

//SELECT CONCAT("L",lottery_code,'("',lottery_name,'","',lottery_code,'"),') FROM  lottery_config
public enum LotteryAttribute {

    //L0101("UU快三", "0101", "", false, 0),
    //L1000("欢乐生肖", "1000", "", false, 0),
    //L1001("新疆时时彩", "1001", "", false, 0),
    //L1003("天津时时彩", "1003", "", false, 0),
    L1008("大发时时彩", "1008", "SSC", false, 1),
    //L1100("广东11选5", "1100", "", false, 0),
    //L1101("上海11选5", "1101", "", false, 0),
    //L1102("山东11选5", "1102", "", false, 0),
    //L1103("江西11选5", "1103", "", false, 0),
    //L1201("福彩3D", "1201", "", false, 0),
    //L1202("排列3", "1202", "", false, 0),
    L1300("大发六合彩", "1300", "LHC", false, 1),
    //L1301("香港六合彩", "1301", "", false, 0),
    //L1302("北京快乐8", "1302", "", false, 0),
    //L1303("北京PK10", "1303", "", false, 0),
    L1304("大发PK10", "1304", "PK10", false, 1),
    //L1401("江苏快3", "1401", "", false, 0),
    //L1402("安徽快3", "1402", "", false, 0),
    //L1403("广西快3", "1403", "", false, 0),
    //L1404("吉林快3", "1404"),
    //L1405("湖北快3", "1405"),
    //L1406("北京快3", "1406"),
    L1407("大发快3", "1407", "K3", false, 1),
    //L1408("河北快3", "1408"),
    //L1409("贵州快3", "1409"),
    //L1410("上海快3", "1410"),
    //L1411("甘肃快3", "1411"),
    L1412("大发5分快3", "1412", "K3", false, 5),
    L1009("大发5分时时彩", "1009", "SSC", false, 5),
    L1306("大发5分PK10", "1306", "PK10", false, 5),
    //L1307("大发5分快乐8", "1307"),
    //L1308("大发快乐8", "1308"),
    //L1104("大发5分11选5", "1104"),
    //L1105("大发11选5", "1105"),
    //L1203("大发5分3D", "1203"),
    //L1204("大发3D", "1204"),
    //L1205("大发5分排列3", "1205"),
    //L1206("大发排列3", "1206"),
    L1309("幸运飞艇", "1309","PK10", false, 1),
    L1305("大发5分六合彩", "1305", "LHC", false, 5),
    L1018("站长时时彩", "1018", "SSC", true, 1),
    L1019("站长5分时时彩", "1019", "SSC", true, 5),
    L1418("站长快3", "1418", "K3", true, 1),
    L1419("站长5分快3", "1419", "K3", true, 5),
    //L1310("站长六合彩", "1310"),
    //L1311("站长5分六合彩", "1311"),
    L1312("站长PK10", "1312", "PK10", true, 1),
    L1313("站长5分PK10", "1313", "PK10", true, 5),
    L1010("3分时时彩", "1010", "SSC", false, 3),
    L1314("3分PK10", "1314", "PK10", false, 3),
    L1413("3分快3", "1413", "K3", false, 3)
    //L1315("北京28", "1315")
    ;

    private String lotteryName;
    private String lotteryCode;
    private String type;
    private boolean isTenant;
    private int timeType;


    LotteryAttribute(String lotteryName, String lotteryCode, String type, boolean isTenant, int timeType) {
        this.lotteryCode = lotteryCode;
        this.lotteryName = lotteryName;
        this.type = type;
        this.isTenant = isTenant;
        this.timeType = timeType;
    }

    public static LotteryAttribute getLotterybyName(String lotteryName) {
        for (LotteryAttribute lotteryAttribute : LotteryAttribute.values()) {
            if (lotteryAttribute.lotteryName.equals(lotteryName)) {
                return lotteryAttribute;
            }
        }
        return null;
    }

    public static String getLotteryCodebyName(String lotteryName) {
        for (LotteryAttribute lotteryAttribute : LotteryAttribute.values()) {
            if (lotteryAttribute.lotteryName.equals(lotteryName)) {
                return lotteryAttribute.lotteryCode;
            }
        }
        return "";
    }

}
