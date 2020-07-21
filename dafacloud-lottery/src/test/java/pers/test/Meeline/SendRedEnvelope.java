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
                .other("pAuthorization", "a8c22c656c7fc1e4548c8dafaf416753cca4fd6b94fabd3c20424a0df46dc2d0")
                .other("random", "9311h87181ba").build();
        String body = "{\"p\":\"/v1/transfer/sendRedEnvelopeExternal\",\"d\":{\"third\":{\"amount\":\"2000\",\"wishingRemark\":\"\",\"muid\":\"0003b927\",\"safetyPassword\":\"d176837521307af0e3ee2e254a21437c\",\"num\":\"500\",\"type\":\"2\"},\"msgPush\":{\"chatroom\":\"3913df2f71\",\"msg_type\":\"groupRed\",\"msg\":\"{\\\"id\\\":\\\"##RECORDID##\\\",\\\"user_id\\\":\\\"0003b927\\\",\\\"remark\\\":\\\"\\\",\\\"type\\\":\\\"2\\\"}\",\"uuid\":\"0.6830462685002205\"}},\"r\":\"0892h6094740\",\"log\":{\"m\":\"2\",\"js_init_time\":\"1593570496094\"}}";
        String result = DafaRequest.post(HttpConfig.custom().url(sendRedEnvelope).body(body).headers(httpHeader));
        System.out.println(result);
    }

    public static void main(String[] args) {
        send();
    }


}
