package pers.dafacloud.mapper.user;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.model.User;

import java.util.List;

public interface UserMapper {

    /**
     * @param
     */
    List<User> getUser(@Param("tenantCode") String tenantCode, @Param("num") Integer num);
}
