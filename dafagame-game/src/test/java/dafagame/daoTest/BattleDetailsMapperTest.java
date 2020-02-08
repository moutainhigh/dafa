package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.Dao.SqlSessionFactoryUtils;
import pers.dafagame.enums.Card;
import pers.dafagame.mapper.BattleDetailsMapper;
import pers.dafagame.mapper.RecordBattleMapper;

import java.util.List;
import java.util.Map;

public class BattleDetailsMapperTest {

    /**
     * 斗地主 牌库
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGame");
        BattleDetailsMapper battleDetailsMapper = sqlSessionTransaction.getMapper(BattleDetailsMapper.class);
        List<Map> mapList = battleDetailsMapper.queryBattleDetails();

        for (Map map : mapList) {
            String[] hands = map.get("hands").toString().split(",");
            //if (Integer.parseInt(hands[17]) == 57 || Integer.parseInt(hands[18]) == 57 || Integer.parseInt(hands[19]) == 57) {
            //    System.out.println(map.get("hands").toString());
            //}

            //if (Integer.parseInt(hands[17]) == 56 || Integer.parseInt(hands[18]) == 56 || Integer.parseInt(hands[19]) == 56) {
            //    System.out.println(map.get("hands").toString());
            //}
            //System.out.println(Integer.parseInt(hands[18]) == 56 ? "大" : Integer.parseInt(hands[18]) == 57 ? "小" : "1");
            //System.out.println(Integer.parseInt(hands[19]) == 56 ? "大" : Integer.parseInt(hands[19]) == 57 ? "小" : "1");
            System.out.println(Card.getCard(Integer.parseInt(hands[17])).shown + "," + Card.getCard(Integer.parseInt(hands[18])).shown + "," + Card.getCard(Integer.parseInt(hands[19])).shown);


            if(Card.getCard(Integer.parseInt(hands[17])).shown=='Q'&&Card.getCard(Integer.parseInt(hands[18])).shown=='K'&&Card.getCard(Integer.parseInt(hands[19])).shown=='w'){
                System.out.println(map.get("hands").toString());
            }


        }



    }


}
