package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.entity.TestApiResult;

import java.util.List;

@Mapper
public interface TestApiResultMapper {

    int addTestApiResult(TestApiResult testApiResult);

    int deleteTestApiResult(int id);

    int deleteBatchTestApiResult(String ids);

    List<TestApiResult> searchTestApiResult(TestApiResult testApiResult);

    int searchTestApiResultCount(TestApiResult testApiResult);

}
