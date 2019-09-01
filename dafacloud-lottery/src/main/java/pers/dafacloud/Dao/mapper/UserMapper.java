package pers.dafacloud.Dao.mapper;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.Dao.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * @param
     */
    List<User> getUser(@Param("tenantCode") String tenantCode, @Param("num") Integer num);
}
