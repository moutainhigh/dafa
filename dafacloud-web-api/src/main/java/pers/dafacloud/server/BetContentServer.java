package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.BetContentMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BetContentServer {

    @Resource
    private BetContentMapper betContentMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<String> getBetContentList(int contentType, String lotteryCode) {
        return betContentMapper.getBetContentList(contentType, lotteryCode);
    }

    @MyDataSource(DataSourceType.dev1)
    public int insertBetContent(List<Map> list) {
        return betContentMapper.insertBetContent(list);
    }

}
