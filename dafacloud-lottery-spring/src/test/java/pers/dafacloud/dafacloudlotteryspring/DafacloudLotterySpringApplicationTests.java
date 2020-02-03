package pers.dafacloud.dafacloudlotteryspring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pers.dafacloud.configuration.RedisUtil;
import pers.dafacloud.server.BettingRecordInfoServer;
import pers.utils.fileUtils.FileUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DafacloudLotterySpringApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    RedisUtil redisUtil;

    @Autowired
    BettingRecordInfoServer bettingRecordInfoServer;

    /**
     * 查询pro -> 写入test
     */
    @Test
    public void contextLoads() {
        List<String> tenants = FileUtil.readFile(DafacloudLotterySpringApplicationTests.class.getResourceAsStream("/tenantCodePro.txt"));
        System.out.println(tenants.size());
        for (int i = 0; i < tenants.size(); i++) {
            List<Map> lists = bettingRecordInfoServer.geBettingRecordInfoList(tenants.get(i));
            System.out.println("lists.size:" + lists.size());
            if (lists.size() == 0)
                continue;
            int a = bettingRecordInfoServer.insertBettingRecordInfoList(lists);
            System.out.println("insert : " + a);
            lists.clear();
        }

    }


    @Test
    public void test02() {
        //System.out.println("balanceLock_119529:"+redisUtil.get("balanceLock_119529"));
        //JSONObject jsonObject = (JSONObject) redisUtil.get("game_agentInfo_124394");
        //System.out.println(jsonObject);
        //System.out.println("game_agentInfo_124394:"+redisUtil.get("game_agentInfo_124394").toString());
    }

    @Test
    public void test01() {
        //System.out.println(redisTemplate.opsForValue().get("game_agentInfo_124394"));
    }

    public void f1() {
        for (int i = 0; i < 10; i++) {
            int ii = i;
            System.out.println("aaa");
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println(ii + "zxcxzczx");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }


}
