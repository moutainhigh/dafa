package pers.dafacloud.utils.enums;

public enum Path {

    login("/v1/users/login")
    ,betting("/v1/betting/addBetting")
    ,rebate("/v1/users/rebate")
    ,getServerTimeMillisecond("/v1/betting/getServerTimeMillisecond")
    ,openTime("/v1/lottery/openTime?")
    ,inviteCode("/v1/users/inviteCode")
    ,getBankCardListFront("/v1/users/getBankCardListFront?")
    ,card("/v1/users/card")
    ,unReadMessage("/v1/users/unReadMessage")
    ,messageList("/v1/users/messageList")
    ,messageContent("/v1/users/messageContent")
    ,gerInviteCode("/v1/users/inviteCode")
    ,userManage("/v1/users/userManage")
    ,getAnouncement("/v1/users/announcement")
    ,getAnnouncementContent("/v1/users/announcementContent")
    ,queryBalanceFront("/v1/balance/queryBalanceFront")
    ,getTransactionRecordsFront("/v1/balance/getTransactionRecordsFront")
    ,getBetData("/v1/betting/getBetData")
    ,updateInviteCodeRemark("/v1/users/updateInviteCodeRemark")
    ,register("/v1/users/register")
    ;


    public String value;
    //构造方法
    Path(String value) {
        this.value = Environment.DEFAULT.url+value;
    }

    public static void main(String[] args) {
        Path path = Path.login;
        System.out.println(path.value);
        System.out.println(Path.login);
    }

}
