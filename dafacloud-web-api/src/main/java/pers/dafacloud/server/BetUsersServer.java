package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.BetUsersMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BetUsersServer {

    @Resource
    private BetUsersMapper betUsersMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<Map> getBetUsersList(int type, int userType, String evCode, String tenantCode) {
        return betUsersMapper.getBetUsersList(type, userType, evCode, tenantCode);
    }

    @MyDataSource(DataSourceType.dev1)
    public int insertBetUsers(List<Map> list) {
        return betUsersMapper.insertBetUsers(list);
    }



}
