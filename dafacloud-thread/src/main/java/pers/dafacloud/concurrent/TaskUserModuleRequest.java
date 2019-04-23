package pers.dafacloud.concurrent;

import org.apache.http.cookie.Cookie;
//import org.apache.log4j.Logger;
import pers.dafacloud.constans.Environment;
import pers.dafacloud.httpUtils.Request;

import java.util.HashMap;
import java.util.Map;

public class TaskUserModuleRequest extends CallableTemplate<Map<String, String>> {

    //private static Logger logger = Logger.getLogger(TaskUserModuleRequest.class);
    private static Environment environment = Environment.DEFAULT;
    private Cookie cookie;
    public TaskUserModuleRequest(Cookie cookie){
        this.cookie=cookie;
    }

    @Override
    public Map<String, String> process() {
        Map<String, String> map = new HashMap<>();
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.put("Origin", environment.url);
        for (int i = 0; i < 1; i++) {
            Request.doGet(environment.url+"/v1/users/unReadMessage?",headers,cookie);
        }
        map.put("result", "process完成");
        return map;
    }
}
