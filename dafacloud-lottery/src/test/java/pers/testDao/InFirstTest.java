package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.mapper.in_first.InFirstMapper;
import pers.dafacloud.mapper.lotteryOpenMessage.LotteryOpenMessageMapper;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 造数据，首冲报表
 */
public class InFirstTest {

    static SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("dev");
    static InFirstMapper inFirstMapper = sqlSessionTransaction.getMapper(InFirstMapper.class);
    //static List<Map> mapList = inFirstMapper.queryGameOpenMessage();

    //`date`,
    //		`source_id`,
    //		`tenant_code`,
    //		`user_id`,
    //		`grade`,
    //		`amount`,
    //		`remark`,
    //		`gmt_created`,
    //		`gmt_modified`
    public static void main(String[] args) {
        List<Map> mapList = new ArrayList<>();
        List<String> InFirstUsers = FileUtil.readFile(InFirstTest.class.getResourceAsStream("/txt/InFirstUser.txt"));
        for (int i = 90000; i < 100000 - 1; i++) {
            Map map = new HashMap();
            map.put("date", "2020-04-29");
            map.put("source_id", "1");
            map.put("tenant_code", "dafa");
            map.put("user_id", InFirstUsers.get(i));
            map.put("grade", "0");
            map.put("amount", "66");
            map.put("remark", "银行转账");
            map.put("gmt_created", "2020-04-30 16:55:00.000");
            map.put("gmt_modified", "2020-04-30 16:55:00.000");
            mapList.add(map);
        }
        int i = inFirstMapper.addInFirstList(mapList);
        System.out.println("写入：" + i);

    }
}
