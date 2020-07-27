package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.entity.User;

@Mapper
public interface UserMapper {

    User findByUsername(String username);
    User findUserById(String userId);
}
