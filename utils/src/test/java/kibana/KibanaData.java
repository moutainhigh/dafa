package kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.listUtils.ListRemoveRepeat;
import pers.utils.other.KibanaUtils;
import pers.utils.timeUtils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String body0 = "{\"index\":\"{indexEv}\",\"ignore_unavailable\":true,\"timeout\":30000,\"preference\":1564455142933}\n{\"version\":true,\"size\":5000,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"10m\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"{query}\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestamp\":{\"gte\":{startTime},\"lte\":{endTime},\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647}}\n";
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
        JSONArray responses = null;
        try {
            responses = JSONObject.fromObject(result).getJSONArray("responses");
        } catch (Exception e) {
            System.out.println(responses);
            e.printStackTrace();
        }
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
        String search = "header:*xcw6662* AND url:*getMessageListWeb*";//url:*userAgentReport* AND header:*9999165*
        String startTime = "2019-12-03 00:00:00";
        String endTime = "2019-12-03 23:00:00";
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
        String startTime = "2019-11-25 19:39:00";
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

    @Test(description = "棋牌彩票虚假数据统计概率")
    public static void test01c() {
        String search = "remark:*统计数据* AND server-name:*game-toubao*";
        //String startTime = "2019-12-06 21:27:00";
        //String endTime = "2019-12-07 23:59:59";
        String startTime = "2019-12-10 00:00:00";
        String endTime = "2019-12-11 23:59:59";
        String env = "master-info-*";
        JSONArray hits = queryKibana(search, startTime, endTime, env);
        List<String> list = new ArrayList<>();
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
                //String remark = source.getString("remark");
                String detail = source.getString("detail");
                //System.out.println(detail);
                // list.add(remark.substring(0, 8) + "`" + detail);
                //System.out.println(detail);
                lotteryGameompile(detail);
            }
        }
        System.out.println(list.size());
        //  KibanaUtils.longhuXuni(list);
    }


    public static void main(String[] args) {
        List<String> list = FileUtil.readFile(KibanaData.class.getResourceAsStream("/test/b.txt"));
        for (String s : list) {
            String[] ss = s.split(";");
            String[] ss0 = ss[1].split(",");
            int result; //0龙虎 1 虎
            if (Integer.parseInt(ss0[0]) / 4 > Integer.parseInt(ss0[1]) / 4) {
                result = 1;
            } else if (Integer.parseInt(ss0[0]) / 4 < Integer.parseInt(ss0[1]) / 4) {
                result = 2;
            } else {
                result = 3;
            }
            String ss01 = String.format("%s,%s;%s,%s", ss[0], ss0[0], ss0[1], result);
            String ss02 = String.format("insert into test2(name1,name2,name3) values(%s,'%s;%s',%s);", ss[0], ss0[0], ss0[1], result);
            System.out.println(ss02);
        }
    }

    @Test(description = "测试")
    public static void test0c() {
        List<String> list = FileUtil.readFile(KibanaData.class.getResourceAsStream("/test/c.txt"));
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);

            String[] ss1 = s.split(";");

            String[] ss0 = ss1[1].split(",");
            //StringBuilder sb = new StringBuilder();
            int result; //0龙虎 1 虎
            if (Integer.parseInt(ss0[0]) / 4 > Integer.parseInt(ss0[1]) / 4) {
                result = 1;
            } else if (Integer.parseInt(ss0[0]) / 4 < Integer.parseInt(ss0[1]) / 4) {
                result = 2;
            } else {
                result = 3;
            }
            System.out.println(String.format("%s - %s - %s", ss1[0], ss1[1], result));
        }
    }

    /**
     * 解析 []中的数据
     *
     * */
    public static void lotteryGameompile(String str) {
        //String str = "期号:12101433,开奖结果:[1,0,0,1],假投注:[0,2279,2027,0],真投注:[4880,1821,3123,3760],大小条件局数:496,单双条件局数:412,总局数:1870统计数据:大小0.265单双0.22";
        Pattern pattern = Pattern.compile("(?<=\\[)(.+?)(?=\\])");
        Matcher matcher = pattern.matcher(str);
        StringJoiner stringJoiner = new StringJoiner(",");
        while (matcher.find()) {
            stringJoiner.add(matcher.group());
        }
        System.out.println(stringJoiner.toString());
    }


    @Test(description = "测试")
    public static void test01abc() {

    }
}
