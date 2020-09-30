package pers.dafacloud.mapper.gameBettingInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GameBettingInfoMapper {


    List<Map> getGameBetingInfo(@Param("tableName") String table);

    int insertGameBetingInfo(List<Map> list);


    List<Map> getGameOpenNum(@Param("maxId") String maxId);

    int insertGameOpenNum(List<Map> list);

}
