package pers.dafacloud.mapper.InRecord;

import org.apache.ibatis.annotations.Param;
import pers.dafacloud.entity.InRecord;

import java.util.List;

public interface InRecordMapper {

   List<InRecord> getId(@Param("record") List<String> record);

}
