package pers.utils.jsonUtils;

//import com.dafagame.beans.http.ResponseCodeEnum;
import com.google.gson.internal.LinkedTreeMap;

/**
 * @author jacky
 * @date 2019/3/7
 * @project game-api
 *
 * integer
 */
public class HttpResponse<T> {
    private String msg;
    private int code;
    private T data;

    public HttpResponse() {
    }

    public HttpResponse(String msg, int code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
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

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return this.code == 1;
    }

    public final static HttpResponse SuccessResponse(){
        HttpResponse response = new HttpResponse();
        response.setCode(1);
        return response;
    }

    public final static HttpResponse DefaultResponse(String msg){
        HttpResponse response = new HttpResponse();
        response.setCode(-1);
        response.setMsg(msg);
        return response;
    }
}
