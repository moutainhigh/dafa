package pers.dafacloud.chatRoom;


import net.sf.json.JSONObject;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint(configurator=Config.class)
public class ChatRoomResponce{

    private List<String> list = new ArrayList<>();
    private String issue;//奖期
    private String betResponse; //701返回结果
    private String userRebate;
    private int stateTime;//倒计时
    private int status;
    private  int count=0;
    private String username;

    //private  int code = "";

    /*public ChatRoomResponce(String username){
        this.username=username;
    }*/

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
    public void onClose(Session session, CloseReason reason)
    {
        System.out.println(username+"关闭websocket："+session.getMaxBinaryMessageBufferSize()+","+reason+session.getMaxTextMessageBufferSize());
    }
//    @OnClose
//    public void OnClose(String t) {
//        System.out.println(t);
//    }
}
