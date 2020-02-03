package pers.dafacloud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.configuration.RedisUtil;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class RestRedisController {

    @Resource
    RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/restRedis")
    @RequestMapping("")
    public String function01(String key) {
        // System.out.println(redisUtil.get(key));
        //String s = (String) redisUtil.get(key);
        //System.out.println(s);
        System.out.println(redisTemplate.opsForValue().get(key));
        Map map = JSON.parseObject(redisTemplate.opsForValue().get(key), Map.class);
        System.out.println(map);
        //JSONObject jsonObject = (JSONObject) redisUtil.get(StringUtils.isEmpty(key) ? "game_agentInfo_124394" : key);
        return "";
    }

}
