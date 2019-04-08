package pers.dafacloud.concurrent;

import java.util.Map;

public class Websockettask2 extends CallableTemplate<Map<String, String>> {

    private Myweb2 client;
    private String userName;

    public Websockettask2(String url, String userName) throws Exception {
        this.client = new Myweb2(url);
        //this.userName = userName;
    }

    @Override
    public Map<String, String> process() {
        client.connect();
        //连接
        int count = 0;
        while (!client.getReadyState().equals(org.java_websocket.WebSocket.READYSTATE.OPEN)) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        try {
            Thread.sleep(TestWebsocket.stepTime);//投注间隔1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.send("xzczczcz");
        return null;
    }
}
