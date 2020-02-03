package pers.dafacloud.Dao.mapper.lotteryOpenMessage;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LotteryOpenMessageMapper {

    @Select("select issue issue,open_number openNumber from lottery_open_message where lottery_code = 1407 ORDER BY gmt_created desc limit 300;")
    //@Select("<script> SELECT a.issue issue,a.open_number openNumber,b.issue issueKill,b.open_number openNumberKill from " +
    //        "(SELECT * FROM lottery_open_message where lottery_code = 1407  ) a " +
    //        "LEFT JOIN " +
    //        "(SELECT * FROM lottery_kill_process where lottery_code = 1407 and gmt_created > '2020-01-15' ) b on  a.issue = b.issue " +
    //        "ORDER BY a.gmt_created desc </script>")
    List<Map> queryLotteryOpenMessage();
}
