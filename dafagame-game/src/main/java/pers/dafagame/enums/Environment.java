package pers.dafagame.enums;

public enum Environment {

    //DEFAULT("http://caishen01.com");
    DEFAULT("http://duke.dafagame-testCookie.com:86");
    //DEFAULT("http://m.caishen02.com");
    //DEFAULT("http://2019.dafacloud-admin.com");
    //DEFAULT("http://pt.dafacloud-pre.com");
    //http://pt02.dafacloud-test.com
    //http://pt01.dafacloud-test.com
    //TEST("http://dafagame-test.com"),
    //TESTAPP("http://app.dfcdn5.com");
    //http://192.168.8.193

    public String url;

    Environment(String url) {
        this.url = url;
    }

}