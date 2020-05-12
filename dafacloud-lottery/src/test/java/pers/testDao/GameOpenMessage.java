package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.mapper.lotteryOpenMessage.LotteryOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 开奖表 lottery_open_message
 */
public class GameOpenMessage {
    static SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("lotteryGame");
    static LotteryOpenMessageMapper lotteryOpenMessageMapper = sqlSessionTransaction.getMapper(LotteryOpenMessageMapper.class);
    static List<Map> mapList = lotteryOpenMessageMapper.queryGameOpenMessage();

    //奔驰宝马开小的概率
    public static void bcbm() {
        System.out.println("数据量:" + mapList.size());
        int xiao = 0;
        int da = 0;
        //小车标：保时捷5，13，21，29，宝马，6，14，22，30，奔驰，7，15，23，31，大众，8，16，24，32
        for (Map map : mapList) {
            int openNumbers = Integer.parseInt(map.get("openNumber").toString());
            switch (openNumbers) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 13:
                case 14:
                case 15:
                case 16:
                case 21:
                case 22:
                case 23:
                case 24:
                case 29:
                case 30:
                case 31:
                case 32:
                    xiao++;
                    break;
                default:
                    da++;
                    break;
            }
            //System.out.println(openNumbers);
        }
        //xiao*4.9*4
        System.out.println(String.format("小车标：%s,大车标：%s", xiao, da));
    }


    public static void main(String[] args) {
        bcbm();
    }
}
