package pers.dafacloud.TestCase;


import pers.dafacloud.utils.LotteryRequest;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class AddTenant {

    public static void addTenant() {
        String path = "/v1/management/tenant/addTenant";
        String body = UrlBuilder.custom()
                .addBuilder("tenantCode", "autotest")
                .addBuilder("name", "autotest")
                .addBuilder("webTitle", "autotest")
                .addBuilder("admin", "autotest")
                .addBuilder("password", "6fb8f59ce1a44f7fe24d0b617e0038d3")
                .addBuilder("topAgent", "autotest88")
                .addBuilder("testAgent", "autotest")
                .addBuilder("styleId", "1")
                .addBuilder("url", "bc.abc.com")
                .addBuilder("ip", "1.1.1.1")
                .fullBody();
        System.out.println(LotteryRequest.postCms(path, body));
    }

    public static void main(String[] args) {
        addTenant();
    }

}
