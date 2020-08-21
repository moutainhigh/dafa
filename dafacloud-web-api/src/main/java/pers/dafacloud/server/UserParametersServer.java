package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.entity.User;
import pers.dafacloud.entity.UserParameters;
import pers.dafacloud.mapper.UserMapper;
import pers.dafacloud.mapper.UserParametersMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserParametersServer {

    @Resource
    private UserParametersMapper userParametersMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<UserParameters> getUserParametersList(String username) {
        return userParametersMapper.getUserParametersList(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public List<UserParameters> getUserParametersAllList() {
        return userParametersMapper.getUserParametersAllList();
    }

    @MyDataSource(DataSourceType.dev1)
    public int getUserParametersCount(String username) {
        return userParametersMapper.getUserParametersCount(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateUserParameters(UserParameters userParameters) {
        return userParametersMapper.updateUserParameters(userParameters);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addUserParameters(UserParameters userParameters) {
        return userParametersMapper.addUserParameters(userParameters);
    }
    @MyDataSource(DataSourceType.dev1)
    public int deleteUserParameters(String id) {
        return userParametersMapper.deleteUserParameters(id);
    }
    @MyDataSource(DataSourceType.dev1)
    public UserParameters getUserParametersById(String id) {
        return userParametersMapper.getUserParametersById(id);
    }

}
