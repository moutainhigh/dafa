package pers.test.chatRoom;

import net.sf.json.JSONObject;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.randomNameAddrIP.RandomIP;

import java.util.List;

public class GetGenerateKey {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        List<String> tokens = FileUtil.readFile(GetGenerateKey.class.getResourceAsStream("/test/token.txt")).subList(0, 1000);
        getGenerateKeytask(tokens);
    }

    public static void getGenerateKeytask(List<String> tokens) {
        HttpConfig httpConfig = HttpConfig.custom();
        for (int i = 0; i < tokens.size(); i++) {
            HttpHeader httpHeader0 = HttpHeader.custom()
                    .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                    .other("X-Token", tokens.get(i));
            httpConfig.headers(httpHeader0.build());
            String result =DafaRequest.get(httpConfig.url("http://caishen02.com/v1/broadCast/generateKey"));
            //System.out.println(result);
            String data = JSONObject.fromObject(result).getString("data");
            System.out.println(data);
        }
    }
}
