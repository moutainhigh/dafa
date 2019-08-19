package pers.utils.dafaRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DafaHandle {


    /**
     * 对请求返回的list数据处理
     * */
    public static void dafaHandler(String result) {
        //System.out.println(result);
        JSONArray ja;
        try {
            JSONObject data = JSONObject.fromObject(result).getJSONObject("data");
            System.out.println("数据量：" + data.get("total"));
            if (data.has("rows")) {
                ja = data.getJSONArray("rows");
            } else if (data.has("loglist")) {
                ja = data.getJSONArray("loglist");
            } else if (data.has("betInfoList")) {
                ja = data.getJSONArray("betInfoList");
            } else if (data.has("chaseOrderList")) {
                ja = data.getJSONArray("chaseOrderList");
            } else {
                System.out.println(result);
                ja = null;
            }

            if (ja.size() > 0) {
                System.out.println("数据量：" + ja.size());

                for (int i = 0; i < ja.size(); i++) {
                    System.out.println(ja.getJSONObject(i).toString());
                }
            } else {
                System.out.println(result);
            }
        } catch (Exception e) {
            System.out.println("result:" + result);
        }
    }
}
