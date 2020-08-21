package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.entity.UsersEnvironmentConfig;
import pers.dafacloud.mapper.UsersEnvironmentConfigMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UsersEnvironmentConfigServer {

    @Resource
    private UsersEnvironmentConfigMapper usersEnvironmentConfigMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<UsersEnvironmentConfig> getUsersEnvironmentConfigList(String username) {
        return usersEnvironmentConfigMapper.getUsersEnvironmentConfigList(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public int getUsersEnvironmentConfigListCount(String username) {
        return usersEnvironmentConfigMapper.getUsersEnvironmentConfigListCount(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public List<UsersEnvironmentConfig> getUserParametersActivitedList() {
        return usersEnvironmentConfigMapper.getUserParametersActivitedList();
    }

    @MyDataSource(DataSourceType.dev1)
    public int addUsersEnvironmentConfig(UsersEnvironmentConfig usersEnvironmentConfig) {
        return usersEnvironmentConfigMapper.addUsersEnvironmentConfig(usersEnvironmentConfig);
    }

    @MyDataSource(DataSourceType.dev1)
    public int deleteUsersEnvironmentConfig(String id) {
        return usersEnvironmentConfigMapper.deleteUsersEnvironmentConfig(id);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateUsersEnvironmentConfig(UsersEnvironmentConfig usersEnvironmentConfig) {
        return usersEnvironmentConfigMapper.updateUsersEnvironmentConfig(usersEnvironmentConfig);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateUsersEnvironmentConfigState(String id, int state) {
        return usersEnvironmentConfigMapper.updateUsersEnvironmentConfigState(id, state);
    }

    @MyDataSource(DataSourceType.dev1)
    public int resetUsersEnvironmentConfigState(String username, String evName) {
        return usersEnvironmentConfigMapper.resetUsersEnvironmentConfigState(username, evName);
    }

    @MyDataSource(DataSourceType.dev1)
    public UsersEnvironmentConfig getUsersEnvironmentConfigById(String id) {
        return usersEnvironmentConfigMapper.getUsersEnvironmentConfigById(id);
    }

}
