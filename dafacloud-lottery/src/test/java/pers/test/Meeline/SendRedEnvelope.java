package pers.test.Meeline;

import org.apache.http.Header;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;

public class SendRedEnvelope {
    private static String host = "http://caishen02.com";

    public static void send() {
        String sendRedEnvelope = host + "/api/platform/trans/v2";
        Header[] httpHeader = HttpHeader.custom()
                .contentType("application/json")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                .other("pAuthorization", "5739959b72fa07c58383e574bc3a97d7947ed8101d0c21207ebd16b27651f2db")
                .other("random",    "9311h87181ba").build();
        String body = "{\"p\":\"/v1/transfer/sendRedEnvelopeExternal\",\"d\":{\"third\":{\"amount\":\"2000\",\"wishingRemark\":\"\",\"muid\":\"0003b927\",\"safetyPassword\":\"64844d9f70cfdf62cf4013caee4fadf3\",\"num\":\"1001\",\"type\":\"2\"},\"msgPush\":{\"chatroom\":\"3913df2f71\",\"msg_type\":\"groupRed\",\"msg\":\"{\\\"id\\\":\\\"##RECORDID##\\\",\\\"user_id\\\":\\\"0003b927\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"2\\\"}\",\"uuid\":\"0.6830462685002205\"}},\"r\":\"0892h6094740\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593570496094\"}}";
        String result = DafaRequest.post(HttpConfig.custom().url(sendRedEnvelope).body(body).headers(httpHeader));
        System.out.println(result);
    }

    public static void main(String[] args) {
        send();
    }


}
