package pers.dafacloud.chatRoom;

import lombok.Data;
import javax.websocket.*;

@ClientEndpoint(configurator = Config.class)
@Data
public class ChatRoomResponce {

    private String username;

    public ChatRoomResponce(String username){
        this.username=username;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println(this.username + "::Connected success " + "," + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        //System.out.println("接收消息："+message);
        //JSONObject jsonObject = JSONObject.fromObject(message);
        //if(jsonObject.getInt("code")==6){
        //
        //}
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println(username + "关闭websocket：" + session.getMaxBinaryMessageBufferSize() + "," + reason + session.getMaxTextMessageBufferSize());
    }
}
