package pers.dafacloud.dafaLottery;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pers.utils.fileUtils.FileUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LotteryIssuePublic {

    //private static String host = LotteryConstant.host;
    //private static String openTime = "/v1/lottery/openTime";
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static DateTimeFormatter formatterDateY = DateTimeFormatter.ofPattern("yyyy");

    private static JSONObject openTimeJson = JSONObject.fromObject(FileUtil.readFileRetrunString(LotteryIssuePublic.class.getResourceAsStream("/openTime/all.json")));

    public static String getPublicIssue(String lotteryCode) {
        LocalTime tNow = LocalTime.parse(LocalTime.now().format(formatter));
        if ("1202".equals(lotteryCode)) {//排列3
            long day = ChronoUnit.DAYS.between(LocalDate.parse("2020-06-26"), LocalDate.now()) + (tNow.isAfter(LocalTime.parse("21:00:00")) ? 128 + 1 : 128);
            return LocalDate.now().format(formatterDateY) + day;
        }
        if ("1201".equals(lotteryCode)) {//福彩3
            long day = ChronoUnit.DAYS.between(LocalDate.parse("2020-06-26"), LocalDate.now()) + (tNow.isAfter(LocalTime.parse("21:00:00")) ? 129 + 1 : 129);
            return LocalDate.now().format(formatterDateY) + day;
        }

        if (openTimeJson.get(lotteryCode) == null) {
            return null;
        }
        //JSONArray datas = JSONArray.fromObject(FileUtil.readFileRetrunString("/Users/duke/Documents/github/dafa/dafacloud-web-api/src/main/resources/a.json"));
        JSONArray datas = openTimeJson.getJSONArray(lotteryCode);

        for (int i = 0; i < datas.size(); i++) {
            JSONObject data = datas.getJSONObject(i);
            String startTime = data.getString("startTime").substring(11);
            String endTime = data.getString("endTime").substring(11);
            if (tNow.isAfter(LocalTime.parse(startTime)) && tNow.isBefore(LocalTime.parse(endTime))) {
                if ("1302".equals(lotteryCode) || "1315".equals(lotteryCode)) { //北京快乐8 北京28
                    long day = ChronoUnit.DAYS.between(LocalDate.parse("2020-06-27"), LocalDate.now()) * 179 + 1005178 + data.getInt("issue");
                    return String.format("%s", day);
                } else {
                    return LocalDate.now().format(formatterDate) + data.getString("issue");
                }
            }
        }
        return LocalDate.now().format(formatterDate) + "001";
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            System.out.println(getPublicIssue("1309"));
            System.out.println("it consumes " + (System.currentTimeMillis() - start) + "ms");
        }

    }
}
