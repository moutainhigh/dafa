package pers.dafacloud.enums;

import lombok.Getter;

@Getter
public enum TestApiResultEnum {

    SUCCESS("1", "成功"),
    ERROR("-11", "接口返回错误"),
    DE_ERROR("-12", "依赖接口返回失败"),
    DE1_ERROR("0", "依赖接口1返回失败"),
    DE2_ERROR("0", "依赖接口2返回失败"),
    DE_NO_DATA("-13", "依赖接口未返回数据"),
    ;

    private String code;
    private String message;

    TestApiResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
