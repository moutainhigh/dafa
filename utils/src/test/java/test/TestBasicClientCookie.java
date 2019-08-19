package test;

import org.apache.http.impl.cookie.BasicClientCookie;

public class TestBasicClientCookie {

    public static void main(String[] args) {
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "EAD588C1439978E4A169E7C72CC302CA");
        System.out.println(cookie.getValue());
        cookie.setValue("123456");
        System.out.println(cookie.getValue());
    }

}
