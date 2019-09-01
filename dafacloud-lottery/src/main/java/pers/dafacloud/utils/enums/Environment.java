package pers.dafacloud.utils.enums;

public enum Environment {

    //DEFAULT("http://caishen01.com","app.dfcdn5.com"),
    //DEFAULT("https://app.dfanzhuoapp.com","app.dfcdn5.com"),
    DEFAULT("https://2019rsnewdatacloudapp.dafacloudapp.com"),
    TEST("http://dafacloud-testCookie.com"),
    PRO("http://caishen01.com"),
    pre("http://dafacloud-pre.com"),
    TESTAPP("http://app.dfcdn5.com");

    public String url;

    Environment(String url) {
        this.url = url;
    }

}