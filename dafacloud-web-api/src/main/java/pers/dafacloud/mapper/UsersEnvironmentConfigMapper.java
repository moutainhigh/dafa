package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.entity.UsersEnvironmentConfig;

import java.util.List;

@Mapper
public interface UsersEnvironmentConfigMapper {

    List<UsersEnvironmentConfig> getUsersEnvironmentConfigList(String username);

    int getUsersEnvironmentConfigListCount(String username);

    List<UsersEnvironmentConfig> getUserParametersActivitedList();

    int addUsersEnvironmentConfig(UsersEnvironmentConfig usersEnvironmentConfig);

    int deleteUsersEnvironmentConfig(String id);

    int updateUsersEnvironmentConfig(UsersEnvironmentConfig usersEnvironmentConfig);

    int updateUsersEnvironmentConfigState(String id, int evState);

    int resetUsersEnvironmentConfigState(String username, String evName);

    UsersEnvironmentConfig getUsersEnvironmentConfigById(String id);

}
