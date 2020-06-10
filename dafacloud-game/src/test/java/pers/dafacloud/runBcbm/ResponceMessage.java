package pers.dafacloud.runBcbm;


import lombok.Getter;
import lombok.Setter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.dafacloud.enums.BcbmCodeEmu;

import javax.websocket.*;

@ClientEndpoint
public class ResponceMessage {

    @Getter
    private String issue;//奖期
    private String betResponse; //701返回结果
    private int betResponseCode; //701返回结果
    @Getter
    private String userRebate;//返点
    private String username;

    @Getter
    @Setter
    private boolean canBetting;
    @Getter
    int daCount = 0;

    public ResponceMessage(String username) {
        this.username = username;
    }


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
            canBetting = data.getInt("status") == 1;//当前状态，1投注，2
            //System.out.println("初始化 stateTime："+stateTime+"，status："+status);
        } else if ("709".equals(proto)) { //开始下注通知
            canBetting = true;
            issue = data.getString("issue");
        } else if ("701".equals(proto)) { //投注回应
            betResponseCode = data.getInt("code");
            System.out.println(username + " - 投注回应 : " + message);
        } else if ("710".equals(proto)) { //结算通知
            String pokers = data.getString("pokers");
            BcbmCodeEmu bcbmCodeEmu = BcbmCodeEmu.getNameByNum(pokers);
            System.out.println(issue + ":" + bcbmCodeEmu.name);
            if (bcbmCodeEmu != null && bcbmCodeEmu.isBig == 1) {
                daCount++;
            } else {
                daCount = 0;
            }
            canBetting = false;
        } else if ("716".equals(proto)) {//结束投注通知
            canBetting = false;
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
