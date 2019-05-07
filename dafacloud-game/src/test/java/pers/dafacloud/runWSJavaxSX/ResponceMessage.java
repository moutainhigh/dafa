package pers.dafacloud.runWSJavaxSX;


import net.sf.json.JSONObject;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class ResponceMessage {

    private List<String> list = new ArrayList<>();
    private String issue;//奖期
    private String betResponse; //701返回结果
    private String userRebate;//返点
    private int stateTime;//倒计时
    private int status;
    private  int count=0;
    private String username;

    public ResponceMessage(String username){
        this.username=username;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public  String getIssue() {
        return issue;
    }

    public  void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBetResponse() {
        return betResponse;
    }

    public void setBetResponse(String betResponse) {
        this.betResponse = betResponse;
    }

    public String getUserRebate() {
        return userRebate;
    }

    public void setUserRebate(String userRebate) {
        this.userRebate = userRebate;
    }

    public int getStateTime() {
        return stateTime;
    }

    public void setStateTime(int stateTime) {
        this.stateTime = stateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " +username+","+ session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        //System.out.println(message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        String proto = jsonObject.get("proto").toString();
        JSONObject data = jsonObject.getJSONObject("data");
        if("713".equals(proto)){ //场景通知
            //this.setIssue("");
            issue = data.get("issue").toString();//奖期
            userRebate = data.get("userRebate").toString();//返点
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status =  Integer.parseInt(data.get("status").toString());//当前状态，1投注，2
            //System.out.println("初始化 stateTime："+stateTime+"，status："+status);
        }else if("709".equals(proto)){ //开始下注通知

            issue = data.get("issue").toString();
            this.setIssue(data.get("issue").toString());
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 1;
        }else if("701".equals(proto)){ //投注回应
            count++;
            betResponse = data.get("msg").toString();
            //System.out.println(System.currentTimeMillis()+","+count+","+message);

        }else if("710".equals(proto)){ //结算通知
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }else if("716".equals(proto)){//结束投注通知
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        System.out.println(username+"关闭websocket："+session.getMaxBinaryMessageBufferSize()+","+reason+session.getMaxTextMessageBufferSize());
    }
}
