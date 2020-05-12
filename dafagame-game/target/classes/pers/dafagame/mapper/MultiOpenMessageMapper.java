package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

//@Mapper
public interface MultiOpenMessageMapper {

    //@Select("select id,game_code,round_type,room_number,inning,open_number,open_result,is_open,created_date,gmt_created,gmt_modified\n" +
    //        "from open_message \n" +
    //        "where created_date \n" +
    //        "BETWEEN '2020-03-01' AND '2020-03-31'\n" +
    //        "and game_code =101;")

    List<Map> getMultiOpenMessageList(@Param("maxId") String maxId);


    int addMultiOpenMessageList(List<Map> list);

}
