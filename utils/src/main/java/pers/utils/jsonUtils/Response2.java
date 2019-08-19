package pers.utils.jsonUtils;


import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author jacky
 * @date 2019/3/7
 * @project game-api
 * <p>
 * 接收jsonobject
 */

public class Response2 {
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }



    public void setData(String data) {
        this.data = data;
    }

    private String msg;
    private int code;
    private String data;

    public Response2(String msg, int code, String data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == 1;
    }


    public JSONObject getJSONObject() {
        return JSONObject.fromObject(data);
    }

    public JSONArray getJSONArray() {
        return JSONArray.fromObject(data);
    }

    public JSONArray getInt() {
        return JSONArray.fromObject(data);
    }







}
