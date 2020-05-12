package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BattleDetailsMapper {

    /**
     * 对战 游戏记录 详情
     */
    List<Map> getBattleDetailList(@Param("maxId") String maxId);

    /**
     * 写入
     */
    int addBattleDetailList(List<Map> list);
}
