package rocketMq;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.timeUtils.TimeUtil;
import pers.utils.urlUtils.UrlBuilder;

/**
 * mq队列测试
 */
public class MqDafaCloudData {

    private static String host = "http://52.76.195.164:8080";

    public static void main(String[] args) {

    }

    @Test(description = "测试")
    public static void test01() {
        long begin = TimeUtil.getMillSecond("2019-08-01 20:00:00");
        long end = TimeUtil.getMillSecond("2019-08-01 23:59:59");
        String url = host + "/rocketConsole/message/queryMessageByTopic.query";
        String body = UrlBuilder.custom()
                .url(url)
                .addBuilder("begin", String.valueOf(begin))
                .addBuilder("end", String.valueOf(end))
                .addBuilder("topic", "balance-api")
                .fullUrl();
        //明细
        String result = DafaRequest.get(body);
        //System.out.println(result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        System.out.println("数据条数" + data.size());
        for (int i = 0; i < data.size(); i++) {
            JSONObject datai = data.getJSONObject(i);
            System.out.println(datai);
            String msgId = datai.getString("msgId");
            String KEYS = datai.getJSONObject("properties").getString("KEYS"); //
            String storeTime = TimeUtil.millSecondToDateString(datai.getLong("storeTimestamp")); //storeTime
            System.out.println(KEYS);
            System.out.println(storeTime);
            //getMsgId(msgId);
        }
    }

    public static void getMsgId(String getMsgId) {
        String urlDetail = host + "/rocketConsole/message/viewMessage.query";
        String bodyDetail = UrlBuilder.custom()
                .url(urlDetail)
                .addBuilder("msgId", getMsgId)
                .addBuilder("topic", "balance-api")
                .fullUrl();
        String result = DafaRequest.get(bodyDetail);
        System.out.println("getMsgId,明细：" + result);
    }
}
