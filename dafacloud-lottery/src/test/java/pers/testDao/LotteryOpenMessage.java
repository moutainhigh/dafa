package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.lotteryOpenMessage.LotteryOpenMessageMapper;
import pers.utils.fileUtils.FileUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 开奖表 lottery_open_message
 */
public class LotteryOpenMessage {
    static SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("lottery");
    static LotteryOpenMessageMapper lotteryOpenMessageMapper = sqlSessionTransaction.getMapper(LotteryOpenMessageMapper.class);
    static List<Map> mapList = lotteryOpenMessageMapper.queryLotteryOpenMessage();

    //第5位龙和虎的概率是否均等
    public static void pk10d5lh() {
        System.out.println("数据量:" + mapList.size());
        int l = 0;
        int h = 0;
        for (Map map : mapList) {
            String[] openNumbers = map.get("openNumber").toString().split(",");
            if (Integer.parseInt(openNumbers[4]) > Integer.parseInt(openNumbers[5])) {
                l++;
            } else {
                h++;
            }
        }
        System.out.println(String.format("龙次数：%s,虎次数：%s", l, h));
    }


    //第5位龙虎长龙概率
    public static void pk10lh() {
        System.out.println("数据量:" + mapList.size());
        int countL = 0;
        int countH = 0;

        //统计
        int[] tjcountL = new int[27];
        int[] tjcountH = new int[27];
        for (Map map : mapList) {
            String[] openNumbers = map.get("openNumber").toString().split(",");
            int five = Integer.parseInt(openNumbers[4]);
            int six = Integer.parseInt(openNumbers[5]);
            if (five > six) {
                countH = 0;
                countL++;
                if (countL >= 2) {
                    tjcountL[countL - 2]++;
                }
            } else {
                countL = 0;
                countH++;
                if (countH >= 2) {
                    tjcountH[countH - 2]++;
                }
            }
        }
        System.out.println(Arrays.toString(tjcountL));
        System.out.println(Arrays.toString(tjcountH));
    }


    //快三 和 六合彩
    public static void k3lhc() {
        System.out.println(mapList.size());
        //计数
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

        //统计
        int[] tjda = new int[20];
        int[] tjxiao = new int[20];
        int[] tjdan = new int[20];
        int[] tjshuang = new int[20];

        for (Map map : mapList) {
            String openNumber = map.get("openNumber").toString();
            String issue = map.get("issue").toString();
            //大发快三求和
            String[] openNumbers = openNumber.split(",");
            int total = Integer.parseInt(openNumbers[0]) + Integer.parseInt(openNumbers[1]) + Integer.parseInt(openNumbers[2]);

            //大发六合彩，特码
            //String[] openNumbers = openNumber.split("\\+");
            //int total = Integer.parseInt(openNumbers[1]);

            StringBuilder sb = new StringBuilder();
            sb.append(issue + "," + openNumber + ",");

            if (total >= 11) {
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

            //统计长龙大于n期的数据
            int n = 2;
            if (da > n) {
                tjda[da - (n + 1)]++;
            }

            if (xiao > n) {
                tjxiao[xiao - (n + 1)]++;
            }

            if (dan > n) {
                tjdan[dan - (n + 1)]++;
            }
            if (shuang > n) {
                tjshuang[shuang - (n + 1)]++;
            }

            sb.append("," + da + "," + xiao + "," + dan + "," + shuang);

            if (da > 6 || xiao > 6 || dan > 6 || shuang > 6) {
                if (map.get("issueKill") != null) {
                    sb.append(" ---skill");
                }
            }
            if (map.get("issueKill") != null) {
                sb.append(" --- kill");
            }
            System.out.println(sb.toString());
            FileUtil.writeFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/test/ChangLongLHC2.txt", sb.toString());
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

    /**
     * 统计长龙出现次数，线上问题，用户在7期长龙开始顺龙
     */
    public static void main(String[] args) {
        //pk10d5lh();
        //pk10lh();
        k3lhc();

    }
}
