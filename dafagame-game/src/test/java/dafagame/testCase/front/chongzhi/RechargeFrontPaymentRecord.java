package dafagame.testCase.front.chongzhi;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.dafagame.utils.enums.Path;
import pers.utils.ThreadSleep.ThreadSleep;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.urlUtils.UrlBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付充值接口(前台POST)
 * <p>
 * paymentType     YHZZ(3,"银行转账"),
 * WXYB(4,"微信一般"), WXDSF(5,"微信第三方"), ZFBYB(6,"支付宝一般"),ZFBDSF(7,"支付宝第三方"),
 * QQQBYB(8,"QQ钱包一般"),QQQBDSF(9,"QQ钱包第三方"),YLYB(10,"银联一般"),YLDSF(11,"银联第三方");
 *
 */
public class RechargeFrontPaymentRecord {

    //充值都是调用一个接口，只是参数不同
    private static String rechargeFrontPaymentRecord = "/v1/transaction/rechargeFrontPaymentRecord";

    //QQ钱包充值渠道获取
    private static String frontQQPayment = "/v1/transaction/frontQQPayment";
    //微信充值渠道获取
    private static String frontWechatPayment = "/v1/transaction/frontWechatPayment";
    //支付宝充值渠道获取
    private static String frontAlipayPayment = "/v1/transaction/frontAlipayPayment";
    //银行转账充值渠道获取
    private static String frontBankTransferPayment = "/v1/transaction/frontBankTransferPayment";
    //银联支付充值渠道获取
    private static String frontUnionpayPayment = "/v1/transaction/frontUnionpayPayment";

    //dafagame_transaction.qq_payment 表
    @Test(description = "qq钱包一般充值和第三方充值")
    public void test001() {
        String result0 = DafaRequest.get(0,frontQQPayment);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
        JSONArray rechargePay = jsonResult0.getJSONObject("data").getJSONArray("rechargeQQPay");
        if (rechargePay.size() == 0) {
            AssertUtil.assertNull(false, "没有QQ充值渠道");
        }
        for (int i = 0; i < rechargePay.size(); i++) {
            JSONObject payment = rechargePay.getJSONObject(i);
            System.out.println(payment.getString("id"));
            System.out.println(payment);
            //qqId.add(bankTransferPayment.getString("id"));
            String fixedAmountList = payment.getString("fixedAmountList");
            String payType = payment.getString("payType");
            String result;
            if ("一般".equals(payType)) {
                String body = UrlBuilder.custom()
                        .addBuilder("id", payment.getString("id"))
                        .addBuilder("amount", "100")
                        .addBuilder("paymentType", "8")
                        .addBuilder("remark", "充值QQ号:123456789")
                        .fullBody();
                result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                JSONObject jsonResult = JSONObject.fromObject(result);
                AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
            } else {
                if ("".equals(fixedAmountList) || fixedAmountList == null) { //不是固定金额
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", payment.getString("minAmount"))
                            .addBuilder("paymentType", "9")
                            //.addBuilder("remark","duke测试QQ第三方充值备注") //可以不填
                            .fullBody();
                    System.out.println(body);
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，非固定金额"+result);
                } else { //固定金额
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", fixedAmountList.split(",")[0])
                            .addBuilder("paymentType", "9")
                            //.addBuilder("remark","duke测试QQ第三方充值备注") //可以不填
                            .fullBody();
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，固定金额"+result);
                }
            }
            ThreadSleep.sleeep(5);
        }
    }

