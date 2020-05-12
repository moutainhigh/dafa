package pers.dafacloud.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.ApiManageMapper;
import pers.dafacloud.model.ApiManage;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApiManageServer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    ApiManageMapper apiManageMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<ApiManage> getApiList(ApiManage apiManage) {
        return apiManageMapper.getApiList(apiManage);
    }

    @MyDataSource(DataSourceType.dev1)
    public List<ApiManage> getBatchTestApiList(String groupsApi, String owner) {
        return apiManageMapper.getBatchTestApiList(groupsApi, owner);
    }

    @MyDataSource(DataSourceType.dev1)
    public int getApiListCount(ApiManage apiManage) {
        return apiManageMapper.getApiListCount(apiManage);
    }


    @MyDataSource(DataSourceType.dev1)
    public ApiManage getApiById(int id) {
        return apiManageMapper.getApiById(id);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateApi(ApiManage apiManage) {
        return apiManageMapper.updateApi(apiManage);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addApi(ApiManage apiManage) {
        return apiManageMapper.addApi(apiManage);
    }

    @MyDataSource(DataSourceType.dev1)
    public int cloneApi(int id) {
        return apiManageMapper.cloneApi(id);
    }

}
