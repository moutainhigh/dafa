package pers.dafacloud.enums;

import lombok.Getter;

@Getter
public enum TestApiResultEnum {

    SUCCESS("1", "成功"),
    FAIL("-11", "失败"),
    ERROR("-12", "错误"),
    DE_ERROR("-13", "依赖接口错误"),
    //DE_FAIL("-12", "依赖接口失败"),
    //DE1_ERROR("0", "依赖接口失败"),
    //DE2_ERROR("0", "依赖接口2返回失败"),
    DE_NO_DATA("-14", "依赖接口返回空"),
    ;

    private String code;
    private String message;

    TestApiResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
