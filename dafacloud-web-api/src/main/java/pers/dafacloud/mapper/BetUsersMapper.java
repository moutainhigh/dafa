package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BetUsersMapper {

    List<Map> getBetUsersList(@Param("type") int type,@Param("userType") int userType, @Param("evCode") String evCode, @Param("tenantCode") String tenantCode);

    int insertBetUsers(List<Map> list);
}
