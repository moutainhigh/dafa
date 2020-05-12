package com.dafagame.dafaGameUtils.cardLogic;


//import com.dafagame.cardLogic.ZjhCards;
//import com.dafagame.common.Player;
//import com.dafagame.config.InitBaseConfig;
//import com.dafagame.model.BattleGameRecord;
import com.dafagame.protocol.world.World;
import com.dafagame.protocol.zjh.Zjh;

import java.math.BigDecimal;

public class ZjhPlayer extends Player{
//====================================================
    // 玩家当前所在场次
    public volatile String roundType;
    // 玩家当前房间
    //public volatile ZjhRoom curRoom = null;
    //玩家的座位号
    public int seatId = -1;

    public Zjh.PlayerStatus status = Zjh.PlayerStatus.Bystander;

    public boolean offLine = false;

    public int noOptTime = 0;

    //是否掉线等待过 上一个玩家操作后切换到该玩家后重置
    public boolean isoffLineWaited = false;
    //是否闷牌
    public boolean isDark = true;
    //是否跟到底
    public boolean isFollowEnd = false;
    //总投注
    public BigDecimal totalBet = BigDecimal.ZERO;
    //服务费
    public BigDecimal tax = BigDecimal.ZERO;

    public String comparePlayer = "";

    public String compareSeat = "";

    //手牌
    public ZjhCards cards = null;

    //public BattleGameRecord record;

    //public ZjhPlayer(World.Player player){
    //    super(player);
    //}

    //每局开始重置玩家投注数据
    public void startReset(){
        isoffLineWaited = false;
        status = Zjh.PlayerStatus.Play;
        isDark = true;
        isFollowEnd = false;
        totalBet = BigDecimal.ZERO;
        cards = null;
        tax = BigDecimal.ZERO;
        comparePlayer = "";
        compareSeat = "";
    }

    public void createRecordModel(String roomNumber, String roundType){
        //record = new BattleGameRecord();
        //record.setRoomNumber(roomNumber);
        //record.setRoundType(roundType);
        //record.setGameCode(InitBaseConfig.game.gameCode);
        //record.setTenantCode(tenantCode);
        //record.setUserId(uid);
        //record.setUserName(userName);
        //record.setSourceId(sourceId);
        //record.setUserType(userType);
    }

    //public boolean isRobot(){
    //    return "robot".equals(tenantCode);
    //}
}
