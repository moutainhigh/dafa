package pers.dafacloud.Dao.mapper.InRecord;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.Dao.model.InRecord;

import java.util.List;

public interface InRecordMapper {

   List<InRecord> getId(@Param("record") List<String> record);

}
