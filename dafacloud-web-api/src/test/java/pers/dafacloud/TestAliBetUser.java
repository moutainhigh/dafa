package pers.dafacloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pers.dafacloud.server.BetUsersServer;
import pers.utils.fileUtils.FileUtil;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestAliBetUser {

    @Autowired
    BetUsersServer betUsersServer;

    @Test
    public void function01() {
        //type :1 url 1个字段，2 ip 3个字段 ； userType 1平台 ，2站长彩（要单独区分），
        //List<Map> mapList = betUsersServer.getBetUsersList(1, 1, "dev1", "dafa");
        //System.out.println(mapList.size());
        List<String> list = FileUtil.readFile(TestAliBetUser.class.getResourceAsStream("/users/dev1DafaIP.txt"));
        List<Map> mapListUser = new ArrayList<>();
        for (String s : list) {
            String[] sa = s.split(",");
            Map map = new HashMap();
            map.put("userName", sa[0]);
            map.put("userId", sa[1]);
            map.put("tenantCode", "dafa");
            map.put("evCode", "dafa");
            map.put("userType", "1");
            mapListUser.add(map);
        }
        System.out.println(betUsersServer.insertBetUsers(mapListUser));
    }

    @Test
    public void test01() {
        //type :1 url 1个字段，2 ip 3个字段 ； userType 1平台 ，2站长彩（要单独区分），
        //List<Map> mapList = betUsersServer.getBetUsersList(1, 1, "dev1", "dafa");
        //System.out.println(mapList.size());
        List<String> list = FileUtil.readFile(TestAliBetUser.class.getResourceAsStream("/users/preDafaIP.txt"));
        List<Map> mapListUser = new ArrayList<>();
        for (String s : list) {
            String[] sa = s.split(",");
            Map map = new HashMap();
            map.put("userName", sa[0]);
            map.put("userId", sa[1]);
            map.put("tenantCode", "dafa");
            map.put("evCode", "pre");
            map.put("userType", "1");
            mapListUser.add(map);
        }
        System.out.println(betUsersServer.insertBetUsers(mapListUser));
    }


}
