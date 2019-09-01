package kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.timeUtils.TimeUtil;

import java.util.List;

public class KibanaData {

    @Test(description = "测试")
    public static void test01() {
        String url = "https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com/_plugin/kibana/elasticsearch/_msearch";
        //long startTime = TimeUtil.getLCMillSecond();//当天凌晨
        //long endTime = new Date().getTime();//当前时间戳

        long startTime = TimeUtil.getMillSecond("2019-08-02 00:00:00");
        long endTime = TimeUtil.getMillSecond("2019-08-02 23:59:59");
        String indexEv = "master-access-*";
        String query = "header:*223.104.235.180* AND url:*forgetPassword*";
        String body0 = "{\"index\":\"{indexEv}\",\"ignore_unavailable\":true,\"timeout\":30000,\"preference\":1564455142933}\n{\"version\":true,\"size\":500,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"10m\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"{query}\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestamp\":{\"gte\":{startTime},\"lte\":{endTime},\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647}}\n";
        String body = body0
                .replace("{indexEv}", indexEv)
                .replace("{query}", query)
                .replace("{startTime}", String.valueOf(startTime))
                .replace("{endTime}", String.valueOf(endTime));
        System.out.println(body);
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
        System.out.println(one.getString("took"));
        JSONArray hits = one.getJSONObject("hits").getJSONArray("hits");//
        System.out.println("数据量：" + hits.size());
        if (hits.size() != 0) {
            for (int i = 0; i < hits.size(); i++) {
                JSONObject hitsData = hits.getJSONObject(i);
                JSONObject source = hitsData.getJSONObject("_source");
//                String url0 = source.getString("url");
//                System.out.println(url0);
                String header = source.getString("header");
                System.out.println(header);
            }
        }
        //System.out.println(result);

    }

    @Test(description = "用户注册ip")
    public static void test02() {
        String url = "https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com/_plugin/kibana/elasticsearch/_msearch";
        //long startTime = TimeUtil.getLCMillSecond();//当天凌晨
        //long endTime = new Date().getTime();//当前时间戳
        List<String> users = FileUtil.readFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/KibanaDataUser.txt");
        long startTime = TimeUtil.getMillSecond("2019-08-18 00:00:00");
        long endTime = TimeUtil.getMillSecond("2019-08-18 23:59:59");
        String indexEv = "master-access-*";
        for (int j= 0; j < users.size(); j++) {
            //查询条件
            String query = String.format("paramsBody:*%s* AND url:*register*",users.get(j));
            String body0 = "{\"index\":\"{indexEv}\",\"ignore_unavailable\":true,\"timeout\":30000,\"preference\":1564455142933}\n{\"version\":true,\"size\":500,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"10m\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"{query}\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestamp\":{\"gte\":{startTime},\"lte\":{endTime},\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647}}\n";
            String body = body0
                    .replace("{indexEv}", indexEv)
                    .replace("{query}", query)
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
            JSONArray hits = one.getJSONObject("hits").getJSONArray("hits");//
            //System.out.println("数据量：" + hits.size());
            if (hits.size() != 0) {
                for (int i = 0; i < hits.size(); i++) {
                    JSONObject hitsData = hits.getJSONObject(i);
                    JSONObject source = hitsData.getJSONObject("_source");
                    //请求头信息
                    JSONObject header = source.getJSONObject("header");
                    //System.out.println(header.toString());
                    //x-user-name是cookie里面的，并不是真正注册的用户
                    System.out.println(users.get(j)+","+header.getString("x-user-name")+","+header.getString("x-client-ip"));
                    //请求体信息
                    //JSONObject  paramsBody = UrlencodedStringToJson.urlencodedStringToJson(source.getString("paramsBody"));
                    //System.out.println(users.get(j)+","+paramsBody.getString("userName" ));
                }
            }else{
                System.out.println(users.get(j)+"注册请求没有找到");
            }
        }
    }

}
