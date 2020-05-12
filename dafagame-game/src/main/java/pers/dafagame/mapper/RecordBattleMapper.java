package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RecordBattleMapper {

    /**
     * 对战 游戏记录
     */
    //@Select("select * from record_battle_duke limit 10")
    List<Map> getRecordBattleList(@Param("tableName") String table);


    /**
     * 写入数据
     */
    int addRecordBattleList(List<Map> list);

}
