package pers.dafacloud.kibana;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import pers.dafacloud.utils.BaseException;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

public class KibanaUtils {

    /**
     * 获取数据
     *
     * @param search     查询条件
     * @param startTimes 开始时间
     * @param endTimes   结束时间
     * @param env        查询的环境
     * @return JSONArray hits
     */
    public static JSONArray queryKibana(String search, long startTimes, long endTimes, String env) throws BaseException {
        String url = "https://52.74.25.157/_plugin/kibana/elasticsearch/_msearch";
        //long startTime = TimeUtil.getMillSecond(startTimes);
        //long endTime = TimeUtil.getMillSecond(endTimes);
        String bodyTemp = "{\"index\":\"{indexEv}\",\"ignore_unavailable\":true,\"timeout\":100000,\"preference\":1564455142933}\n{\"version\":true,\"size\":9000,\"sort\":[{\"@timestamp\":{\"order\":\"desc\",\"unmapped_type\":\"boolean\"}}],\"_source\":{\"excludes\":[]},\"aggs\":{\"2\":{\"date_histogram\":{\"field\":\"@timestamp\",\"interval\":\"10m\",\"time_zone\":\"Asia/Shanghai\",\"min_doc_count\":1}}},\"stored_fields\":[\"*\"],\"script_fields\":{},\"docvalue_fields\":[\"@timestamp\"],\"query\":{\"bool\":{\"must\":[{\"query_string\":{\"query\":\"{query}\",\"analyze_wildcard\":true,\"default_field\":\"*\"}},{\"range\":{\"@timestamp\":{\"gte\":{startTime},\"lte\":{endTime},\"format\":\"epoch_millis\"}}}],\"filter\":[],\"should\":[],\"must_not\":[]}},\"highlight\":{\"pre_tags\":[\"@kibana-highlighted-field@\"],\"post_tags\":[\"@/kibana-highlighted-field@\"],\"fields\":{\"*\":{}},\"fragment_size\":2147483647}}\n";
        String body = bodyTemp
                .replace("{indexEv}", env)
                .replace("{query}", search)
                .replace("{startTime}", String.valueOf(startTimes))
                .replace("{endTime}", String.valueOf(endTimes));
        //System.out.println(body);//请求体
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-ndjson")
                .other("kbn-version", "6.3.1")
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36")
                //.other("origin", "https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com")
                //.referer("https://search-dafacloud-jkbqcehoqjsdmjfxlet6u7fb4m.ap-southeast-1.es.amazonaws.com/_plugin/kibana/app/kibana")
                .build();

        HttpConfig httpConfig = HttpConfig.custom().headers(headers).body(body).url(url);
        //
        /*HttpHost proxy = new HttpHost("13.250.0.161", 943, "http");//dafa windows主机
        BasicScheme proxyAuth = new BasicScheme();
        // Make client believe the challenge came form a proxy
        try {
            proxyAuth.processChallenge(new BasicHeader(AUTH.PROXY_AUTH, "BASIC realm=default"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BasicAuthCache authCache = new BasicAuthCache();
        authCache.put(proxy, proxyAuth);
        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope(proxy),
                new UsernamePasswordCredentials("duke", "duke"));
        HttpClientContext context = HttpClientContext.create();
        context.setAuthCache(authCache);
        context.setCredentialsProvider(credsProvider);
        httpConfig.context(context);*/

        JSONArray responses;
        try {
            String result = DafaRequest.post(httpConfig);
            System.out.println(result);
            responses = JSONObject.fromObject(result).getJSONArray("responses");
        } catch (Exception e) {
            System.out.println("responses 返回数据解析json 失败");
            if (e.getMessage().contains("timed out")) {
                throw new BaseException(1, "请求超时");
            }
            throw new BaseException(2, "数据解析json 失败");
        }
        JSONObject one = responses.getJSONObject(0);
        //System.out.println(one.getString("took"));
        //System.out.println("数据量：" + hits.size());
        return one.getJSONObject("hits").getJSONArray("hits");
    }
}
