package pers.dafacloud.kibana;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.utils.BaseException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class IPLimit {
    private static Logger logger = LoggerFactory.getLogger(IPLimit.class);

    public static JSONObject queryLimitIPCms(String host, String timeType, String queryType) throws BaseException {
        LocalDateTime dt = LocalDateTime.now();
        long startTime;
        if ("1".equals(timeType)) {
            startTime = dt.plusMinutes(-15).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        } else {
            startTime = dt.plusHours(-1).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }
        long endTime = dt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        String search;
        String ev;
        if ("0".equals(queryType)) {
            search = String.format("detail:*IP未绑定* AND detail:*%s* AND server-name:*zuul-cms*", host);
            ev = "master-info*";
        } else if ("1".equals(queryType)) {
            //search = String.format("detail:*限制* AND detail:*%s*  AND server-name:*zuul-front*", host);
            search = String.format("url:*login*  AND msg:\\\"*您已被限制登录*\\\" AND paramsBody:\\\"*%s*\\\"", host);
            ev = "master-access*";
        } else if ("2".equals(queryType)) {
            ev = "master-access*";
            search = String.format("msg:\\\"*地区限制*\\\" AND paramsBody:\\\"*%s*\\\"", host);
        } else {
            logger.info("queryType错误");
            return null;
        }

        JSONArray hits = KibanaUtils.queryKibana(search, startTime, endTime, ev);

        JSONObject returnJson = new JSONObject();
        JSONArray returnJasonArray = new JSONArray();
        System.out.println("IP数据量：" + hits.size());
        int total = hits.size();
        returnJson.put("total", total > 500 ? 500 : total);
        for (int i = 0; i < total; i++) {
            if (i >= 500) {
                break;
            }
            JSONObject hit = hits.getJSONObject(i);
            JSONObject source = hit.getJSONObject("_source");
            if ("0".equals(queryType)) {
                String datetime = source.getString("datetime");
                JSONObject detail = source.getJSONObject("detail");
                JSONObject returnJson0 = new JSONObject();
                returnJson0.put("datetime", datetime);
                returnJson0.put("ip", detail.getString("ip"));
                returnJson0.put("url", detail.getString("url"));
                returnJasonArray.add(returnJson0);
            } else {
                String datetime = source.getString("endTime");
                JSONObject detail = source.getJSONObject("header");
                JSONObject returnJson0 = new JSONObject();
                returnJson0.put("datetime", datetime);
                returnJson0.put("ip", detail.getString("x-client-ip"));
                returnJson0.put("url", detail.getString("x-url"));
                returnJasonArray.add(returnJson0);
            }
        }
        returnJson.put("list", returnJasonArray);
        System.out.println(returnJson);
        return returnJson;
    }

    public static void main(String[] args)  throws Exception{
        queryLimitIPCms("aicp.acp68.com", "1", "1");
    }
}
