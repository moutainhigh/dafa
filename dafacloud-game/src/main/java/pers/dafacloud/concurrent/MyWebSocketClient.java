package pers.dafacloud.concurrent;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MyWebSocketClient extends WebSocketClient {

    //Logger logger = LoggerFactory.getLogger(MyWebSocketClient.class);
    private static Logger logger = Logger.getLogger(MyWebSocketClient.class);

    private List<String> list = new ArrayList<>();
    private static  String issue;//奖期
    private String betResponse; //701返回结果
    public String userRebate;
    public int stateTime;//倒计时
    public int status;  //
    public  int count=0; //
    public static int countOnline=0;
    public  String userName;

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

    public MyWebSocketClient(String url,String userName) throws URISyntaxException {
        super(new URI(url));
        this.userName=userName;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

        countOnline++;
        logger.info(countOnline+","+userName+"握手...");
    }

    @Override
    public void onMessage(String message) {
        //System.out.println("接收："+message);
        logger.info(message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        String proto = jsonObject.get("proto").toString();
        JSONObject data = jsonObject.getJSONObject("data");
        if("713".equals(proto)){ //场景通知
           // timer.cancel();//清楚定时器
            issue = data.get("issue").toString();//奖期
            userRebate = data.get("userRebate").toString();//返点
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status =  Integer.parseInt(data.get("status").toString());//当前状态，1投注，2
            System.out.println("初始化 stateTime："+stateTime+"，status："+status);
        }else if("709".equals(proto)){ //开始下注通知
            //timer.cancel();//清楚定时器
            issue = data.get("issue").toString();
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 1;
        }else if("701".equals(proto)){ //投注回应
            count++;
            betResponse = data.get("msg").toString();
            //System.out.println(System.currentTimeMillis()+","+count+","+message);
            //logger.info(count+","+userName+","+message);
            if(betResponse.contains("失败")){
                logger.info(userName+","+message);
            }

        }else if("710".equals(proto)){ //结算通知
            //timer.cancel();//清楚定时器
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }else if("716".equals(proto)){//结束投注通知
            //timer.cancel();//清楚定时器
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info(userName+"关闭..."+reason+remote);
        //System.out.println(userName+"关闭...");
    }

    @Override
    public void onError(Exception ex) {
        //System.out.println("异常"+ex);
        logger.info(userName+"异常..."+ex);

    }
}
