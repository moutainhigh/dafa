package pers.dafacloud.enums;

import java.util.ArrayList;
import java.util.List;

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

    //SELECT CONCAT('L',lottery_code,'("',lottery_name,'","',lottery_code,'","',lottery_type,'",','false,','false,','1','),')
    //FROM lottery_config
    //-- where lottery_intro not like '全天%'
    //where lottery_explain  like '凌晨%';
    L1407("大发快3", "1407", "K3", false, true, 1),
    L1008("大发时时彩", "1008", "SSC", false, true, 1),
    L1304("大发PK10", "1304", "PK10", false, true, 1),
    L1300("大发六合彩", "1300", "LHC", false, true, 1),
    L1105("大发11选5", "1105", "SYX5", false, true, 1),
    L1204("大发3D", "1204", "FC3D", false, true, 1),
    L1206("大发排列3", "1206", "PL35", false, true, 1),
    L1308("大发快乐8", "1308", "KL8", false, true, 1),

    L1413("3分快3", "1413", "K3", false, true, 3),
    L1010("3分时时彩", "1010", "SSC", false, true, 3),
    L1314("3分PK10", "1314", "PK10", false, true, 3),
    L1107("3分11选5", "1107", "SYX5", false, true, 3),
    L1208("3分3D", "1208", "FC3D", false, true, 3),
    L1210("3分排列3", "1210", "PL35", false, true, 3),
    L1316("3分六合彩", "1316", "LHC", false, true, 3),
    L1320("3分快乐8", "1320", "KL8", false, true, 3),

    L1412("5分快3", "1412", "K3", false, true, 5),
    L1009("5分时时彩", "1009", "SSC", false, true, 5),
    L1306("5分PK10", "1306", "PK10", false, true, 5),
    L1104("5分11选5", "1104", "SYX5", false, true, 5),
    L1203("5分3D", "1203", "FC3D", false, true, 5),
    L1205("5分排列3", "1205", "PL35", false, true, 5),
    L1305("5分六合彩", "1305", "LHC", false, true, 5),
    L1307("5分快乐8", "1307", "KL8", false, true, 5),

    L1414("10分快3", "1414", "K3", false, true, 10),
    L1011("10分时时彩", "1011", "SSC", false, true, 10),
    L1317("10分PK10", "1317", "PK10", false, true, 10),
    L1106("10分11选5", "1106", "SYX5", false, true, 10),
    L1207("10分3D", "1207", "FC3D", false, true, 10),
    L1209("10分排列3", "1209", "PL35", false, true, 10),
    L1318("10分六合彩", "1318", "LHC", false, true, 10),
    L1319("10分快乐8", "1319", "KL8", false, true, 10),

    L1418("站长快3", "1418", "K3", true, true, 1),
    L1419("站长5分快3", "1419", "K3", true, true, 5),

    L1018("站长时时彩", "1018", "SSC", true, true, 1),
    L1019("站长5分时时彩", "1019", "SSC", true, true, 5),

    L1312("站长PK10", "1312", "PK10", true, true, 1),
    L1313("站长5分PK10", "1313", "PK10", true, true, 5),


    //L0101("UU快三", "0101", "K3", false, false, 1),
    L1000("欢乐生肖", "1000", "SSC", false, false, 1),
    L1001("新疆时时彩", "1001", "SSC", false, false, 1),
    L1003("天津时时彩", "1003", "SSC", false, false, 1),
    L1100("广东11选5", "1100", "SYX5", false, false, 1),
    L1101("上海11选5", "1101", "SYX5", false, false, 1),
    L1102("山东11选5", "1102", "SYX5", false, false, 1),
    L1103("江西11选5", "1103", "SYX5", false, false, 1),
    L1201("福彩3D", "1201", "FC3D", false, false, 1),
    L1202("排列3", "1202", "PL35", false, false, 1),
    L1301("香港六合彩", "1301", "LHC", false, false, 1),
    L1302("北京快乐8", "1302", "KL8", false, false, 1),
    L1303("北京PK10", "1303", "PK10", false, false, 1),
    L1401("江苏快3", "1401", "K3", false, false, 1),
    L1402("安徽快3", "1402", "K3", false, false, 1),
    L1403("广西快3", "1403", "K3", false, false, 1),
    L1404("吉林快3", "1404", "K3", false, false, 1),
    L1405("湖北快3", "1405", "K3", false, false, 1),
    L1406("北京快3", "1406", "K3", false, false, 1),
    L1408("河北快3", "1408", "K3", false, false, 1),
    L1409("贵州快3", "1409", "K3", false, false, 1),
    L1410("上海快3", "1410", "K3", false, false, 1),
    L1411("甘肃快3", "1411", "K3", false, false, 1),
    L1309("幸运飞艇", "1309", "PK10", false, false, 1),
    L1315("北京28", "1315", "BJ28", false, false, 1),
    ;
    public String name;
    public String code;
    public String type;
    public boolean isTenant;
    public boolean isPrivate;
    public int timeType;

    LotteryConfig(String name, String code, String type, boolean isTenant, boolean isPrivate, int timeType) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.isTenant = isTenant;
        this.isPrivate = isPrivate;
        this.timeType = timeType;
    }


    public static LotteryConfig getLottery(String lotteryCode) {
        for (LotteryConfig lotteryConfig : values()) {
            if (lotteryCode.equals(lotteryConfig.code))
                return lotteryConfig;
        }
        return null;
    }

    public static List<String> getPublicLotteryCode() {
        List<String> lotteryCodes = new ArrayList<>();
        for (LotteryConfig lotteryConfig : values()) {
            if (!lotteryConfig.isPrivate)
                lotteryCodes.add(lotteryConfig.code);
        }
        return lotteryCodes;
    }

    public static void main(String[] args) {
        //System.out.println(getPublicLotteryCode());
        //"['大发时时彩', '1008', '2', '5000'],"
        for (LotteryConfig lotteryConfig : values()) {
            System.out.println(String.format("['%s', '%s', '2', '5000'],",lotteryConfig.name,lotteryConfig.code));
        }

    }

}
