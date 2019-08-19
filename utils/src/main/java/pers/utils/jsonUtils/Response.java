package pers.utils.jsonUtils;

//import com.dafagame.beans.http.ResponseCodeEnum;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.reflect.ParameterizedType;

/**
 * @author jacky
 * @date 2019/3/7
 * @project game-api
 *
 * 接收jsonobject
 */
public class Response {
    private String msg;
    private int code;
    private LinkedTreeMap data;

    public Response() {
    }

    public Response(String msg, int code,Object obj) {
        this.msg = msg;
        this.code = code;
        this.data = JsonUtils.objToLinkedMap(obj);
    }

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

//    public <T>T getObj(Class<T> tClass) {
//        return JsonUtils.linkedMapToObj(tClass,this.data);
//    }

    public <T>T getData(Class<T> tClass) {
        return JsonUtils.linkedMapToObj(tClass,this.data);
    }

    public <T>JsonObject getJson(){
        return JsonUtils.toJson(this.data);
    }

    public void setData(Object obj) {
        this.data = JsonUtils.objToLinkedMap(obj);
    }

    public boolean isSuccess() {
        return this.code == 1;
    }

    public final static Response SuccessResponse(){
        Response response = new Response();
        response.setCode(1);
        return response;
    }

    public final static Response DefaultResponse(String msg){
        Response response = new Response();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }
}
