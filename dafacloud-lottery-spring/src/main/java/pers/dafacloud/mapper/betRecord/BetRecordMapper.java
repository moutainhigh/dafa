package pers.dafacloud.mapper.betRecord;

import pers.dafacloud.entity.GetBetInfo;

import java.util.List;
import java.util.Map;


public interface BetRecordMapper {

    /**
     *
     * */
    List<GetBetInfo> getRecordByIssue(String issue);

    /**
     *
     * */
    List<GetBetInfo> getRecordByLotteryCode(Map map);

}
