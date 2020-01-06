package dafagame.test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZjhShaLvRobot {
    private static String s = "{21:[0,8.122],22:[75.94,1.235],23:[91.17,0.365],24:[97.26,0],25:[99.59,0],26:[99.9,0]," +
            "31:[0,6.102],32:[58.17,2.064],33:[82.84,0.67],34:[94.47,0],35:[99.24,0],36:[99.74,0]," +
            "41:[0,4.354],42:[44.06,2.707],43:[76.14,0.894],44:[92.28,0],45:[99.03,0],46:[99.69,0]," +
            "51:[0,3.08],52:[31.37,3.265],53:[68.93,1.198],54:[89.64,0],55:[98.78,0],56:[99.54,0]}";

    public static void main(String[] args) {
        JSONObject jo = JSONObject.fromObject(s);
    }

    /**
     * 赢率
     *pokerType  123456
     * people 2345
     *pokerNum
     * */
    public static double getP(int pokerType, int people, int pokerNum) {
        JSONObject jo = JSONObject.fromObject(s);
        JSONArray ja = jo.getJSONArray(people + "" + pokerType);
        double base = ja.getDouble(0);
        double add = ja.getDouble(1);
        double p = 0;
        switch (pokerType) {
            case 1:
                p = base + add * (pokerNum - 5);
                break;
            case 2:
            case 3:
                p = base + add * (pokerNum - 2);
                break;
            case 4:
            case 5:
            case 6:
                p = base;
                break;
        }
        return p;
    }
    //PX = ( X1 * A + X2 * B + X3 * C + X4 * D +X5 * E)/N;




}
