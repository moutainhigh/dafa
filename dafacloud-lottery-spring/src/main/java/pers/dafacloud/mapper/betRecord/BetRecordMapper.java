package pers.dafacloud.mapper.betRecord;

import pers.dafacloud.model.GetBetInfo;

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
