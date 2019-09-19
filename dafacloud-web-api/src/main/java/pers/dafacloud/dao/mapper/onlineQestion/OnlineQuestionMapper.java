package pers.dafacloud.dao.mapper.onlineQestion;

import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

//@Component
public interface OnlineQuestionMapper {

    List<Map> getQuestion(Map map);

    int getQuestionCount(Map map);

    int addQuestion(Map map);

    int deleteQuestion(int id);

    int updateQuestion(Map map);

}
