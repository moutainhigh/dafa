package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import pers.dafacloud.entity.User;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    User findByUsername(String username);

    User findUserById(String userId);

    String findMenuRole(String roleId);

    int updateUserPassword(String userId,String password);

    int resetPassword(String userId,String password);

    List<Map> getOwnerOpt();

    List<Map> getUserList(@Param(value="username") String username);

    int addUser(User User);
}
