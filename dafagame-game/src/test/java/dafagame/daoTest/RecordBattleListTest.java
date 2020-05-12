package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.utils.SqlSessionFactoryUtils;
import pers.dafagame.mapper.RecordBattleMapper;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;

public class RecordBattleListTest {

    /**
     * 对战游戏记录
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGameHistory");
        RecordBattleMapper recordBattleMapper = sqlSessionTransaction.getMapper(RecordBattleMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        RecordBattleMapper recordBattleMapper2 = sqlSessionTransaction2.getMapper(RecordBattleMapper.class);

        List<String> tenantCodes = FileUtil.readFile(RecordMultiListTest.class.getResourceAsStream("/txt/tenantCode.txt"));
        if (tenantCodes.size() == 0) {
            System.out.println("tenantCodes = 0");
            return;
        }

        for (int i = 0; i < tenantCodes.size(); i++) {
            String tenantCode = tenantCodes.get(i);
            System.out.println(tenantCode + " - " + " 当前: " + i);
            List<Map> mapList = recordBattleMapper.getRecordBattleList(tenantCode);
            System.out.println(tenantCode + ":" + mapList.size());
            if (mapList.size() == 0) {
                continue;
            }
            //mapList.forEach(System.out::println);
            int num = recordBattleMapper2.addRecordBattleList(mapList);
            System.out.println(i + " - " + tenantCode + " : insert - " + num);
            mapList.clear();
        }
    }
}
