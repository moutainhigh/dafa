package pers.dafagame.utils.enums;

public enum Param {
    userId("?userId=50000433")
    ,pageSetting("?pageSize=10&pageNum=1")
    ,messageContent("?id=427&messageType=1")
    ,inviteCode("?pageSize=10&pageNum=1&isAgent=1")
    ,userManage("?userName=wesley1son&isAgent=-1")
    ,announcementContentId("?id=98")
    ,getTransactionRecordsFront("?pageSize=10&dateType=sevenday&pageNum=1&self=true&dictionId=-1&")
    ,getBetData("?dateType=sevenday&state=1&lotteryCode=-1&pageNum=1&pageSize=10&");

    public String params;
    //构造方法
    Param(String params){this.params = params;};
}
