package pers.dafacloud.Dao.mapper.lotteryBettingInfo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LotteryBetingInfoMapper {

    List<Map> getLotteryBetingInfo(@Param("tableName") String table);

    int insertLotteryBetingInfo(List<Map> list);

    int getLotteryBetingInfoCount(@Param("tableName") String table);


}
