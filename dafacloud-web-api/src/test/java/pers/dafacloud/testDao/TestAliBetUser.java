package pers.dafacloud.testDao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.dafacloud.server.BetContentServer;
import pers.dafacloud.server.BetUsersServer;
import pers.utils.fileUtils.FileUtil;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestAliBetUser {

    @Autowired
    BetUsersServer betUsersServer;

    @Autowired
    BetContentServer betContentServer;


    @Test
    public void test01() {
        //type :1 url 1个字段，2 ip 3个字段 ； userType 1平台 ，2站长彩（要单独区分），
        //List<Map> mapList = betUsersServer.getBetUsersList(1, 1, "dev1", "dafa");
        //System.out.println(mapList.size());
        //List<String> list = FileUtil.readFile(TestAliBetUser.class.getResourceAsStream("/users/preDafaIP.txt"));
        //List<Map> mapListUser = new ArrayList<>();
        //for (String s : list) {
        //    String[] sa = s.split(",");
        //    Map map = new HashMap();
        //    map.put("userName", sa[0]);
        //    map.put("userId", sa[1]);
        //    map.put("tenantCode", "dafa");
        //    map.put("evCode", "pre");
        //    map.put("userType", "1");
        //    mapListUser.add(map);
        //}
        //System.out.println(betUsersServer.insertBetUsers(mapListUser));

        List<String> listo = betContentServer.getBetContentList(1, "1407");
        System.out.println(listo.size());

    }


    @Test()
    public void test02() {
        List<String> list = FileUtil.readFile(TestAliBetUser.class.getResourceAsStream("/users/a.txt"));
        List<Map> mapListUser = new ArrayList<>();
        for (String s : list) {
            String[] sa = s.split(",");
            Map map = new HashMap();
            map.put("tenantCode", sa[0]);
            map.put("userName", sa[1]);
            map.put("userId", sa[2]);
            map.put("evCode", "dev1");//dev1,dev2,pre,pro
            map.put("userType", "2");//userType 1平台 ，2站长彩（要单独区分）
            mapListUser.add(map);
        }
        System.out.println(betUsersServer.insertBetUsers(mapListUser));
    }


}
