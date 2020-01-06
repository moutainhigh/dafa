package pers.dafacloud.server;

import org.apache.ibatis.annotations.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.apiContent.ApiContentMapper;
import pers.dafacloud.model.ApiContent;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ApiContentServer {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    ApiContentMapper apiContentMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<ApiContent> apiContentList(ApiContent apiContent) {
        return apiContentMapper.queryApi(apiContent);
    }

    //@MyDataSource(DataSourceType.dev1)
    //public List<ApiContent> apiContentListBatchTest(String groupsApi, String owner) {
    //    return apiContentMapper.queryApiBatchTest(groupsApi, owner);
    //}

    @MyDataSource(DataSourceType.dev1)
    public List<ApiContent> queryApiBatchTest(String groupsApi, String owner) {
        return apiContentMapper.queryApiBatchTest(groupsApi, owner);
    }

    @MyDataSource(DataSourceType.dev1)
    public int apiContentCount(ApiContent apiContent) {
        return apiContentMapper.queryApiCount(apiContent);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateApiContent(ApiContent apiContent) {
        return apiContentMapper.updateApi(apiContent);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addApiContent(ApiContent apiContent) {
        return apiContentMapper.addApi(apiContent);
    }

    @MyDataSource(DataSourceType.dev1)
    public int cloneApi(int id) {
        return apiContentMapper.cloneApi(id);
    }

}
