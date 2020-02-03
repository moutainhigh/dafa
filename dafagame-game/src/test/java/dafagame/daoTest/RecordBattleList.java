package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.Dao.SqlSessionFactoryUtils;
import pers.dafagame.mapper.RecordBattleMapper;

import java.util.List;
import java.util.Map;

public class RecordBattleList {

    /**
     * 对战游戏记录
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGame");
        RecordBattleMapper gameBetingInfoMapper = sqlSessionTransaction.getMapper(RecordBattleMapper.class);
        List<Map> mapList = gameBetingInfoMapper.queryRecordBattleList();
        System.out.println(mapList);
    }
}
