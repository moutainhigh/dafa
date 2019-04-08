package pers.dafacloud.webSocketClient;

import net.sf.json.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MyWebSocketClient extends WebSocketClient {

    Logger logger = LoggerFactory.getLogger(MyWebSocketClient.class);

    private List<String> list = new ArrayList<>();
    private static  String issue;//奖期
    private String betResponse; //701返回结果
    public String userRebate;
    public int stateTime;//倒计时
    public int status;
    public  int count=0;

    public String getBetResponse() {
        return betResponse;
    }

    public void setBetResponse(String betResponse) {
        this.betResponse = betResponse;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public MyWebSocketClient(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("握手...");
    }

    @Override
    public void onMessage(String message) {
        //System.out.println("接收："+message);
        //logger.info(message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        String proto = jsonObject.get("proto").toString();
        JSONObject data = jsonObject.getJSONObject("data");
        if("713".equals(proto)){ //场景通知
           // timer.cancel();//清楚定时器
            issue = data.get("issue").toString();//奖期
            userRebate = data.get("userRebate").toString();//返点
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status =  Integer.parseInt(data.get("status").toString());//当前状态，1投注，2
            //System.out.println("初始化 stateTime："+stateTime+"，status："+status);
        }else if("709".equals(proto)){ //开始下注通知
            //timer.cancel();//清楚定时器
            issue = data.get("issue").toString();
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 1;
        }else if("701".equals(proto)){ //投注回应
            count++;
            betResponse = data.get("msg").toString();
            //System.out.println(System.currentTimeMillis()+","+count+","+message);

        }else if("710".equals(proto)){ //结算通知
            //timer.cancel();//清楚定时器
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }else if("716".equals(proto)){//结束投注通知
           // timer.cancel();//清楚定时器
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("异常"+ex);

    }
}
