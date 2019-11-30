package kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListRemoveRepeat;
import pers.utils.timeUtils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class KibanaData {

    /**
     * 获取数据
     *
     * @param search     查询条件
     * @param startTimes 开始时间
     * @param endTimes   结束时间
     * @param env        查询的环境
     * @return JSONArray hits
     */
    public static JSONArray queryKibana(String search, String startTimes, String endTimes, String env) {
        String url = "https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com/_plugin/kibana/elasticsearch/_msearch";
        long startTime = TimeUtil.getMillSecond(startTimes);
        long endTime = TimeUtil.getMillSecond(endTimes);
        String indexEv = env;
        //查询条件
        String query = String.format("header:'a02916'");
        String body0 = "{\"index\":\"{indexEv}\",\"ignore_unavailable\":true,\"timeout\":30000,\"preference\":1564455142933}\n{\"version\":true,\"size\":500,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"10m\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"{query}\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestamp\":{\"gte\":{startTime},\"lte\":{endTime},\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647}}\n";
        String body = body0
                .replace("{indexEv}", indexEv)
                .replace("{query}", search)
                .replace("{startTime}", String.valueOf(startTime))
                .replace("{endTime}", String.valueOf(endTime));
        //System.out.println(body);//请求体
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-ndjson")
                .other("kbn-version", "6.3.1")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                .other("origin", "https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com")
                .referer("https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com/_plugin/kibana/app/kibana")
                .build();
        String result = DafaRequest.post(url, body, headers);
        JSONArray responses = JSONObject.fromObject(result).getJSONArray("responses");
        JSONObject one = responses.getJSONObject(0);
        //System.out.println(one.getString("took"));
        JSONArray hits = one.getJSONObject("hits").getJSONArray("hits");
        System.out.println("数据量：" + hits.size());
        return hits;
    }

    @Test(description = "统计ip出现次数")
    public static void test001() {
        String startTime = "2019-11-06 00:00:00";
        String endTime = "2019-11-06 23:59:59";
        String search = "header:'a02916'";
        String env = "master-access-*";
        List<String> resultList = new ArrayList<>();
        JSONArray hits = queryKibana(search, startTime, endTime, env);
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                JSONObject header = source.getJSONObject("header");
                System.out.println(header.getString("x-client-ip"));
                resultList.add(header.getString("x-client-ip"));
            }
        }
        System.out.println(ListRemoveRepeat.removeRepeatCount(resultList));//去重和统计次数
    }

    @Test(description = "统计接口时间差（毫秒）")
    public static void test01a() {
        String search = "url:*userAgentReport* AND header:*9999165*";
        String startTime = "2019-11-25 00:00:00";
        String endTime = "2019-11-25 12:00:00";
        String env = "master-access-*";
        JSONArray hits = queryKibana(search, startTime, endTime, env);
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                //JSONObject header = source.getJSONObject("header");
                System.out.println(TimeUtil.getDiffMillSecond(source.getString("startTime"), source.getString("endTime")));
            }
        }
    }

    @Test(description = "cms被ip未绑定")
    public static void test01b() {
        String search = "url:*userAgentReport* AND header:*9999165*";
        String startTime = "2019-11-25 00:00:00";
        String endTime = "2019-11-25 12:00:00";
        String env = "master-access-*";
        JSONArray hits = queryKibana(search, startTime, endTime, env);
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                //JSONObject header = source.getJSONObject("header");
                System.out.println(TimeUtil.getDiffMillSecond(source.getString("startTime"), source.getString("endTime")));
            }
        }
    }
}
