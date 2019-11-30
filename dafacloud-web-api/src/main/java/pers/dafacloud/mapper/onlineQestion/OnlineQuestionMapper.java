package pers.dafacloud.mapper.onlineQestion;

import org.apache.ibatis.annotations.Mapper;
import pers.dafacloud.model.OnlineQuestion;

import java.util.List;
import java.util.Map;

@Mapper
public interface OnlineQuestionMapper {

    List<Map> getQuestion(Map map);

    int getQuestionCount(Map map);

    List<OnlineQuestion> exportQuestion(Map map);

    int addQuestion(Map map);

    int deleteQuestion(int id);

    int updateQuestion(Map map);

}
