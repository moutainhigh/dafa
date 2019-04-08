package pers.dafacloud.LongHu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LongHuGameLogic extends AbstractGameLogic {

    private static LongHuGameLogic instance = new LongHuGameLogic();

    public static AbstractGameLogic instance() { return instance; }

    public static List<Integer> pokers;


    private LongHuGameLogic() {
        pokers = new ArrayList<>();
        for (int i = 4; i < 55; ++i){
            pokers.add(i);
        }
    }

    @Override
    public int[] check(String openNumber) {
        try{
            //龙虎和 123
            int[] pos = {0,0,0};
            String[] split = openNumber.split(",");
            int[] cards = new int[2];
            if(split.length < 2)
                throw new RuntimeException("发牌错误");
            for (int i = 0; i < split.length; i++) {

                cards[i] = Integer.parseInt(split[i]);
            }

            int dragon = getPokerValue(cards[0]);
            int tiger = getPokerValue(cards[1]);

            //System.out.println(dragon+","+tiger);

            if(dragon > tiger)
                pos[0] = 1;
            else if(dragon < tiger)
                pos[1] = 1;
            else if(dragon == tiger)
                pos[2] = 1;

            return pos;
        }catch (Exception e){
            throw new RuntimeException("牌型计算错误"+e);
           // throw new CheckException("牌型计算错误", e);
        }
        //return pos;


    }

    public int getPokerValue(int poker){
        return poker >> 2;
    }

    public static String getLotteryPokers(){
        Collections.shuffle(pokers);
        StringBuffer buffer = new StringBuffer();
        buffer.append(pokers.get(0)).append(",").append(pokers.get(1));
        return buffer.toString();
    }
}

