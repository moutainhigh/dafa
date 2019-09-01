package pers.dafacloud.Dao.mapper;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.Dao.pojo.GetBetInfo;

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


}
