package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.configuration.DataSourceType;
import pers.dafacloud.mapper.tableDetail.TableDetailMapper;
import pers.dafacloud.entity.TableDetail;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;

@Service

public class TableDetailServer {

    @Resource
    private TableDetailMapper tableDetailMapper;

    @MyDataSource(DataSourceType.dev1)
    public List<TableDetail> tableDetailList(TableDetail tableDetail) {
        return tableDetailMapper.queryTableDetail(tableDetail);
    }

    @MyDataSource(DataSourceType.dev1)
    public int tableDetailCount(TableDetail tableDetail) {
        return tableDetailMapper.queryTableDetailCount(tableDetail);
    }

    @MyDataSource(DataSourceType.dev1)
    public int addTableDetail(TableDetail tableDetail) {
        return tableDetailMapper.addTableDetail(tableDetail);
    }

    @MyDataSource(DataSourceType.dev1)
    public int updateTableDetail(TableDetail tableDetail) {
        return tableDetailMapper.updateTableDetail(tableDetail);
    }

    @MyDataSource(DataSourceType.dev1)
    public int cloneTableDetail(int id) {
        return tableDetailMapper.cloneTableDetail(id);
    }


}
