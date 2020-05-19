package pers.dafacloud.mapper.aliBetContent;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AliBetContentMapper {

    List<Map> getBetContentMapper(@Param("contentType") int contentType,@Param("lotteryCode") String lotteryCode);

    int insertBetContent(List<Map> list);
}
