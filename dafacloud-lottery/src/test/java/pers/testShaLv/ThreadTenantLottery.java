package pers.testShaLv;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import pers.utils.ThreadSleep.ThreadSleep;
import pers.utils.assertUtils.AssertUtil;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.httpclientUtils.HttpHeader;
import pers.utils.jsonUtils.JsonArrayBuilder;
import pers.utils.jsonUtils.JsonObjectBuilder;
import pers.utils.urlUtils.UrlBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ThreadTenantLottery {

    private static String url = "http://52.77.207.64:8020//v1/betting/addBetting";

    /**
     * 1418 站長快3
     * 1419 站長5分快3
     * <p>
     * 1018 站長时时彩
     * 1019 站長5分时时彩
     */
    public static void main(String[] args) {

        List<String> user005 = FileUtil.readFile(
                "/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/sv005500.txt"); //
        List<String> betContents = FileUtil.readFile(
                "/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/svBetContent/1418.txt"); //
        for (int i = 0; i < 200; i++) { //用户
            int index = i;
            new Thread(() -> {
                String[] userArray = user005.get(index).split(",");
                System.out.println(user005.get(0));
                Header[] headers = HttpHeader.custom()
                        .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36")
                        .other("x-user-id", userArray[0])
                        .other("x-tenant-code", userArray[1])
                        .other("x-user-name", userArray[2])
                        .other("x-source-Id", "1")
                        //.other("Origin", "http://52.77.207.64")
                        .build();
                //投注
                for (int j = 0; j < 1000; j++) {
                    int betContentIndex = (int) (Math.random() * (betContents.size()));
                    String betContent = betContents.get(betContentIndex);
                    String[] betContentArray = betContent.split("`");

                    //long currentMillTime = System.currentTimeMillis();
                    long now = System.currentTimeMillis();
                    long lcMillTime = 0;
                    String currentDate = "";
                    try {
                        SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
                        lcMillTime = sdfOne.parse(sdfOne.format(now)).getTime();
                        Date date = new Date();
                        currentDate = sdfOne.format(date);
                        //System.out.println(currentDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    int second = (int) (now - lcMillTime) / 1000;//距离今日凌晨秒数
                    int issueOneNum = second / 60 + 1;//期数

                    JSONObject order1 = JsonObjectBuilder.custom()
                            .put("lotteryCode", betContentArray[0])
                            .put("playDetailCode", betContentArray[1])
                            .put("bettingNumber", betContentArray[2])
                            .put("bettingAmount", betContentArray[3])
                            .put("bettingCount", betContentArray[4])
                            .put("bettingPoint", "7")
                            .put("bettingIssue", String.format("%s%04d",currentDate,issueOneNum))
                            .put("graduationCount",betContentArray[5] )
                            .put("bettingUnit", betContentArray[6])
                            //Double.valueOf(betContentArray[5]).intValue()
                            .bulid();
                    JSONArray orders = JsonArrayBuilder
                            .custom()
                            .addObject(order1)
                            .bulid();
                    String bettingData = UrlBuilder.custom().addBuilder("bettingData", orders.toString()).fullBody();
                    //System.out.println(bettingData);
                    String result = DafaRequest.post(url, bettingData, headers);
                    //ThreadSleep.sleeep(1);
                    JSONObject jsonResult = JSONObject.fromObject(result);
                    if(jsonResult.getInt("code")!=1){
                        System.out.println(bettingData);
                        System.out.println(result);
                    }
                }
            }).run();
        }


    }


}
