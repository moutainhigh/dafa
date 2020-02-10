package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RecordBattleMapper {

    /**
     * 对战 游戏记录
     */
    @Select("select * from record_battle_duke limit 10")
    List<Map> queryRecordBattleList();

}
