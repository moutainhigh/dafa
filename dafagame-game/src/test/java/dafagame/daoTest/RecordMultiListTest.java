package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
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
}
