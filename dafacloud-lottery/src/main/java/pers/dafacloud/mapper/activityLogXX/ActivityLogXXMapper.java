package pers.dafacloud.mapper.activityLogXX;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ActivityLogXXMapper {

    /**
     * 首冲报表 写入
     */
    int addActivityLogXX(List<Map> list);
}
