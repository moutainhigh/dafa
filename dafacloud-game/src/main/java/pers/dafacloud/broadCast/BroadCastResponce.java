package pers.dafacloud.broadCast;

import lombok.Data;

import javax.websocket.*;

@ClientEndpoint(configurator = Config.class)
//@ClientEndpoint
@Data
public class BroadCastResponce {

    private String username;

    public BroadCastResponce(String username){
        this.username=username;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(this.username + "::Connected success " + "," + session.getBasicRemote());
        StartBroadCastWs.count++;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("接收消息："+message);
        //JSONObject jsonObject = JSONObject.fromObject(message);
        //if(jsonObject.getInt("code")==6){
        //
        //}
        System.out.println("当前连接数："+StartBroadCastWs.count);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(username + "关闭websocket：" + session.getMaxBinaryMessageBufferSize() + "," + reason + session.getMaxTextMessageBufferSize());
        StartBroadCastWs.count--;
    }
}
