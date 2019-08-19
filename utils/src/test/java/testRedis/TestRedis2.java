package testRedis;

import org.testng.annotations.Test;
import pers.utils.redisUtils.TestEnvRedis;
import redis.clients.jedis.Jedis;


//https://blog.csdn.net/wgw335363240/article/details/24471311
public class TestRedis2 {


    @Test(description = "测试")
    public static void test01() {
        Jedis jedis = TestEnvRedis.getJedis();
        System.out.println(jedis.keys("*"));
        //sSystem.out.println(jedis.get(""));
    }

}
