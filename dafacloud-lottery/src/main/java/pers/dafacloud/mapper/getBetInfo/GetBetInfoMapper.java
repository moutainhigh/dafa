package pers.dafacloud.mapper.getBetInfo;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.entity.GetBetInfo;

import java.util.List;

public interface GetBetInfoMapper {

    /**
     * @param
     */
    List<GetBetInfo> getBetInfo();

    /**
     *
     * */
    List<GetBetInfo> getRecord(@Param("tableName")String table);

    /**
     *
     * */
    List<GetBetInfo> getRecordByIssue(String issue);


}
