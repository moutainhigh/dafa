package pers.dafacloud.websocket;



import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;


public class WebsocketClient {

    public static WebSocketClient client;

    public static void main(String[] args) throws InterruptedException, IOException {
        try {
            client =
                    new WebSocketClient(
                            new URI("ws://m.dafacloud-test.com/ws?token=8edd6e45eb827562e4d088c1b463b9e21824c2514d142abe7c581553fee0fff1"), new Draft_6455()) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    //logger.info("握手成功");
                    System.out.println("握手成功");
                }

                @Override
                public void onMessage(String msg) {
                    //logger.info("收到消息==========" + msg);
                    System.out.println("收到消息==========" + msg);
                    if (msg.equals("over")) {
                        client.close();
                    }

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    //logger.info("链接已关闭");
                    System.out.println("链接已关闭");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                    System.out.println("发生错误已关闭");
                    //logger.info("发生错误已关闭");
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        //logger.info(client.getDraft());
        int count = 0;
        while (!client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
            //logger.info("正在连接...");
            System.out.println("正在连接...");
            count++;
        }
        System.out.println("连接次数：" + count);
        String msg1 = "{\"p\":\"msgSend\",\"d\":{\"chatroom\":\"13bcd3ebbdef954e\",\"msgs\":[\"{\\\"p\\\":\\\"msg\\\",\\\"d\\\":\\\"测试31\\\"}\"],\"msg_type\":\"msg\"},\"r\":\"9067565792\"}";
        //client.send(msg1);
        String msg21 = "{\"p\":\"msgSend\",\"d\":{\"chatroom\":\"13bcd3ebbdef954e\",\"msgs\":[\"{\\\"p\\\":\\\"msg\\\",\\\"d\\\":\\\"%s\\\"}\"],\"msg_type\":\"msg\"},\"r\":\"9067565792\"}";
        //client.send(msg21);
        String ss1 = "%s";
        BufferedReader myReader = new BufferedReader(new InputStreamReader(System.in));
        String x1 = myReader.readLine();
        String s2 = String.format(msg21, "qqqqq");
        String ss = String.format(ss1, x1);
        System.out.println(ss);
        client.send(s2);
        System.out.println(222);
//       Scanner scanner1 = new Scanner(System.in);
//       String msg2 = scanner1.nextLine();
// 	   System.out.println("msg2:"+msg2.toString());
        //client.send(msg21);
//       System.out.println(111);
//       Scanner scanner = null;
//       for (int i = 0; i < 100; i++) {
//    	  scanner = new Scanner(System.in);
//    	  String msg = scanner.next();
//    	  System.out.println("msg:"+msg.toString());
//    	  client.send(msg.getBytes());
//    	  System.out.println("发送成功啊1");
//       }
//       scanner.close();
        //client.send("111");
        //连接成功,发送信息
//       Scanner scanner = new Scanner(System.in);
//       String s = scanner.next();
//       client.send(s);


    }
}
