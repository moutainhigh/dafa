package pers.dafagame.dafagameUtils;

import net.sf.json.JSONObject;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.urlUtils.UrlBuilder;

public class PhoneCode {

    private static String sendPhoneCode = Constants.host + "/v1/users/sendPhoneCode";

    public static String sendPhoneCode(HttpConfig httpConfig, String phone) {
        String body = UrlBuilder.custom()
                .addBuilder("phone", phone)
                .addBuilder("phoneCodeType", "2")
                .fullBody();
        httpConfig.body(body).url(sendPhoneCode);
        String result = DafaRequest.post(httpConfig);
        //System.out.println(result);
        return JSONObject.fromObject(result).getString("data");
    }
}
