package pers.dafacloud;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafacloud.Dao.SqlSessionFactoryUtils;
import pers.dafacloud.Dao.mapper.tenantOpenMessage.TenantOpenMessageMapper;
import pers.utils.dafaRequest.DafaRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChangLong {

    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("dev");
    private static TenantOpenMessageMapper tenantOpenMessageMapper = sqlSession.getMapper(TenantOpenMessageMapper.class);


    private static int lotteryCode = 1418;
    private static String tenantCode = "dafa";
    private static List<Map> list;

    static {
        Map map = new HashMap();
        map.put("lotteryCode", lotteryCode);
        map.put("tenantCode", tenantCode);
        list = tenantOpenMessageMapper.getOpenNumber(map);


    }

    /**
     * 1418 站长快3
     * 1419 站长5分快3
     * 1018 站长时时彩
     * 1019 站长5分时时彩
     */
    public static void main(String[] args) {
        //ssc();
        ks();
    }

    public static void ssc() {
        int counterArry[] = new int[12];
        int maxArray[] = new int[12];
        int cardType[] = new int[12];
        int[] beforeResult = new int[12];//大 单 大 单 大 单 大 单 大 单 大 单
        int[] curResult;
        for (int i = 0; i < list.size(); i++) {
            for (Object key : list.get(i).keySet()) { //单组号码
                //System.out.println("key= "+ key + " and value= " + list.get(i).get(key));
                String codes = (String) list.get(i).get(key);
                //System.out.println(codes);
                String[] sa = codes.split(",");
                curResult = curResultSsc(sa);
                //做对比
                for (int index = 0; index < 12; index++) {
                    //
                    if (beforeResult[index] == curResult[index]) {
                        counterArry[index]++;
                    } else { //中断
                        if (beforeResult[index] != 0 && maxArray[index] == 0) { //不是第一次，maxArray 没有被赋值过
                            cardType[index] = beforeResult[index];
                            maxArray[index] = counterArry[index];
                        }
                        counterArry[index] = 1;
                    }
                }
                beforeResult = curResult;
            }
        }
        for (int i = 0; i < 12; i++) {
            String wei = i / 2 == 0 ? "和" : i / 2 == 1 ? "万" : i / 2 == 2 ? "千" : i / 2 == 3 ? "百" : i / 2 == 4 ? "十" : "个";
            if (i % 2 == 0 && maxArray[i] > 2)//大小
                System.out.print(String.format("%s%s%s,", wei, cardType[i] == 1 ? "大" : "小", maxArray[i]));
            else if (maxArray[i] > 2)
                System.out.print(String.format("%s%s%s,", wei, cardType[i] == 1 ? "单" : "双", maxArray[i]));
        }
        System.out.println();
        getChangLongApi();
    }

    public static void ks() {
        int counterArry[] = new int[2];
        int maxArray[] = new int[2];
        int cardType[] = new int[2];
        int[] beforeResult = new int[2];//大 单
        int[] curResult;
        for (int i = 0; i < list.size(); i++) {
            for (Object key : list.get(i).keySet()) { //单组号码
                String codes = (String) list.get(i).get(key);
                //System.out.println(codes);
                String[] sa = codes.split(",");
                curResult = curResultKs(sa);//[1,2]
                //做对比
                for (int index = 0; index < 2; index++) {
                    if (beforeResult[index] == curResult[index]) {
                        counterArry[index]++;
                    } else { //中断
                        if (beforeResult[index] != 0 && maxArray[index] == 0) { //不是第一次，maxArray 没有被赋值过
                            cardType[index] = beforeResult[index];
                            maxArray[index] = counterArry[index];
                        }
                        counterArry[index] = 1;
                    }
                }
                beforeResult = curResult;
            }
        }
        for (int i = 0; i < 2; i++) {
            if (i % 2 == 0 && maxArray[i] > 2)//大小
                System.out.print(String.format("%s%s,", cardType[i] == 1 ? "大" : "小", maxArray[i]));
            else if (maxArray[i] > 2)
                System.out.print(String.format("%s%s,", cardType[i] == 1 ? "单" : "双", maxArray[i]));
        }
        System.out.println();
        getChangLongApi();
    }

    @Test(description = "打印")
    public static void getChangLongApi() {
        String result = DafaRequest.get("http://m.caishen02.com/v1/lottery/longDragon?type=1",
                "36B70EB9432BFF1CC2FB83DA9603F675");
        //System.out.println(result);
        JSONObject jo = JSONObject.fromObject(result);
        JSONArray ja = jo.getJSONArray("data");
        for (int i = 0; i < ja.size(); i++) {
            JSONObject cl = ja.getJSONObject(i);
            int lottery = cl.getInt("lotteryCode");
            if (lottery == lotteryCode) {
                System.out.println(cl.getString("name") + cl.getString("desc") + "," + cl.getString("times"));
            }

        }


    }

    private static int[] curResultSsc(String[] sa) {
        int[] result = new int[12];
        int total = 0;
        for (String code : sa) {
            total += Integer.parseInt(code);
        }
        if (total > 22) result[0] = 1;
        else result[0] = 2;
        if (total % 2 == 0) result[1] = 1;
        else result[1] = 2;
        //大1 单 1
        if (Integer.parseInt(sa[0]) > 4) result[2] = 1;
        else result[2] = 2;
        if (Integer.parseInt(sa[0]) % 2 == 1) result[3] = 1;
        else result[3] = 2;

        if (Integer.parseInt(sa[1]) > 4) result[4] = 1;
        else result[4] = 2;
        if (Integer.parseInt(sa[1]) % 2 == 1) result[5] = 1;
        else result[5] = 2;

        if (Integer.parseInt(sa[2]) > 4) result[6] = 1;
        else result[6] = 2;
        if (Integer.parseInt(sa[2]) % 2 == 1) result[7] = 1;
        else result[7] = 2;

        if (Integer.parseInt(sa[3]) > 4) result[8] = 1;
        else result[8] = 2;
        if (Integer.parseInt(sa[3]) % 2 == 1) result[9] = 1;
        else result[9] = 2;

        if (Integer.parseInt(sa[4]) > 4) result[10] = 1;
        else result[10] = 2;
        if (Integer.parseInt(sa[4]) % 2 == 1) result[11] = 1;
        else result[11] = 2;
        return result;
    }

    private static int[] curResultKs(String[] sa) {
        int[] result = new int[2];//1大2小 1单2双
        int total = 0;
        for (String code : sa) {
            total += Integer.parseInt(code);
        }
        if (total > 10) result[0] = 1;
        else result[0] = 2;
        if (total % 2 == 0) result[1] = 1;
        else result[1] = 2;
        return result;
    }

    @Test(description = "打印")
    public static void test01() {
        for (int i = 0; i < list.size(); i++) {
            StringBuilder sb = new StringBuilder();
            for (Object key : list.get(i).keySet()) { //单组号码
                //System.out.println("key= "+ key + " and value= " + list.get(i).get(key));
                String codes = (String) list.get(i).get(key);
                String[] sa = codes.split(",");
                int total = 0;
                for (String ss : sa) {
                    total += Integer.parseInt(ss);
                }
                sb.append(codes);
                sb.append(total > 22 ? "和大," : "和小,");
                sb.append(total % 2 == 0 ? "和双," : "和单,");

                sb.append(Integer.parseInt(sa[0]) > 4 ? "万大," : "万小,");
                sb.append(Integer.parseInt(sa[0]) % 2 == 0 ? "万双," : "万单,");

                sb.append(Integer.parseInt(sa[1]) > 4 ? "千大," : "千小,");
                sb.append(Integer.parseInt(sa[1]) % 2 == 0 ? "千双," : "千单,");

                sb.append(Integer.parseInt(sa[2]) > 4 ? "百大," : "百小,");
                sb.append(Integer.parseInt(sa[2]) % 2 == 0 ? "百双," : "百单,");

                sb.append(Integer.parseInt(sa[3]) > 4 ? "十大," : "十小,");
                sb.append(Integer.parseInt(sa[3]) % 2 == 0 ? "十双," : "十单,");

                sb.append(Integer.parseInt(sa[4]) > 4 ? "个大," : "个小,");
                sb.append(Integer.parseInt(sa[4]) % 2 == 0 ? "个双," : "个单,");
            }
            System.out.println(sb);
        }
    }


}
