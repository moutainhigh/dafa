package pers.dafacloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExportBetContentMapper {

    List<Map> getBetContent(@Param("tableName") String table, @Param("date") String date, @Param("maxId") String maxId);

}
