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

    private static String host = "http://52.76.195.164:9877";
    //private static String topic = "lottery-tenant-1";
    private static String topic = "lottery-open";

    @Test(description = "测试")
    public static void test01() {
        String topic = "lottery-tenant-1";
        JSONArray data = getMessageByTopic("2019-10-28 19:50:00", "2019-10-28 20:59:00", "");
        System.out.println("数据条数" + data.size());
        for (int i = 0; i < data.size(); i++) {
            JSONObject datai = data.getJSONObject(i);
            //System.out.println(datai);
            String msgId = datai.getString("msgId");
            String detailMessage = getDetail(msgId, topic);//返回的详情
            JSONObject jo = JSONObject.fromObject(detailMessage);
            JSONObject messageBody = jo.getJSONObject("data").getJSONObject("messageView").getJSONObject("messageBody");//具体投注内容等
            if ("201910281202".equals(messageBody.getString("issue"))) {
                System.out.println(messageBody.toString());
            }
        }
    }

    @Test(description = "lottery-open接收betting的订单信息")
    public static void test02() {
        String topic = "lottery-open";
        JSONArray data = getMessageByTopic("2019-10-30 15:55:55",
                "2019-10-30 15:56:02", topic);//2019-10-30 15:55:58.543
        System.out.println("数据条数" + data.size());
        for (int i = 0; i < data.size(); i++) {
            System.out.println(i);
            JSONObject datai = data.getJSONObject(i);
            String msgId = datai.getString("msgId");
            String TAGS = datai.getJSONObject("properties").getString("TAGS");
            if ("1300".equals(TAGS)) {
                String detailMessage = getDetail(msgId, topic);//返回的详情
                JSONObject jo = JSONObject.fromObject(detailMessage);
                JSONObject messageBody = jo.getJSONObject("data").getJSONObject("messageView").getJSONObject("messageBody");//具体投注内容等
                if ("201910300956".equals(messageBody.getString("issue"))
                        && "1300".equals(messageBody.getString("lotteryCode"))
                ) {
                    System.out.println(messageBody.toString());
                    System.out.println(datai.toString());
                }
            }
        }
    }


    /**
     * 获取基础信息
     * 返回JSONArray
     */
    public static JSONArray getMessageByTopic(String startTime, String endTime, String topic) {
        long begin = TimeUtil.getMillSecond(startTime);
        long end = TimeUtil.getMillSecond(endTime);
        String url = host + "/rocketConsole/message/queryMessageByTopic.query";
        String body = UrlBuilder.custom()
                .url(url)
                .addBuilder("begin", String.valueOf(begin))
                .addBuilder("end", String.valueOf(end))
                .addBuilder("topic", topic)//balance-api
                .fullUrl();
        //明细
        String result = DafaRequest.get(body);
        //System.out.println(result);
        JSONObject jsonObject = JSONObject.fromObject(result);
        JSONArray data = jsonObject.getJSONArray("data");
        return data;
    }

    /**
     * 获取 message 详情
     */
    public static String getDetail(String getMsgId, String topic) {
        String urlDetail = host + "/rocketConsole/message/viewMessage.query";
        String bodyDetail = UrlBuilder.custom()
                .url(urlDetail)
                .addBuilder("msgId", getMsgId)
                .addBuilder("topic", topic)
                .fullUrl();
        String result = DafaRequest.get(bodyDetail);
        return result;
    }
}
