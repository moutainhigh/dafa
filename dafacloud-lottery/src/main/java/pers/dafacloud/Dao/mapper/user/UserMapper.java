package pers.dafacloud.Dao.mapper.user;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.Dao.model.User;

import java.util.List;

public interface UserMapper {

    /**
     * @param
     */
    List<User> getUser(@Param("tenantCode") String tenantCode, @Param("num") Integer num);
}
