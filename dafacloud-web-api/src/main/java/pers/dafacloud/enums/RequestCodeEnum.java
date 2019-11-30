package pers.dafacloud.enums;

public enum RequestCodeEnum {
	
	/**
     * 成功
     */
    CODE1(1),
    /**
     * 逻辑不通
     */
    CODENEGATIVE1(-1),
	/**
     * 异常
     */
    CODENEGATIVE2(-2);

    private int code;

    public int getCode() {
        return code;
    }

    RequestCodeEnum(int code){
        this.code = code;
    }

}
