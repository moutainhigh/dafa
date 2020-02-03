package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 监控测试，提前写入奖号
 */
public interface LotteryOpenMessageMapper {

    /**
     * 平台彩种
     */
    @Insert("INSERT into dafacloud_lottery.lottery_open_message(lottery_code,issue,open_number,open_time,is_open,is_manual)" +
            " VALUES(#{lotteryCode},#{issue},#{openNum},#{openTime},0,0);")
    int InsertOpenLottery(@Param("lotteryCode") String lotteryCode,
                          @Param("openNum") String openNum,
                          @Param("issue") String issue,
                          @Param("openTime") String openTime);

    /**
     * 站长彩种
     */
    @Insert("INSERT into dafacloud_lottery.tenant_open_message(tenant_code,lottery_code,issue,open_number,open_time,is_open,is_manual,created_date)" +
            " VALUES('dafa',#{lotteryCode},#{issue},#{openNum},#{openTime},0,0,'2019-12-20');")
    int InsertTenantOpenLottery(@Param("lotteryCode") String lotteryCode,
                                @Param("openNum") String openNum,
                                @Param("issue") String issue,
                                @Param("openTime") String openTime);

}
