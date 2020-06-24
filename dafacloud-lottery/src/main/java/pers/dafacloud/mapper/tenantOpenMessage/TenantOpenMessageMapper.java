package pers.dafacloud.mapper.tenantOpenMessage;

import java.util.List;
import java.util.Map;

public interface TenantOpenMessageMapper {

    List<Map> getOpenNumber(Map map);

    List<Map> getLotteryOpenNumber();

    List<Map> getGameOpenNumber();

}
