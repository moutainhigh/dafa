package pers.dafacloud.dao.mapper.betRecord;

import pers.dafacloud.dao.pojo.GetBetInfo;

import java.util.List;


public interface BetRecordMapper {

    /**
     *
     * */
    List<GetBetInfo> getRecordByIssue(String issue);

}
