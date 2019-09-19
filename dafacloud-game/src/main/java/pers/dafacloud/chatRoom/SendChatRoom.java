package pers.dafacloud.chatRoom;

import pers.dafacloud.utils.concurrent.CallableTemplate;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Map;

//public class SendChatRoom extends CallableTemplate<Map<String, String>> {
//
//    private String url;
//
//    public SendChatRoom(String url) {
//        this.url = url;
//    }
//
//    @Override
//    public Map<String, String> process() {
//        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//        ChatRoomResponce chatRoomResponce = new ChatRoomResponce("");
//        try {
//            Session session = container.connectToServer(chatRoomResponce, URI.create(url));
//            session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
//            session.setMaxBinaryMessageBufferSize(204800);
//
//            for (int i = 0; i < 10000000; i++) {
//                //session.getBasicRemote().sendText("body: \"{\"id\":440,\"userId\":\"50045206\",\"nickname\":\"du***6\",\"avatar\":\"C362F2B1E0EA389A.jpg\",\"nicknameColorId\":\"\",\"messageColorId\":\"\",\"grade\":-1,\"messageTypeId\":1,\"message\":\"autotest\",\"gmtCreated\":\"2019-05-04 12:27:42.763\"}\"\n" +
//                //        "code: 1\n" +
//                //        "extend: {}");
//                //System.out.println("发送成功");
//                session.getBasicRemote().sendText("{'code':9}");
//                System.out.println("发送成功");
//                Thread.sleep(30000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
