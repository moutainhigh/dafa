package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.lotteryOpenMessage.LotteryOpenMessageMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 开奖表 lottery_open_message
 */
public class LotteryOpenMessage {

    /**
     * 统计长龙出现次数，线上问题，用户在7期长龙开始顺龙
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("lottery");
        LotteryOpenMessageMapper lotteryOpenMessageMapper = sqlSessionTransaction.getMapper(LotteryOpenMessageMapper.class);
        List<Map> mapList = lotteryOpenMessageMapper.queryLotteryOpenMessage();
        System.out.println(mapList.size());

        int da = 0;
        int xiao = 0;
        int dan = 0;
        int shuang = 0;

        int daT = 0;
        int xiaoT = 0;
        int danT = 0;
        int shuangT = 0;

        int daCount = 0;
        int xiaoCount = 0;
        int danCount = 0;
        int shuangCount = 0;

        int[] tjda = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] tjxiao = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] tjdan = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] tjshuang = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            String issue = map.get("issue").toString();
            String[] openNumbers = openNumber.split(",");
            int total = Integer.parseInt(openNumbers[0]) + Integer.parseInt(openNumbers[1]) + Integer.parseInt(openNumbers[2]);
            StringBuilder sb = new StringBuilder();
            sb.append(issue + "," + openNumber + ",");

            if (total > 10) {
                sb.append("大 - ");
                da++;
                if (xiao > 5 && map.get("issueKill") != null)
                    sb.append("zkill,");
                xiao = 0;
            } else {
                sb.append("- 小 ");
                xiao++;
                if (da > 5 && map.get("issueKill") != null)
                    sb.append("zkill,");
                da = 0;
            }

            if (total % 2 == 0) {
                sb.append("- 双 ");
                shuang++;
                if (dan > 5 && map.get("issueKill") != null)
                    sb.append("zkill,");
                dan = 0;

            } else {
                sb.append("单 - ");
                dan++;
                if (shuang > 5 && map.get("issueKill") != null)
                    sb.append("zkill,");
                shuang = 0;
            }


            if (da > 5) {
                tjda[da - 6]++;
            }

            if (xiao > 5) {
                tjxiao[xiao - 6]++;
            }

            if (dan > 5) {
                tjdan[dan - 6]++;
            }
            if (shuang > 5) {
                tjshuang[shuang - 6]++;
            }
            sb.append("," + da + "," + xiao + "," + dan + "," + shuang);

            if (da > 6 || xiao > 6 || dan > 6 || shuang > 6) {
                if (map.get("issueKill") != null) {
                    sb.append(" ---skill");
                }
            }
            System.out.println(sb.toString());
            //System.out.println(issue + ";" + openNumber);
        }
        System.out.println(Arrays.toString(tjda));
        System.out.println(Arrays.toString(tjxiao));
        System.out.println(Arrays.toString(tjdan));
        System.out.println(Arrays.toString(tjshuang));

        int daTotal = 0;
        for (int i : tjda) {
            daTotal += i;
        }
        System.out.println(",盈利：" + (daTotal - tjda[0] * 2));

        int xiaoTotal = 0;
        for (int i : tjxiao) {
            xiaoTotal += i;
        }

        System.out.println(",盈利：" + (xiaoTotal - tjxiao[0] * 2));

        int danTotal = 0;
        for (int i : tjdan) {
            danTotal += i;
        }
        System.out.println(",盈利：" + (danTotal - tjdan[0] * 2));

        int shuangTotal = 0;
        for (int i : tjshuang) {
            shuangTotal += i;
        }
        System.out.println(",盈利：" + (shuangTotal - tjshuang[0] * 2));


    }

}
