package pers.testProductBug;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;
import pers.utils.StringUtils.StringBuilders;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.fileUtils.FileUtil;
import pers.utils.urlUtils.UrlBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class GetBetRecord {
    private static String getBetDataList = "http://2019.dafacloud-admin.com/v1/betting/getBetDataList";

    @Test(description = "测试")
    public static void test01() throws Exception {
        //userName=&lotteryCode=&openState=&startTime=2019-09-11%2000%3A00%3A00&endTime=2019-09-12%2000%3A00%3A00&issue=&pageNum=1&pageSize=20&
        String url = UrlBuilder.custom()
                .url(getBetDataList)
                .addBuilder("userName")
                .addBuilder("lotteryCode")
                .addBuilder("openState")
                .addBuilder("issue")
                .addBuilder("startTime", "2019-09-01 00:00:00")
                .addBuilder("endTime", "2019-09-02 00:00:00")
                .addBuilder("pageNum", "1")
                .addBuilder("pageSize", "200000")
                .fullUrl();
        System.out.println(url);

        String result = DafaRequest.get(url, "37E895FAE85C872D1FD4AD11499CC0AE");
        //System.out.println(result);
        JSONArray jsonArray = JSONObject.fromObject(result).getJSONObject("data").getJSONArray("betInfoList");
        System.out.println(JSONObject.fromObject(result).getJSONObject("data").getString("total"));
        File file = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/betRecord/0901.txt");
        if (!file.exists()) {
            System.out.println("writeFile:找不到文件" + "");
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);//true表示追加
        System.out.println(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            if (jsonArray.size() == 0) {
                return;
            }
            JSONObject content = jsonArray.getJSONObject(i);
            StringBuilder sb = new StringBuilder();
            sb.append(content.getString("userName")).append("`")
                    .append(content.getString("lotteryName")).append("`")
                    .append(content.getString("playName")).append("`")
                    .append(content.getString("issue")).append("`")
                    .append(content.getString("betNumber")).append("`")
                    .append(content.getString("openNum")).append("`")
                    .append(content.getString("betMoney")).append("`")
                    .append(content.getString("betCount")).append("`")
                    .append(content.getString("graduation")).append("`")
                    .append(content.getString("bonusOrState")).append("`")
                    .append(content.getString("playType")).append("`")
                    .append(content.getString("gmtCreated")).append("`")
                    .append(content.getString("sourceName")).append("`")
            ;
            fileOutputStream.write(sb.toString().getBytes());
            if (i != (jsonArray.size() - 1)) {
                fileOutputStream.write("\r".getBytes());
            }
        }
        fileOutputStream.close();
        //System.out.println(result);
    }

    @Test(description = "投注记录导出Excell 拼接case when")
    public static void test02() {
        StringBuilder sb =new StringBuilder();
        List<String> list =FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/betRecord/0901.txt");
        sb.append("case ");
        for (int i = 0; i < list.size(); i++) {
            String[] s= list.get(i).split(",");
            sb.append("when a.lottery_code='"+s[0]).append("' then '"+s[1] +"' ");
        }
        sb.append( "end as '彩种'");
        System.out.println(sb.toString());
    }
}
