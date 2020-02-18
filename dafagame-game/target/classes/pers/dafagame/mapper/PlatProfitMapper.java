package pers.dafagame.mapper;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface PlatProfitMapper {

    //@Select("select game_code as gameCode,\n" +
    //        "date,sum(bet_amount) as betAmount,\n" +
    //        "sum(profit_amount) as profitAmount,\n" +
    //        "sum(bet_odds) as betOdds,\n" +
    //        "sum(service_amount) as serviceAmount \n" +
    //        "from (\n" +
    //        "    select game_code,date,sum(bet_amount) as bet_amount,sum(profit_amount) as profit_amount,sum(bet_odds) as bet_odds, 0 as service_amount \n" +
    //        "\t\tfrom bet_user_multi \n" +
    //        "\t\twhere  date between '2020-02-01' and '2020-02-28'\n" +
    //        "\t\tand tenant_code in (select tenant_code from tenant_config where is_test = 0) group by game_code \n" +
    //        "    union all  \n" +
    //        "    select game_code,date,0,0,0,sum(service_amount) as service_amount \n" +
    //        "\t\tfrom service_user_multi \n" +
    //        "\t\twhere  date between '2020-02-01' and '2020-02-28'\n" +
    //        "\t\tand tenant_code in (select tenant_code from tenant_config where is_test = 0) \n" +
    //        "\t\tgroup by game_code \n" +
    //        "\t\t) b group by game_code;")
    List<Map> getbetUserMulti();


    List<Map> getBetUserBanker();





}
