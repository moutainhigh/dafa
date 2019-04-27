package pers.dafacloud.beting;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.enums.Path;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.pageLogin.Login;

import java.util.HashMap;
import java.util.Map;

public class GetBetRebate {
    static Path rebatePath = Path.rebate;
    private final static Logger Log = LoggerFactory.getLogger(GetBetRebate.class);

    public static JSONObject getAllRebate(){
        //添加header
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        String result = Request.doGet(rebatePath.value, headers);
        if (!result.contains("成功")){
            Log.info("获取返点失败："+result);
            return null;
        }
        return JSONObject.fromObject(result).getJSONObject("data");

    }

    public static void main(String[] args) {
        Login login = new Login();
        login.getDafaCooike("duke01","123456");
        getAllRebate();
    }

}
