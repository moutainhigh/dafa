package pers.testDao;

import org.apache.ibatis.session.SqlSession;
import pers.dafacloud.utils.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.gameBettingInfo.GameBettingInfoMapper;
import pers.utils.fileUtils.FileUtil;

import java.util.List;
import java.util.Map;

public class GameBettingInfo {


    public static void main(String[] args) {
        gameBettingInfo();
    }


    /**
     * 线上棋牌下注记录，导入到数据库
     */
    public static void gameBettingInfo() {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("lotteryGame");
        GameBettingInfoMapper gameBetingInfoMapper = sqlSessionTransaction.getMapper(GameBettingInfoMapper.class);

        SqlSession sqlSessionTransaction2 = SqlSessionFactoryUtils.openSqlSession("dev");
        GameBettingInfoMapper gameBetingInfoMapper2 = sqlSessionTransaction2.getMapper(GameBettingInfoMapper.class);

        List<String> tenantCodes = FileUtil.readFile(GameBettingInfo.class.getResourceAsStream("/tenantCode.txt"));
        for (int i = 0; i < tenantCodes.size(); i++) {
            System.out.print(i);
            getInsetGameBettingInfo(gameBetingInfoMapper, gameBetingInfoMapper2, tenantCodes.get(i));
        }
    }

    /**
     * 棋牌下注记录，先查询线上数据再写入测试库
     */
    public static void getInsetGameBettingInfo(GameBettingInfoMapper gameBetingInfoMapper,
                                               GameBettingInfoMapper gameBetingInfoMapper2, String tenantCode) {
        List<Map> list = gameBetingInfoMapper.getGameBetingInfo(tenantCode);
        System.out.print("-查询数据" + list.size());
        if (list.size() == 0) {
            System.out.println(list.size() + " - " + tenantCode);
            return;
        }
        int result = gameBetingInfoMapper2.insertGameBetingInfo(list);
        System.out.println(" - 写入：" + result + "-" + tenantCode);

    }


}