    @Test(description = "微信第三方和一般充值")
    public void test002() {
        String result0 = DafaRequest.get(0,frontWechatPayment);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
        JSONArray rechargePay = jsonResult0.getJSONObject("data").getJSONArray("rechargeWechatPay");
        if (rechargePay.size() == 0) {
            AssertUtil.assertNull(false, "没有微信充值渠道");
        }
        for (int i = 0; i < rechargePay.size(); i++) {
            JSONObject payment = rechargePay.getJSONObject(i);
            System.out.println(payment);
            //qqId.add(bankTransferPayment.getString("id"));
            String fixedAmountList = payment.getString("fixedAmountList");
            String payType = payment.getString("payType");
            String result;
            if ("一般".equals(payType)) {
                String body = UrlBuilder.custom()
                        .addBuilder("id", payment.getString("id"))
                        .addBuilder("amount", "100")
                        .addBuilder("paymentType", "4")
                        .addBuilder("remark", "充值微信号:微信")
                        .fullBody();
                result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                JSONObject jsonResult = JSONObject.fromObject(result);
                AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
            } else { //第三方
                if ("".equals(fixedAmountList) || fixedAmountList == null) {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", payment.getString("minAmount"))
                            .addBuilder("paymentType", "5")
                            //.addBuilder("remark","") //可以不填
                            .fullBody();
                    System.out.println(body);
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，非固定金额"+result);
                } else {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", fixedAmountList.split(",")[0])
                            .addBuilder("paymentType", "5")
                            //.addBuilder("remark","") //可以不填
                            .fullBody();
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，固定金额"+result);
                }
            }
            ThreadSleep.sleeep(5);
        }
    }
    @Test(description = "支付宝第三方和一般充值")
    public void test003() {
        String result0 = DafaRequest.get(0,frontAlipayPayment);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
        JSONArray rechargePay = jsonResult0.getJSONObject("data").getJSONArray("rechargeAlipay");
        if (rechargePay.size() == 0) {
            AssertUtil.assertNull(false, "没有支付宝充值渠道");
        }
        for (int i = 0; i < rechargePay.size(); i++) {
            JSONObject payment = rechargePay.getJSONObject(i);
            System.out.println(payment.getString("id"));
            System.out.println(payment);
            //qqId.add(bankTransferPayment.getString("id"));
            String fixedAmountList = payment.getString("fixedAmountList");
            String payType = payment.getString("payType");
            String result;
            if ("一般".equals(payType)) {
                String body = UrlBuilder.custom()
                        .addBuilder("id", payment.getString("id"))
                        .addBuilder("amount", "100")
                        .addBuilder("paymentType", "6")
                        .addBuilder("remark", "充值支付宝账号")
                        .fullBody();
                result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                JSONObject jsonResult = JSONObject.fromObject(result);
                AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
            } else {
                if ("".equals(fixedAmountList) || fixedAmountList == null) {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", payment.getString("minAmount"))
                            .addBuilder("paymentType", "7")
                            .fullBody();
                    System.out.println(body);
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，非固定金额"+result);
                } else {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", fixedAmountList.split(",")[0])
                            .addBuilder("paymentType", "7")
                            .fullBody();
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "第三方充值，固定金额"+result);
                }
            }
            ThreadSleep.sleeep(5);
        }
    }

    @Test(description = "银行转账")
    public void test004() {
        String result0 = DafaRequest.get(0,frontBankTransferPayment);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
        JSONArray rechargeBankTransferPay = jsonResult0.getJSONObject("data").getJSONArray("rechargeBankTransferPay");
        if (rechargeBankTransferPay.size() == 0) {
            AssertUtil.assertNull(false, "没有银行转账充值渠道");
        }
        for (int i = 0; i < rechargeBankTransferPay.size(); i++) {
            JSONObject payment = rechargeBankTransferPay.getJSONObject(i);
            //System.out.println(payment.getString("id"));
            //System.out.println(payment);
            //String fixedAmountList = payment.getString("fixedAmountList");
            //String payType = payment.getString("payType");
            String body = UrlBuilder.custom()
                    .addBuilder("id", payment.getString("id"))
                    .addBuilder("amount", "100")
                    .addBuilder("paymentType", "3")
                    .addBuilder("remark", "转账银行卡：123456789")
                    .fullBody();
            String result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
            JSONObject jsonResult = JSONObject.fromObject(result);
            AssertUtil.assertCode(jsonResult.getInt("code") == 1, "银行转账，"+result);
            ThreadSleep.sleeep(5);
        }
    }

    @Test(description = "银联支付第三方和一般")
    public void test005() {
        String result0 = DafaRequest.get(0,frontUnionpayPayment);
        JSONObject jsonResult0 = JSONObject.fromObject(result0);
        AssertUtil.assertCode(jsonResult0.getInt("code") == 1, result0);
        JSONArray rechargePay = jsonResult0.getJSONObject("data").getJSONArray("rechargeUnionPay");
        if (rechargePay.size() == 0) {
            AssertUtil.assertNull(false, "没有银联支付充值渠道");
        }
        for (int i = 0; i < rechargePay.size(); i++) {
            JSONObject payment = rechargePay.getJSONObject(i);
            System.out.println(payment.getString("id"));
            System.out.println(payment);
            //qqId.add(bankTransferPayment.getString("id"));
            String fixedAmountList = payment.getString("fixedAmountList");
            String payType = payment.getString("payType");
            String result;
            if ("一般".equals(payType)) {
                String body = UrlBuilder.custom()
                        .addBuilder("id", payment.getString("id"))
                        .addBuilder("amount", "100")
                        .addBuilder("paymentType", "10")
                        .addBuilder("remark", "银联支付账号:一般")
                        .fullBody();
                result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                JSONObject jsonResult = JSONObject.fromObject(result);
                AssertUtil.assertCode(jsonResult.getInt("code") == 1, result);
            } else {
                if ("".equals(fixedAmountList) || fixedAmountList == null) {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", payment.getString("minAmount"))
                            .addBuilder("paymentType", "11")
                            .fullBody();
                    System.out.println(body);
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "银联第三方充值，非固定金额"+result);
                } else {
                    String body = UrlBuilder.custom()
                            .addBuilder("id", payment.getString("id"))
                            .addBuilder("amount", fixedAmountList.split(",")[0])
                            .addBuilder("paymentType", "11")
                            .fullBody();
                    result = DafaRequest.post(0,rechargeFrontPaymentRecord, body);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    AssertUtil.assertCode(jsonResult.getInt("code") == 1, "银联第三方充值，固定金额"+result);
                }
            }
            ThreadSleep.sleeep(5);
        }
    }
}
