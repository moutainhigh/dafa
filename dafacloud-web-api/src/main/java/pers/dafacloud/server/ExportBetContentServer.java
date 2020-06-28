package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.ExportBetContentMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ExportBetContentServer {

    @Resource
    ExportBetContentMapper exportBetContentMapper;

    @MyDataSource(DataSourceType.bettingSource)
    public List<Map> getBetContent(String table, String date, String maxId) {
        return exportBetContentMapper.getBetContent(table, date, maxId);
    }


}
