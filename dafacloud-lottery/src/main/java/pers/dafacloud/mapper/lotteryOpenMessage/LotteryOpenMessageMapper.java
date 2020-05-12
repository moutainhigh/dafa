package pers.dafacloud.mapper.lotteryOpenMessage;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LotteryOpenMessageMapper {

    //站长
    //@Select("select issue issue,open_number openNumber from tenant_open_message where tenant_code = 'wccp' and lottery_code = 1419 ORDER BY gmt_created desc limit 30000;")
    //平台
    @Select("select issue issue,open_number openNumber from lottery_open_message where lottery_code = 1407 ORDER BY gmt_created desc limit 30000;")
    //@Select("select issue issue,open_number openNumber from tenant_open_message where  tenant_code = 'htw'  and  lottery_code = 1300  ORDER BY gmt_created desc limit 30000;")
    //@Select("<script> SELECT a.issue issue,a.open_number openNumber,b.issue issueKill,b.open_number openNumberKill from " +
    //        "(SELECT * FROM lottery_open_message where lottery_code = 1300 and gmt_created > '2020-04-14' and gmt_created &lt; '2020-04-15'  ) a " +
    //        "LEFT JOIN " +
    //        "(SELECT * FROM lottery_kill_process where lottery_code = 1300 and gmt_created > '2020-04-14' and gmt_created &lt;   '2020-04-15'  ) b on  a.issue = b.issue " +
    //        "ORDER BY a.gmt_created desc </script>")
    List<Map> queryLotteryOpenMessage();


    @Select("select issue issue,open_number openNumber from game_open_message where game_code = 2005 ORDER BY gmt_created desc limit 40000;")
    List<Map> queryGameOpenMessage();
}
