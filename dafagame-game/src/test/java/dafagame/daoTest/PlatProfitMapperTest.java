package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.Dao.SqlSessionFactoryUtils;
import pers.dafagame.mapper.PlatProfitMapper;

import java.util.List;
import java.util.Map;

/**
 * 平台赢率计算公式
 */
public class PlatProfitMapperTest {

    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("dev");//proReport
        PlatProfitMapper platProfitMapperTest = sqlSessionTransaction.getMapper(PlatProfitMapper.class);
        List<Map> mapList = platProfitMapperTest.getbetUserMulti();
        List<Map> bankerList = platProfitMapperTest.getBetUserBanker();

        //List<>
        mapList.forEach(map -> {
            //System.out.println(map);
            map.forEach((k, v) -> {
                if (v.equals("103")) {
                    System.out.println(map);
                }
                if (k.equals("gameCode")) {

                }

            });
            map.get("gameCode");
        });


        for (int i = 0; i < mapList.size(); i++) {
            Map map = mapList.get(i);
            Map map0 = bankerList.get(i);

        }


    }
}
