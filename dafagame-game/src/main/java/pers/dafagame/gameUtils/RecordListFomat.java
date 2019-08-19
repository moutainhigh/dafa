package pers.dafagame.gameUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 交易记录返回的数据
 */
public class RecordListFomat {

    /**
     * 对交易记录返回的数据解析成行数
     *
     */
    public static void fomatHandle(String result) {
        try {
            JSONObject data = JSONObject.fromObject(result).getJSONObject("data");
            JSONArray jsonArray = null;
            if (data.has("list")) {
                jsonArray = data.getJSONArray("list");
            } else if (data.has("")) {
                jsonArray = data.getJSONArray("list");
            } else {
                System.out.println();
            }

            System.out.println("数据长度：" + jsonArray.size());
            for (Object jo : jsonArray) {
                System.out.println(jo);
            }
        } catch (Exception e) {
            System.out.println(result);
            e.printStackTrace();
        }
    }
}
