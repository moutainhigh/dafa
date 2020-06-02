package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.onlineQestion.OnlineQuestionMapper;
import pers.dafacloud.entity.OnlineQuestion;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class OnlineQuestionServer {

    @Resource
    private OnlineQuestionMapper onlineQuestionMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<Map> questionList(Map map) {
        return onlineQuestionMapper.getQuestion(map);
    }

    @MyDataSource(DataSourceType.dev1)
    public int questionCount(Map map) {
        return onlineQuestionMapper.getQuestionCount(map);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addQuestion(Map map) {
        return onlineQuestionMapper.addQuestion(map);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateQuestion(Map map) {
        return onlineQuestionMapper.updateQuestion(map);
    }

    @MyDataSource(DataSourceType.dev1)
    public List<OnlineQuestion> exportQuestion(Map map) {
        return onlineQuestionMapper.exportQuestion(map);
    }

    @MyDataSource(DataSourceType.dev1)
    public int deleteQuestion(int id) {
        return onlineQuestionMapper.deleteQuestion(id);
    }


}
