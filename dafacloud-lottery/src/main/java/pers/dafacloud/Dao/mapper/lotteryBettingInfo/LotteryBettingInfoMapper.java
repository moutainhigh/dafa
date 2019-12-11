package pers.dafacloud.Dao.mapper.lotteryBettingInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LotteryBettingInfoMapper {

    List<Map> getLotteryBetingInfo(@Param("tableName") String table);


    List<Map> getLotteryBetingInfoDx(@Param("tableName") String table, @Param("date") String date, @Param("maxId") String maxId);

    int insertLotteryBetingInfo(List<Map> list);

    int getLotteryBetingInfoCount(@Param("tableName") String table);


}
