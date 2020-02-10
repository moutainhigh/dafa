package dafagame.daoTest;

import org.apache.ibatis.session.SqlSession;
import pers.dafagame.Dao.SqlSessionFactoryUtils;
import pers.dafagame.enums.Card;
import pers.dafagame.mapper.BattleDetailsMapper;
import pers.dafagame.mapper.RecordBattleMapper;

import java.util.*;

public class BattleDetailsMapperTest {

    /**
     * 斗地主 牌库
     */
    public static void main(String[] args) {
        SqlSession sqlSessionTransaction = SqlSessionFactoryUtils.openSqlSession("proGame");
        BattleDetailsMapper battleDetailsMapper = sqlSessionTransaction.getMapper(BattleDetailsMapper.class);
        List<Map> mapList = battleDetailsMapper.queryBattleDetails();

        List<Card> list = new ArrayList<>();
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
            //System.out.println(Card.getCard(Integer.parseInt(hands[17])).shown + "," + Card.getCard(Integer.parseInt(hands[18])).shown + "," + Card.getCard(Integer.parseInt(hands[19])).shown);

            //根据底牌找到开启杀率时从牌库取得牌
            if (Card.getCard(Integer.parseInt(hands[17])).shown == 'S' && Card.getCard(Integer.parseInt(hands[18])).shown == 'K' && Card.getCard(Integer.parseInt(hands[19])).shown == 'W') {
                System.out.println(map.get("hands").toString());
                System.out.println(map.get("inning").toString());

                for (String s : hands) {
                    list.add(Card.getCard(Integer.parseInt(s)));
                }
            }
        }
        list.sort((o1, o2) -> {
            if (o1.num > o2.num)
                return -1;
            else if (o1.num < o2.num)
                return 1;
            else return Integer.compare(o2.extNum, o1.extNum);
        });

        for (Card card : list) {
            System.out.println(card.getNum() + "\t" + card.extNum+ "\t" +card.shown);
        }

    }


}
