package pers.dafacloud.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.TestApiResultMapper;
import pers.dafacloud.entity.TestApiResult;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestApiResultServer {

    private static Logger log = LoggerFactory.getLogger(TestApiResultServer.class);

    @Resource
    TestApiResultMapper testApiResultMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<TestApiResult> searchTestApiResult(TestApiResult testApiResult){
        return  testApiResultMapper.searchTestApiResult(testApiResult);
    }

    @MyDataSource(DataSourceType.dev1)
    public int searchTestApiResultCount(TestApiResult testApiResult){
        return  testApiResultMapper.searchTestApiResultCount(testApiResult);
    }


    @MyDataSource(DataSourceType.dev1)
    public int addTestApiResult(TestApiResult testApiResult){
        return  testApiResultMapper.addTestApiResult(testApiResult);
    }

    @MyDataSource(DataSourceType.dev1)
    public int deleteTestApiResult(int id){
        return testApiResultMapper.deleteTestApiResult(id);
    }


}
