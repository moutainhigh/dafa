package pers.dafacloud.utils.Dao.mapper;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.utils.Dao.pojo.InRecord;

import java.util.List;

public interface InRecordMapper {

   List<InRecord> getId(@Param("record") List<String> record);


}
