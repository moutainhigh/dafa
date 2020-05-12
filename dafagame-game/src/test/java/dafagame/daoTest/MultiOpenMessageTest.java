package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.mapper.MultiOpenMessageMapper;
import pers.dafagame.mapper.RecordMultiXXMapper;
import pers.dafagame.utils.SqlSessionFactoryUtils;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;

public class MultiOpenMessageTest {

    public static void main(String[] args) {
        //SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGame");
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGameHistory");
        MultiOpenMessageMapper multiOpenMessageMapper = sqlSessionTransaction.getMapper(MultiOpenMessageMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        MultiOpenMessageMapper multiOpenMessageMapper2 = sqlSessionTransaction2.getMapper(MultiOpenMessageMapper.class);

        //List<String> tenantCodes = FileUtil.readFile(GetRecordMultiTest.class.getResourceAsStream("/txt/tenantCode.txt"));
        String maxId = "0";
        for (int i = 0; i < 10000; i++) {
            System.out.println(maxId);
            List<Map> list = multiOpenMessageMapper.getMultiOpenMessageList(maxId);
            System.out.println(" 查询数据量 " + list.size());
            if (list.size() == 0) {
                return;
            }
            if (list.size() < 10000) {
                int result = multiOpenMessageMapper2.addMultiOpenMessageList(list);
                System.out.println( "写入尾数-" + result);
                return;
            } else {
                int result = multiOpenMessageMapper2.addMultiOpenMessageList(list);
                System.out.println( "写入数据量-" + result );
            }
            maxId = list.get(list.size() - 1).get("id").toString();
            list.clear();
        }


    }
}
