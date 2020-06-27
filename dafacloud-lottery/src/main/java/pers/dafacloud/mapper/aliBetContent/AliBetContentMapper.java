package pers.dafacloud.mapper.aliBetContent;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AliBetContentMapper {

    List<Map> getBetContentMapper(@Param("contentType") int contentType, @Param("lotteryType") String lotteryType);

    int insertBetContent(List<Map> list);

    List<Map> getBetContentMapperPro(@Param("lotteryType") String lotteryType, @Param("lotteryCodeFrom") String lotteryCodeFrom);

    int getAliCount(@Param("contentType") int contentType, @Param("lotteryType") String lotteryType);
}
