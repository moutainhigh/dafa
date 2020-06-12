package pers.test.changlong;

import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.enums.BcbmCodeEmu;
import pers.dafacloud.mapper.tenantOpenMessage.TenantOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Bcbm {

    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("lotteryGame");
    private static TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);

    public static void main(String[] args) {
        List<Map> mapList = tenantOpenMessageMapper.getGameOpenNumber();
        System.out.println(mapList.size());

        int[] dalong = new int[50];
        int[] xiaolong = new int[50];
        int xiao = 0;
        int da = 0;
        int[] count = new int[8];
        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getNameByNum(openNumber);
            count[bcbmCode.ordinal()]++;
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            if (bcbmCode.isBig == 1) {
                xiao = 0;
                da++;
                dalong[da - 1]++;
                //if (da > 3) {
                //    dalong[da - 3]++;
                //}
            }
            if (bcbmCode.isBig == 0) {
                da = 0;
                xiao++;
                xiaolong[xiao - 1]++;
                //if (xiao > 3) {
                //    xiaolong[xiao - 3]++;
                //}
            }
            System.out.println(map.get("issue").toString() + " - " + openNumber + (openNumber.length() == 1 ? " " : "") + " - " + bcbmCode.name + " - " + da + " - " + xiao);
        }
        System.out.println("开大长龙长度：" + Arrays.toString(dalong));
        System.out.println("开小长龙长度：" + Arrays.toString(xiaolong));
        //System.out.println(Arrays.toString(count));
        BcbmCodeEmu[] bcbmCodeEmus = BcbmCodeEmu.values();
        for (int i = 0; i < bcbmCodeEmus.length; i++) {
            System.out.print(bcbmCodeEmus[i].name + ":" + count[i] + ", ");
        }

    }

    @Test(description = "测试")
    public static void test01() {
        List<String> names = FileUtil.readFile("/Users/duke/Downloads/大众(大).txt");
        System.out.println("总数据："+names.size());
        int[] dalong = new int[150];
        int[] xiaolong = new int[150];
        int xiao = 0;
        int da = 0;
        int[] count = new int[8];//每个号的情况
        for (String openName : names) {
            //String openNumber = map.get("openNumber").toString();
            BcbmCodeEmu bcbmCode = BcbmCodeEmu.getBcbmBynameN(openName);
            count[bcbmCode.ordinal()]++;
            if (bcbmCode == null) {
                System.out.println("================开奖号码错误======================");
                continue;
            }
            if (bcbmCode.isBig == 1) {
                xiao = 0;
                da++;
                dalong[da - 1]++;
                //if (da > 3) {
                //    dalong[da - 3]++;
                //}
            }
            if (bcbmCode.isBig == 0) {
                da = 0;
                xiao++;
                xiaolong[xiao - 1]++;
                //if (xiao > 3) {
                //    xiaolong[xiao - 3]++;
                //}
            }
        }
        System.out.println("开车标大长龙长度：" + Arrays.toString(dalong));
        System.out.println("开小车标长龙长度：" + Arrays.toString(xiaolong));
        //System.out.println(Arrays.toString(count));
        BcbmCodeEmu[] bcbmCodeEmus = BcbmCodeEmu.values();
        for (int i = 0; i < bcbmCodeEmus.length; i++) {
            System.out.print(bcbmCodeEmus[i].name + ":" + count[i] + ", ");
        }


    }

}
