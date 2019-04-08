package pers.dafacloud.controller;

import net.sf.json.JSONObject;
import org.java_websocket.WebSocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pers.dafacloud.webSocketClient.MyWebSocketClient;

import java.net.URISyntaxException;


@Controller
public class Websocket {

    static MyWebSocketClient client = null;

    /*static {
        try {
            client = new MyWebSocketClient(
                    "ws://m.dafacloud-test.com/gameServer/?TOKEN=kihjEdDrNWbaIklREOc6u8Jjb0+93iSqWop3YHbb6saRLi5yBhmxNs1PBHSpeRK3c7RKYXgIjMdyf6uKTmpl7A&gameId=2001");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        client.connect();
        int count = 0;
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            System.out.println("正在连接..." + (count++));
        }
        System.out.println("建立websocket连接");
    }*/

    @ResponseBody
    @RequestMapping(value = "/test")
    public String success(@RequestBody String content) {
        if ("".equals(content))
            return "请求内容为空";
        String issue = client.getIssue();
        if ("".equals(issue)){
            return "期号为空";
        }
        JSONObject jsonObject = JSONObject.fromObject(content);
        String proto = jsonObject.get("proto").toString();
        //JSONObject data = jsonObject.getJSONObject("data");

        if("700".equals(proto)){
            client.setBetResponse("");
            String betContent = String.format(content,issue);
            System.out.println(betContent);
            client.send(betContent);//发送
            int count = 0;
            String betResponse="";
            while (count<5){
                count++;
                betResponse=client.getBetResponse();
                if(!"".equals(betResponse))
                    return betResponse;
            }
            return "响应超时";
        }




        return "发送成功";


    }


}
