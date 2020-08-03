package pers.dafacloud.utils;

import lombok.Getter;
import lombok.Setter;

public class BaseException extends Exception {

    private Integer code;
    @Getter@Setter
    private String message;

    public BaseException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
