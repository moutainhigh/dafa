package pers.dafacloud.chatRoom;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.dafacloud.loginPage.LoginPage;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpConfig;
import pers.utils.httpclientUtils.HttpCookies;
import pers.utils.httpclientUtils.HttpHeader;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.List;

public class TestBug1 {
    static  WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    public static String host = "m.caishen02.com";
    public static void main(String[] args) throws Exception {
        //ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        //List<SendChatRoom> tasks = new ArrayList<>();
        container.setDefaultMaxSessionIdleTimeout(30000);
        Header[] headers = HttpHeader.custom()
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                //.other("x-user-id", userArray[0])
                //.other("x-tenant-code", userArray[1])
                //.other("x-user-name", userArray[2])
                .other("x-domain", host)
                //.other("x-source-Id", "1")
                //.other("Origin", "http://52.76.195.164")
                .build();
        List<String> users = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-game/src/main/resources/testChatRoom.txt");
        for (int i = 0; i < 100; i++) {
            int ii = i;
            new Thread(() -> {
                String loginBody = LoginPage.getLoginBody(users.get(ii), "123456");
                HttpCookies httpCookies = HttpCookies.custom();
                HttpConfig httpConfig = HttpConfig.custom()
                        //.url("http://52.76.195.164:8111/v1/broadCast/generateKey")
                        //.url("http://m.caishen02.com/v1/broadCast/generateKey")
                        .url("http://"+host+"/v1/users/login")
                        .body(loginBody)
                        .headers(headers).context(httpCookies.getContext());
                String result0 = DafaRequest.post(httpConfig);
                //System.out.println(result0);
                //获取key
                httpConfig.url("http://"+host+"/v1/broadCast/generateKey");
                String result = DafaRequest.get(httpConfig);
                //System.out.println(result);
                String data = JSONObject.fromObject(result).getString("data").replaceAll("\n", "");
                //System.out.println(data);
                String url0 = String.format("ws://"+host+"/v1/broadCast/chat?type=1&key=" +
                        "%s&password=81dc9bdb52d04dc20036dbd8313ed055&roomId=00002&api=/chat&guestCode=", data);
                //System.out.println(url0);
                //SendChatRoom sendChatRoom = new SendChatRoom(url0);
                //WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                ChatRoomResponce chatRoomResponce = new ChatRoomResponce();
                try {
                    Session session = container.connectToServer(chatRoomResponce, URI.create(url0));
                    session.setMaxTextMessageBufferSize(2048000);//设置缓冲文本大小
                    session.setMaxBinaryMessageBufferSize(204800);
                    //session.setMaxIdleTimeout(30000);
                    for (int j = 0; j < 10000000; j++) {
                        //session.getBasicRemote().sendText("body: \"{\"id\":440,\"userId\":\"50045206\",\"nickname\":\"du***6\",\"avatar\":\"C362F2B1E0EA389A.jpg\",\"nicknameColorId\":\"\",\"messageColorId\":\"\",\"grade\":-1,\"messageTypeId\":1,\"message\":\"autotest\",\"gmtCreated\":\"2019-05-04 12:27:42.763\"}\"\n" +
                        //        "code: 1\n" +
                        //        "extend: {}");
                        //System.out.println("发送成功");
                        session.getBasicRemote().sendText("{'code':9}");
                        //System.out.println("发送成功");
                        Thread.sleep(30000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

            Thread.sleep(600);
            //tasks.add(sendChatRoom);
        }
        //List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);//多线程执行
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
