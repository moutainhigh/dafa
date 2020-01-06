package pers.dafacloud.utils;


public class ResponseSys<T> extends Response {

    private T dataNew;

    public ResponseSys(T dataNew) {
        this.dataNew = dataNew;
    }

    public T getDataNew() {
        return dataNew;
    }

    public void setDataNew(T dataNew) {
        this.dataNew = dataNew;
    }

    public static <T> ResponseSys<T> success2(T data) {
        return new ResponseSys<>(data);
    }

}
