package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 重置初始化春节活动站长表数据
 */

@Mapper
public interface ResetSpringActivityMapper {

    @Update("update dafacloud_activity.spring_festival_tenant set bonus=0,available_number = draw_number;")
    int updateResetBonus();

    @Update("delete FROM dafacloud_activity.spring_festival_draw_log;")
    int updateResetLog();

    @Update("update dafacloud_activity.spring_festival_prize set available_number=prize_number;")
    int updateResetAvailableNmber();








}
