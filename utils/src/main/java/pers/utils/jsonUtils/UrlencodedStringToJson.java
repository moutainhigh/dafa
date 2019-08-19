package pers.utils.jsonUtils;

import com.google.gson.JsonObject;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UrlencodedStringToJson {


    @Test(description = "测试")
    private static void test01() {
        System.out.println(transStringToMap("a=1&b=2"));
    }

    /**
     * a=1&b=2 转map，来源网上
     */
    private static Map<String, Object> transStringToMap(String mapString) {
        Map<String, Object> map = new HashMap<String, Object>();
        String[] fSplit = mapString.split("&");
        for (int i = 0; i < fSplit.length; i++) {
            if (fSplit[i] == null || fSplit[i].length() == 0) {
                continue;
            }
            String[] sSplit = fSplit[i].split("=");
            String value = fSplit[i].substring(fSplit[i].indexOf('=') + 1, fSplit[i].length());
            map.put(sSplit[0], value);
        }

        return map;
    }

    /**
     * a=1&b=2 转json
     *
     */
    public static JSONObject urlencodedStringToJson(String mapString) {
        JSONObject jsonObject = new JSONObject();
        String[] fSplit = mapString.split("&");
        for (int i = 0; i < fSplit.length; i++) {
            if (fSplit[i] == null || fSplit[i].length() == 0) {
                continue;
            }
            String[] sSplit = fSplit[i].split("=");
            String value = fSplit[i].substring(fSplit[i].indexOf('=') + 1, fSplit[i].length());
            jsonObject.put(sSplit[0], value);
        }

        return jsonObject;
    }

}
