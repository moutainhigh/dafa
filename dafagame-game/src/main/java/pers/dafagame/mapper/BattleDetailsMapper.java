package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BattleDetailsMapper {

    /**
     * 对战 游戏记录
     */
    @Select("select * from dafagame_game.battle_details where player_identity =1 limit 1000")
    List<Map> queryBattleDetails();
}
