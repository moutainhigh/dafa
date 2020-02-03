package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface BettingRecordInfoMapper {

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
            "where created_date = '2020-01-29'" +
            "        and is_test = 0" +
            "        and lottery_code in ('1300','1305')" +
            "        and betting_state = '2'" +
            "        and betting_number = '家禽'")
    List<Map> getBettingRecordInfoList(@Param("tableName") String table);


    @Insert({"<script>",
            "insert into dafacloud_betting.betting_orders_info_shalv007(" +
                    "tenant_code" +
                    ",user_id" +
                    ",user_name" +
                    ",record_code" +
                    ",lottery_code" +
                    ",play_detail_code" +
                    ",issue" +
                    ",betting_number" +
                    ",betting_amount" +
                    ",return_amount" +
                    ",betting_count" +
                    ",graduation_count" +
                    ",is_single" +
                    ",betting_state" +
                    ",betting_type" +
                    ",betting_point" +
                    ",betting_unit" +
                    ",betting_orders_id" +
                    ",open_num" +
                    ",source_id" +
                    ",is_test" +
                    ",created_date" +
                    ",gmt_created" +
                    ",gmt_modified) values ",
            "<foreach collection='list' item='item' index='index' separator=','>",
                    "(#{item.tenantCode}" +
                    ",#{item.userId}" +
                    ",#{item.userName}" +
                    ",#{item.recordCode}" +
                    ",#{item.lotteryCode}" +
                    ",#{item.lotteryCode}" +
                    ",#{item.issue}" +
                    ",#{item.bettingNumber}" +
                    ",#{item.bettingAmount}" +
                    ",#{item.returnAmount}" +
                    ",#{item.bettingCount}" +
                    ",#{item.graduationCount}" +
                    ",#{item.isSingle}" +
                    ",#{item.bettingState}" +
                    ",#{item.bettingType}" +
                    ",#{item.bettingPoint}" +
                    ",#{item.bettingUnit}" +
                    ",#{item.bettingOrdersId}" +
                    ",#{item.openNum}" +
                    ",#{item.sourceId}" +
                    ",#{item.isTest}" +
                    ",#{item.createdDate}" +
                    ",#{item.gmtCreated}" +
                    ",#{item.gmtModified})",
            "</foreach>",
            "</script>"})
    int insertBettingRecordInfoList(List<Map> lists);

}
