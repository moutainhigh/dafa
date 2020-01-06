package pers.dafacloud.utils;


import pers.dafacloud.enums.RequestCodeEnum;

public class Response<T> {

    private String msg;
    private Integer code;
    private T data;

    public Response() {
    }

    public Response(String msg, Integer code, T data) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Response<T> just(String msg, Integer code, T data) {
        return new Response<>(msg, code, data);
    }

    public static <T> Response<T> success(T data) {
        return new Response<>("获取成功", RequestCodeEnum.CODE1.getCode(), data);
    }

    public static <T> Response<T> returnData(String msg,Integer code ,T data) {
        return new Response<>(msg, code, data);
    }


    public static <T> Response<T> fail(String msg) {
        return new Response<>(msg, RequestCodeEnum.CODENEGATIVE1.getCode(), null);
    }

    public static <T> Response<T> failParam() {
        return new Response<>("参数异常", RequestCodeEnum.CODENEGATIVE1.getCode(), null);
    }

    public static <T> Response<T> error(String msg) {
        return new Response<>(msg, RequestCodeEnum.CODENEGATIVE2.getCode(), null);
    }
}
