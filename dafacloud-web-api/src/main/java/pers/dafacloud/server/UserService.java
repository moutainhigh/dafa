package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.entity.User;
import pers.dafacloud.mapper.UserMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @MyDataSource(DataSourceType.dev1)
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public String findMenuRole(String roleId) {
        return userMapper.findMenuRole(roleId);
    }

    @MyDataSource(DataSourceType.dev1)
    public User findUserById(String userId) {
        return userMapper.findUserById(userId);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateUserPassword(String userId, String password) {
        return userMapper.updateUserPassword(userId, password);
    }

    @MyDataSource(DataSourceType.dev1)
    public List<Map> getOwnerOpt() {
        return userMapper.getOwnerOpt();
    }

    @MyDataSource(DataSourceType.dev1)
    public List<Map> getUserList(String username) {
        return userMapper.getUserList(username);
    }

    @MyDataSource(DataSourceType.dev1)
    public int resetPassword(String userId, String username) {
        return userMapper.resetPassword(userId, username);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addUser(User user) {
        return userMapper.addUser(user);
    }
}
