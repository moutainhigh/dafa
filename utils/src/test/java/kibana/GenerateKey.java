package kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.ListRemoveRepeat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//统计聊天室调用/v1/broadCast/generateKey次数
public class GenerateKey {
    private static List<String> resultList = new ArrayList<>();

    public static void main(String[] args) {
        LocalDateTime startDate = LocalDateTime.of(2020, 8, 25, 0, 0, 0, 0);
        LocalDateTime today = LocalDateTime.of(2020, 8, 26, 0, 0, 0, 0);
        for (int i = 0; i < 2000; i++) {
            LocalDateTime endDate = startDate.plusMinutes(1);
            if (endDate.isAfter(today)) {
                System.out.println("结束:"+endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                break;
            }
            task(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            startDate = endDate;
        }
    }

    /**
     * 2020-08-26 13:16:00
     * 2020-08-26 13:18:00
     * 数据量：1733
     * resultList:21304
     * newList:3930
     */
    private static void task(String startTime, String endTime) {
        //String startTime = "2020-08-26 10:00:00";
        //String endTime = "2020-08-26 10:02:00";
        System.out.println(startTime);
        System.out.println(endTime);
        String search = "url:*generateKey*";
        String env = "master-access-*";
        //List<String> resultList = new ArrayList<>();
        JSONArray hits = null;
        for (int i = 0; i < 10; i++) {
            try {
                hits = KibanaData.queryKibana(search, startTime, endTime, env);
                break;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                JSONObject header = source.getJSONObject("header");
                //System.out.println(header.getString("x-client-ip"));
                resultList.add(header.getString("x-user-name"));
            }
        }
        //System.out.println(ListRemoveRepeat.removeRepeatCount(resultList));//去重和统计次数
        if (resultList.size() > 10000) {
            System.out.println("resultList:" + resultList.size());
            List<String> newList = ListRemoveRepeat.removeRepeat(resultList);
            System.out.println("newList:" + newList.size());
            FileUtil.writeFile("/Users/duke/Documents/github/dafa/utils/src/test/resources/b.txt", newList, true);
            //System.out.println();//去重和统计次数
            resultList.clear();
            newList.clear();
        }
    }

    @Test(description = "测试")
    public static void test01() {
        //10686
        List<String> list = FileUtil.readFile("/Users/duke/Documents/github/dafa/utils/src/test/resources/c.txt");
        List<String> newList = ListRemoveRepeat.removeRepeat(list);
        System.out.println(newList.size());
        FileUtil.writeFile("/Users/duke/Documents/github/dafa/utils/src/test/resources/d.txt", newList, true);
    }
}
