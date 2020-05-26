package pers.dafacloud.constant;

//SELECT CONCAT("L",lottery_code,'("',lottery_name,'","',lottery_code,'"),') FROM  lottery_config
public enum Lottery {

    L0101("UU快三", "0101"),
    L1000("欢乐生肖", "1000"),
    L1001("新疆时时彩", "1001"),
    L1003("天津时时彩", "1003"),
    L1008("大发时时彩", "1008"),
    L1100("广东11选5", "1100"),
    L1101("上海11选5", "1101"),
    L1102("山东11选5", "1102"),
    L1103("江西11选5", "1103"),
    L1201("福彩3D", "1201"),
    L1202("排列3", "1202"),
    L1300("大发六合彩", "1300"),
    L1301("香港六合彩", "1301"),
    L1302("北京快乐8", "1302"),
    L1303("北京PK10", "1303"),
    L1304("大发PK10", "1304"),
    L1401("江苏快3", "1401"),
    L1402("安徽快3", "1402"),
    L1403("广西快3", "1403"),
    L1404("吉林快3", "1404"),
    L1405("湖北快3", "1405"),
    L1406("北京快3", "1406"),
    L1407("大发快3", "1407"),
    L1408("河北快3", "1408"),
    L1409("贵州快3", "1409"),
    L1410("上海快3", "1410"),
    L1411("甘肃快3", "1411"),
    L1412("大发5分快3", "1412"),
    L1009("大发5分时时彩", "1009"),
    L1306("大发5分PK10", "1306"),
    L1307("大发5分快乐8", "1307"),
    L1308("大发快乐8", "1308"),
    L1104("大发5分11选5", "1104"),
    L1105("大发11选5", "1105"),
    L1203("大发5分3D", "1203"),
    L1204("大发3D", "1204"),
    L1205("大发5分排列3", "1205"),
    L1206("大发排列3", "1206"),
    L1309("幸运飞艇", "1309"),
    L1305("大发5分六合彩", "1305"),
    L1018("站长时时彩", "1018"),
    L1019("站长5分时时彩", "1019"),
    L1418("站长快3", "1418"),
    L1419("站长5分快3", "1419"),
    L1310("站长六合彩", "1310"),
    L1311("站长5分六合彩", "1311"),
    L1312("站长PK10", "1312"),
    L1313("站长5分PK10", "1313"),
    L1010("3分时时彩", "1010"),
    L1314("3分PK10", "1314"),
    L1413("3分快3", "1413"),
    L1315("北京28", "1315");

    private String lotteryName;
    private String lotteryCode;

    Lottery(String lotteryName, String lotteryCode) {
        this.lotteryCode = lotteryCode;
        this.lotteryName = lotteryName;
    }

    public static Lottery getLotterybyName(String lotteryName) {
        for (Lottery lottery : Lottery.values()) {
            if (lottery.lotteryName.equals(lotteryName)) {
                return lottery;
            }
        }
        return null;
    }

    public static String getLotteryCodebyName(String lotteryName) {
        for (Lottery lottery : Lottery.values()) {
            if (lottery.lotteryName.equals(lotteryName)) {
                return lottery.lotteryCode;
            }
        }
        return "";
    }

}
