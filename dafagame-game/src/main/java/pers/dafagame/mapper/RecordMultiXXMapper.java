package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RecordMultiXXMapper {

    /**
     * 多人 游戏记录
     */
    List<Map> getRecordMultiList(@Param("tableName") String table);

    /**
     * 写入
     */
    int addRecordMultiList(List<Map> list);
}
