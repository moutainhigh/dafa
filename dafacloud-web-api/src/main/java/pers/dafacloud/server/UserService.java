package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.entity.User;
import pers.dafacloud.mapper.UserMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @MyDataSource(DataSourceType.dev1)
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public User findUserById(String userId) {
        return userMapper.findUserById(userId);
    }
}
