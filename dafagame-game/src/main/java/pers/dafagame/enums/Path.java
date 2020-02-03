package pers.dafagame.enums;

public enum Path {

    //用户服务
    login(":7010/v1/users/login")
    ,addBankCard(":7010/v1/users/addBankCard")  //前端添加银行卡

    //交易服务 ===========================================================================
    //前台 提现
    ,frontWithdrawRecord(":7020/v1/transactionManage/frontWithdrawRecord") //提现展示接口（前台GET）
    ,saveFrontWithdrawRecord(":7020/v1/transactionManage/saveFrontWithdrawRecord") // 提现保存接口（前台POST）
    //前台 交易查询
    ,frontSummaryPaymentRecordList(":7020/v1/transactionManage/frontSummaryPaymentRecordList") //前端充值记录查询（前台GET）
    ,frontTradingWithdrawRecordList(":7020/v1/transactionManage/frontTradingWithdrawRecordList") //前端提现记录


    //前台 充值 支付充值接口
    ,rechargeFrontPaymentRecord(":7020/v1/transactionManage/rechargeFrontPaymentRecord") //支付充值接口(前台POST)
    //后台
    ,saveOrUpdateQQ(":7020/v1/transactionManage/saveOrUpdateQQ") //QQ钱包支付新增和更新接口（后台POST）
    ,updateQQPaymentListSort(":7020/v1/transactionManage/updateQQPaymentListSort")  //银行转账--排序接口:等级限制（后台POST）
    ,saveOrUpdateWechat(":7020/v1/transactionManage/saveOrUpdateWechat") //微信支付新增和更新接口（后台POST）
    ,updateWechatPaymentListSort(":7020/v1/transactionManage/updateWechatPaymentListSort") //微信支付---排序接口（后台POST）


    //后台新增第三方（平台设置 > 第三方管理）
    ,saveOrUpdateThirdPartySettings(":7020/v1/transactionManage/saveOrUpdateThirdPartySettings")
    ,saveOrUpdateFourthParty(":7020/v1/transactionManage/saveOrUpdateFourthParty") //后台新增第四方（支付管理 > 第四方管理）
    ,updateFourthPartySettingsListSort(":7020/v1/transactionManage/updateFourthPartySettingsListSort")//第四方排序
    ,rechargeFourthPartyPaymentRecord(":7020/v1/transactionManage/rechargeFourthPartyPaymentRecord")



    //后台 人工存款
    ,manualRecordList(":7020/v1/transactionManage/manualRecordList")
           // "2019-05-21&endTime=2019-05-22&pageNum=1&pageSize=20&inOutFlag=1") //人工存取款查询接口（后台GET）
    ,saveBatchManualRecord(":7020/v1/transactionManage/saveBatchManualRecord")//人工存款保存接口（后台POST）
    ,importManualRecord(":7020/v1/transactionManage/importManualRecord") // 人工存取款导入接口（后台POST）

    //后台 提现
    ,withdrawRecordList(":7020/v1/transactionManage/withdrawRecordList")//提现管理 > 提现订单管理"查询"和"导出"接口（后台GET）
    ,queryWithdrawOrderDetails(":7020/v1/transactionManage/queryWithdrawOrderDetails")//提现管理 > 提现订单管理  点击提现金额  显示的信息（后台GET）
    ,userProfitCMS(":7040/v1/report/userReport/userProfitCMS") //提现管理 > 提现订单管理  点击提现金额  显示的信息（后台GET）
    ,tradingWithdrawRecordList(":7020/v1/transactionManage/tradingWithdrawRecordList")//交易记录查询 > 提现记录（后台GET）

    ,updateWithdrawRecordStatus(":7020/v1/transactionManage/updateWithdrawRecordStatus") //提现订单管理 > 确认入款操作（后台POST）


    //后台 充值
    ,paymentRecordList(":7020/v1/transactionManage/paymentRecordList") //一般充值和快捷查询接口，第四方充值查询接口（后台GET）
    ,getAccountNameCondition(":7020/v1/transactionManage/getAccountNameCondition") //交易管理 > 一般充值订单中的条件查询中的账户查询(下拉框)（后台GET）
    ,summaryPaymentRecordList(":7020/v1/transactionManage/summaryPaymentRecordList") //交易记录查询 > 充值记录（后台GET）

    ,updateSummaryPaymentRecordState(":7020/v1/transactionManage/updateSummaryPaymentRecordState") //一般确认接口（后台POST）
    ,updateThirdPartyPaymentState(":7020/v1/transactionManage/updateThirdPartyPaymentState")//第三方快捷接口强制入款（后台POST）


    //其他 ===========================================================================
    ,saveTenantBroadcast(":7030/v1/management/tenant/saveTenantBroadcast")
    ,editUsersInfoWithdrawLimit(":7010/v1/users/editUsersInfoWithdrawLimit") //后台修改用户提现次数

    //,bankTransferList(":7020/v1/transactionManage/bankTransferList") //获取
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
