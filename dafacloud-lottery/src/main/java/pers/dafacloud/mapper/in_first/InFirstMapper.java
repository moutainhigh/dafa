package pers.dafacloud.mapper.in_first;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface InFirstMapper {

    /**
     * 首冲报表 写入
     */
    int addInFirstList(List<Map> list);
}
