package kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.ListRemoveRepeat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WithdrawRecordList {

    private static List<String> resultList = new ArrayList<>();

    public static void main(String[] args) {
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 11, 21, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 12, 0, 0, 0);


        task(startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }

    private static void task(String startTime, String endTime) {
        System.out.println(startTime);
        System.out.println(endTime);
        String search = "header:*hycp* AND url:*withdrawRecordList*";
        String env = "master-access-*";
        //List<String> resultList = new ArrayList<>();
        JSONArray hits = null;
        for (int i = 0; i < 1; i++) {
            try {
                hits = KibanaData.queryKibana(search, startTime, endTime, env);
                break;
            } catch (Exception e) {
                System.out.println(hits);
                System.out.println(e);
            }
        }
        System.out.println(hits.size());
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                JSONObject data = source.getJSONObject("data");
                //System.out.println(data);
                JSONArray rows = JSONObject.fromObject(data).getJSONArray("rows");
                for (int j = 0; j < rows.size(); j++) {
                    JSONObject jo = rows.getJSONObject(j);
                    if (168563748 == jo.getInt("id")) {
                        resultList.add(jo.toString());
                        System.out.println(source.getString("endTime"));
                        System.out.println(jo.toString());
                    }
                }


            }
        }
        //System.out.println("resultList:" + resultList.size());
        //for (int i = 0; i < resultList.size(); i++) {
        //    System.out.println(resultList.get(i));
        //}
    }
}
