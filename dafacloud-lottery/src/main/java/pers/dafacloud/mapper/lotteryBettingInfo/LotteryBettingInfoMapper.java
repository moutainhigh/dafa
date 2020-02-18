package pers.dafacloud.mapper.lotteryBettingInfo;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface LotteryBettingInfoMapper {

    @Select("select id id" +
            ",tenant_code tenantCode" +
            ",user_id userId" +
            ",user_name userName" +
            ",record_code recordCode" +
            ",lottery_code lotteryCode" +
            ",play_detail_code lotteryCode" +
            ",issue issue" +
            ",betting_number bettingNumber" +
            ",betting_amount bettingAmount" +
            ",return_amount returnAmount" +
            ",betting_count bettingCount" +
            ",graduation_count graduationCount" +
            ",is_single isSingle" +
            ",betting_state bettingState" +
            ",betting_type bettingType" +
            ",betting_point bettingPoint" +
            ",betting_unit bettingUnit" +
            ",betting_orders_id bettingOrdersId" +
            ",open_num openNum" +
            ",source_id sourceId" +
            ",is_test isTest" +
            ",created_date createdDate" +
            ",gmt_created gmtCreated" +
            ",gmt_modified gmtModified from dafacloud_betting.betting_orders_info_${tableName} " +
            "where created_date = '2020-02-13' " +
            "        and issue = 202002130202 " +
            "        and is_test = 0" +
            "        and lottery_code = '0101' ")
    List<Map> getLotteryBetingInfo(@Param("tableName") String table);

    List<Map> getLotteryBetingInfoDx(@Param("tableName") String table, @Param("date") String date, @Param("maxId") String maxId);

    int insertLotteryBetingInfo(List<Map> list);

    int getLotteryBetingInfoCount(@Param("tableName") String table);


}
