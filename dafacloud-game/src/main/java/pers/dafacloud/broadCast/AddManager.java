package pers.dafacloud.broadCast;

import org.apache.commons.codec.digest.DigestUtils;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

public class AddManager {
    private static String addTenantManager = "http://pt.dafacloud-test.com/v1/management/manager/addTenantManager";

    /**
     * 后台批量新增管理员
     */
    public static void main(String[] args) {
        String name = "duke";
        for (int i = 5; i < 3000; i++) {
            String name0 = String.format("%s%04d", name, i);
            String body = UrlBuilder.custom()
                    .addBuilder("managerName", name0)
                    .addBuilder("password", DigestUtils.md5Hex(name0 + DigestUtils.md5Hex("123456"))) //3449ad199a674806e7956f93d140533b
                    .addBuilder("realName")
                    .addBuilder("managerTroleId", "100064")
                    .fullBody();
            String result = DafaRequest.post(addTenantManager, body, "3BA670E725453BDA85D51A29A4803AD3");
            System.out.println(name0 + "," + result);
        }
    }
}


