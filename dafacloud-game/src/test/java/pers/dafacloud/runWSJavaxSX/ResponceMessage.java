package pers.dafacloud.runWSJavaxSX;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.websocket.*;

@ClientEndpoint
public class ResponceMessage {

    private String issue;//奖期
    private String betResponse; //701返回结果
    private int betResponseCode; //701返回结果
    private String userRebate;//返点
    private int stateTime;//倒计时
    private int status;
    private String username;
    private long returnTime;

    public ResponceMessage(String username) {
        this.username = username;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getBetResponse() {
        return betResponse;
    }

    public String getUserRebate() {
        return userRebate;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStateTime() {
        return stateTime;
    }

    public void setStateTime(int stateTime) {
        this.stateTime = stateTime;
    }

    public int getBetResponseCode() {
        return betResponseCode;
    }

    public void setBetResponseCode(int betResponseCode) {
        this.betResponseCode = betResponseCode;
    }

    final static Object lock = new Object();

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Connected to endpoint: " + username + "," + session.getBasicRemote());
    }

    @OnMessage
    public void onMessage(String message) {
        //System.out.println(message);
        JSONObject jsonObject = JSONObject.fromObject(message);
        String proto = jsonObject.get("proto").toString();
        JSONObject data = jsonObject.getJSONObject("data");
        if ("713".equals(proto)) { //场景通知
            issue = data.get("issue").toString();//奖期
            userRebate = data.get("userRebate").toString();//返点
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = Integer.parseInt(data.get("status").toString());//当前状态，1投注，2
            //System.out.println("初始化 stateTime："+stateTime+"，status："+status);
        } else if ("709".equals(proto)) { //开始下注通知
            issue = data.get("issue").toString();
            //System.out.println(issue);
            synchronized (lock) {
                if (!issue.equals(StartWs.currentIssue)) {
                    //System.out.println("map------" + StartWs.map);
                    StartWs.currentIssue = issue;
                    StartWs.map.clear();
                    StartWs.map.put(issue, 0);
                }
            }
            this.setIssue(data.get("issue").toString());
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 1;
        } else if ("701".equals(proto)) { //投注回应
            betResponseCode = data.getInt("code");
            if (betResponseCode == 1) {
                betResponse = data.get("betReqInfo").toString();
                System.out.println(username + "-" + message);//+System.currentTimeMillis()
                //返回成功则写入数据库。1.设置flag和备注，成功设置true，否则设置  false+备注（失败类型）
                JSONArray bettingAmount = data.getJSONArray("betReqInfo").getJSONObject(0).getJSONArray("bettingAmount");
                int total = 0;
                for (int i = 0; i < bettingAmount.size(); i++) {
                    total += bettingAmount.getInt(i);
                }
                if (StringUtils.isNotEmpty(StartWs.currentIssue)) {
                    int origin = 0;
                    if (StartWs.map.size() > 0) {
                        //System.out.println(StartWs.map);
                        origin = StartWs.map.get(StartWs.currentIssue);
                    }
                    StartWs.map.put(StartWs.currentIssue, origin + total);
                }
            }
            betResponse = data.toString();
            returnTime = System.currentTimeMillis();
        } else if ("710".equals(proto)) { //结算通知
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        } else if ("716".equals(proto)) {//结束投注通知
            stateTime = Integer.parseInt(data.get("stateTime").toString());//倒计时
            status = 2;
        }
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
