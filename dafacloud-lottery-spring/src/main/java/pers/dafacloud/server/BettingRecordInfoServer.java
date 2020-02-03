package pers.dafacloud.server;

import org.springframework.stereotype.Service;
import pers.dafacloud.mapper.BettingRecordInfoMapper;
import pers.dafacloud.utils.dataSource.MyDataSource;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BettingRecordInfoServer {

    @Resource
    BettingRecordInfoMapper bettingRecordInfoMapper;

    /**
     * 获取线上投注记录
     */
    @MyDataSource("proBetting")
    public List<Map> geBettingRecordInfoList(String table) {
        return bettingRecordInfoMapper.getBettingRecordInfoList(table);
    }

    @MyDataSource("dev1")
    public int insertBettingRecordInfoList(List<Map> lists) {
        return bettingRecordInfoMapper.insertBettingRecordInfoList(lists);
    }

}
