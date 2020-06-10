package dafagame.daoTest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;
import pers.dafagame.mapper.RecordMultiXXMapper;
import pers.dafagame.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;

public class RecordMultiListTest {

    /**
     * 多人游戏记录导出
     */
    public static void main(String[] args) {
        //SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGame");
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGameHistory");
        RecordMultiXXMapper recordMultiXXMapper = sqlSessionTransaction.getMapper(RecordMultiXXMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        RecordMultiXXMapper recordMultiXXMapper2 = sqlSessionTransaction2.getMapper(RecordMultiXXMapper.class);

        List<String> tenantCodes = FileUtil.readFile(RecordMultiListTest.class.getResourceAsStream("/txt/tenantCode.txt"));
        if (tenantCodes.size() == 0) {
            System.out.println("tenantCodes = 0");
            return;
        }

        for (int i = 0; i < tenantCodes.size(); i++) {
            String tenantCode = tenantCodes.get(i);
            List<Map> mapList = recordMultiXXMapper.getRecordMultiList(tenantCode);
            System.out.println(tenantCode + ":" + mapList.size());
            if (mapList.size() == 0) {
                continue;
            }
            //mapList.forEach(System.out::println);
            int num = recordMultiXXMapper2.addRecordMultiList(mapList);
            System.out.println(i + " - " + tenantCode + " : insert - " + num);
            mapList.clear();
        }
    }


    @Test(description = "红包扫雷跑数据")
    public static void test01() {
        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev2");
        RecordMultiXXMapper recordMultiXXMapper2 = sqlSessionTransaction2.getMapper(RecordMultiXXMapper.class);
        List<Map> mapList = recordMultiXXMapper2.getRecordMultiList("alysia");
        System.out.println(mapList.size());
        StringBuilder sb = new StringBuilder();
        for (Map map : mapList) {
            String uid = map.get("user_id").toString();
            sb.append(map.get("inning")).append(";")
                    .append(map.get("round_type")).append(";")
                    .append(map.get("return_amount")).append(";")
            //.append(map.get("user_id")).append(";")
            ;

            JSONObject gameDetail = JSONObject.parseObject(map.get("game_detail").toString());
            sb.append(gameDetail.getString("r")).append(";") //1机器人 0玩家
                    .append(gameDetail.getString("k")).append(";")//1杀率包
                    .append(gameDetail.getString("totalMoney")).append(";")
                    .append(gameDetail.getString("multiple")).append(";");
            JSONArray crabList = gameDetail.getJSONArray("crabList");
            int countReal = 0;
            for (int i = 0; i < crabList.size(); i++) {
                JSONObject crabObj = crabList.getJSONObject(i);
                if (uid.equals(crabObj.getString("uid"))) {
                    if (crabObj.getDouble("payMoney") > 0)
                        sb.append("中雷").append(";");
                    else
                        sb.append("未中雷").append(";");
                    sb.append(crabObj.getString("k")).append(";");//是否杀率
                }
                if (crabObj.getInteger("r") == 0) {
                    countReal++;
                }
            }
            sb.append(countReal).append(";");
            sb.append("\r\n");
        }
        System.out.println(sb);

    }

}
