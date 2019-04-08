package pers.dafacloud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/fist")
    public String hello(String aa){
        return "第一个 springboot";

    }

    @ResponseBody
    @RequestMapping("/successtest")
    public String success(String aa){
        /*
        classpath:/META-INF/resources/
                classpath:/resources/
                classpath:/static/
        classpath:/public/
        */
        return "success";//返回success.html 页面。
    }

    @ResponseBody
    //@RequestMapping(value = "/successtest" ,method = RequestMethod.GET)
    @GetMapping("/providers")//等价于上面
    public String getAa(String aa){
        /*
        classpath:/META-INF/resources/
                classpath:/resources/
                classpath:/static/
        classpath:/public/
        */
        return "success";//返回success.html 页面。
    }

    @PostMapping("/login")
    public String login(String name , String value, Map<String,String> map){
        if(!StringUtils.isEmpty(name)&&"123".equals(value)){
            return  "redirect:index.html";
        }
        map.put("msg","用户名密码错误");
        return "login";
    }

}
