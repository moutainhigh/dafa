package pers.dafacloud.utils.enums;

public enum Environment {

    DEFAULT("http://app.dfcdn5.com","app.dfcdn5.com"),
    TEST("http://dafacloud-test.com","pers.dafacloud-test.com"),
    PRO("http://caishen01.com","caishen01.com"),
    pre("http://dafacloud-pre.com","pers.dafacloud-pre.com"),
    TESTAPP("http://app.dfcdn5.com","app.dfcdn5.com");


    public String url;
    public  String domain;

    Environment(String url, String domain) {
        this.url = url;
        this.domain = domain;
    }

}