package com.dafagame.dafaGameUtils.cardLogic;

import com.dafagame.protocol.world.World;
//import com.dafagame.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Player {
    private final static Logger logger = LoggerFactory.getLogger(Player.class);

    // 玩家id
    protected int uid;

    protected String userName;
    // 玩家昵称
    protected String nickName;
    // 用户头像
    protected int avatar;
    // 玩家余额
    protected volatile BigDecimal balance;
    // 保险箱
    protected BigDecimal safeBalance;
    // vip等级
    protected int grade;
    //站长编码
    protected String tenantCode;
    //终端
    protected int sourceId;
    //账号类型 0普通玩家/1测试/2游客
    protected int userType;
    //代理等级
    protected int proxyGrade;
    protected int frameId;

    protected String ip;

    //玩家类型 -2测试站正式账号,-1每个站的测试账号 0普通玩家 1-4机器人
    protected int type;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getSafeBalance() {
        return safeBalance;
    }

    public void setSafeBalance(BigDecimal safeBalance) {
        this.safeBalance = safeBalance;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public static Logger getLogger() {
        return logger;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getProxyGrade() {
        return proxyGrade;
    }

    public void setProxyGrade(int proxyGrade) {
        this.proxyGrade = proxyGrade;
    }

    public int getFrameId() {
        return frameId;
    }

    public int getType() {
        return type;
    }

    public boolean isRobot(){
        return type > 0;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Player() {
    }

    public Player(World.Player player) {
        this.uid = player.getUid();
        //this.userName = player.getUserName();
        //this.nickName = player.getNickName();
        //this.avatar = player.getAvatar();
        //this.grade = player.getGrade();
        //this.tenantCode = player.getTenantCode();
        //this.sourceId = player.getSourceId();
        //this.userType = player.getUserType();
        //this.proxyGrade = player.getProxyGrade();
        //this.frameId = player.getFrameId();
        //this.ip = player.hasClientIp() ? player.getClientIp() : "";
        //if (userType == 1){
        //    this.type = -1;//每个站的测试账号
        //} else { //0普通玩家 1-4机器人
        //    this.type = player.hasType() ? player.getType() : 0;
        //}
    }

    public String nickName(World.Player player) {


        //if (!StringUtils.isEmpty(player.getNickName())) {
        //    if(player.getUserType() == UserType.VISITOR.ordinal() && player.getNickName().length() == 10) {
        //        String headStr = player.getNickName().substring(0, 2);
        //        if (headStr.equals("游客")) {
        //            headStr = player.getNickName().substring(0, 4);
        //            String tail = player.getNickName().substring(8, 10);
        //            return headStr + "**" + tail;
        //        }
        //    }
        //
        //    return player.getNickName();
        //}

        StringBuffer buffer = new StringBuffer();
        if(player.getUserName().length() == 8) {
            String headStr = player.getUserName().substring(0, 2);

            String tail = player.getUserName().substring(6, 8);

            buffer.append(headStr).append("**").append(tail);
        }
        return buffer.toString();
    }

    public boolean isTestPlayer() {
        return type == -1 || userType == 1;
    }
}
