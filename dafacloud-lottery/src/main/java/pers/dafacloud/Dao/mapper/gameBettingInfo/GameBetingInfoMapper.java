package pers.dafacloud.Dao.mapper.gameBettingInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GameBetingInfoMapper {


    List<Map> getGameBetingInfo(@Param("tableName") String table);

    int insertGameBetingInfo(List<Map> list);


}
