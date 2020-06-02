package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.mapper.aliBetContent.AliBetContentMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.util.*;

public class AliBetContentMapperTest {
    private static SqlSession aliSqlSession = SqlSessionFactoryUtils.openSqlSession("ali");
    private static AliBetContentMapper aliBetContentMapper = aliSqlSession.getMapper(AliBetContentMapper.class);

    public static void main(String[] args) {
        //getBetContent(); //测试读
        insertBetContent(); //本地文件读取文件 -> 写入库
        //insertBetContentType(); //按条件 生产注单内容
    }

    public static void getBetContent() {
        System.out.println(aliBetContentMapper.getBetContentMapper(1, "1419").size());
    }

    /**
     * 随机下注
     * 本地文件读取文件 -> 写入库
     */
    public static void insertBetContent() {
        List<String> betContents = FileUtil.readFile(AliBetContentMapperTest.class.getResourceAsStream("/tenantBetContent/ab.txt"));
        List<Map> list = new ArrayList<>();
        Map map;
        for (String betContent : betContents) {
            map = new HashMap();
            map.put("content", betContent);
            map.put("contentType", 2);
            map.put("lotteryCode", "1304");
            list.add(map);
        }
        System.out.println(list.size());
        aliBetContentMapper.insertBetContent(list);
    }

    /**
     * 按条件 生产注单内容
     */
    public static void insertBetContentType() {
        //1407`1407A10`小`3.0000`1`1`1.00
        String lotteryCode = "1407";
        String playDetailCode = "1407A10";
        String bettingNumber = "小";

        List<Map> list = new ArrayList<>();
        Map map;
        for (int i = 0; i < 10; i++) {
            Map map0 = new LinkedHashMap();
            map0.put("lotteryCode", lotteryCode);
            map0.put("playDetailCode", playDetailCode);
            map0.put("bettingNumber", bettingNumber);
            map0.put("bettingAmount", i + 100);
            map0.put("bettingCount", "1");//注数
            map0.put("graduationCount", "1");//倍数
            map0.put("bettingUnit", "1.00");//单位
            StringBuilder sb = new StringBuilder();
            map0.forEach((k, v) -> sb.append(v + "`"));
            System.out.println(sb.substring(0, sb.length() - 1));
            map = new HashMap();
            map.put("content", sb.toString());
            map.put("contentType", 2);
            map.put("lotteryCode", lotteryCode);
            list.add(map);
        }
        System.out.println(list);
        //aliBetContentMapper.insertBetContent(list);


    }


}
