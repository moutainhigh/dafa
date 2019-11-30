package com.dafagame.dafaUtils;

import java.util.ArrayList;
import java.util.List;

public class playCardsHandler {

    private static List<Integer> leftCards = new ArrayList<>();


    public static void test() {
        int[] poker = new int[]{4, 5, 6, 7, 22, 33, 12, 26, 31, 20, 51, 52, 57, 13, 17, 21, 25, 29, 40, 44, 32, 27};
        for (int i : poker) {
            leftCards.add(i);
        }
        //int[] playcardNums = new int[]{3, 3, 15, 14};

        //List<Integer> playcardNum = new ArrayList<>(); //准备出的牌 num
//        for (int i : playcardNums) {
//            playcardNum.add(i);
//        }
        int[] leftCardsNum = getPoker(poker); //牌的张数
        List<List<Integer>> shunzi = getShunZi(leftCardsNum);
        System.out.println(shunzi.get(0));
        List<Integer> playcard = playCards(shunzi.get(0), leftCards, leftCardsNum);//准备出的牌，剩余的牌，可以出的牌
        System.out.println("playcard:" + playcard);
        System.out.println("leftCards:" + leftCards);
        for (int i : leftCardsNum) {
            System.out.print(i + ",");
        }
        System.out.println();

//        getLianDui(poker);
//        getFeiJi(poker);
//        getDuiZI(poker);

    }

    /**
     * 出牌
     * @param playcardNum  准备出的牌 num [3, 4, 5, 6, 7, 8]
     * @param leftCards    leftCards 剩下的牌   17, 21, 25, 29
     * @param leftCardsNum leftCardsNum 剩下牌的张数，数组  2,1,3,3,2,2,0,1,1,1,1,4,0,0,1
     * @return 能出的牌号
     */
    public static List<Integer> playCards(List<Integer> playcardNum, List<Integer> leftCards, int[] leftCardsNum) {
        List<Integer> playcard = new ArrayList<>();//准备出的牌 可以出的牌
        for (int i = 0; i < playcardNum.size(); i++) {
            outer:
            for (int j = 0; j < leftCards.size(); j++) {
                if (playcardNum.get(i) == 15 && leftCards.get(j) == 57) { //大王
                    playcard.add(leftCards.remove(j));
                    leftCardsNum[14]--;
                    System.out.println("1");
                } else if (playcardNum.get(i) == 14 && leftCards.get(j) == 56) { //小王
                    playcard.add(leftCards.remove(j));
                    leftCardsNum[13]--;
                    System.out.println("2");
                } else if (leftCards.get(j) / 4 == playcardNum.get(i)) {
                    //System.out.println("3:" + leftCards.get(j) + "，" + leftCards.get(j) / 4+"，"+playcardNum.get(i));
                    playcard.add(leftCards.remove(j));
                    leftCardsNum[playcardNum.get(i) - 3]--;
                    break outer;
                }
            }
        }
        return playcard;
    }

    /**
     * 初始化 对应牌位置张数
     * */
    public static int[] getPoker(int[] s) {
        int[] a = new int[15];
        for (int i : s) {
            if (i == 57) { //大王
                a[14]++;
            } else if (i == 56) { //小王
                a[13]++;
            } else if (i / 4 == 2) { //2
                a[12]++;
            } else if (i / 4 == 1) { //A
                a[11]++;
            } else
                a[i / 4 - 3]++;
        }
        for (int i : a) {
            System.out.print(i + ",");
        }
        System.out.println();
        return a;
    }

    //挑出顺子
    public static List<List<Integer>> getShunZi(int[] leftPoker) {
        //int[] a = getPoker(poker);
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < leftPoker.length - 3; i++) {//循环3-A
            if (leftPoker[i] > 0) {
                count++;
                temp.add(i + 3);
                //System.out.println(i);
            }
            if (leftPoker[i] == 0 || i == 11 || leftPoker[i] == 4) { //i==0 顺子中断 或者 已到最后，不拆炸弹
                if (count >= 5) { //顺子数量
                    count = 0;
                    List<Integer> list = new ArrayList<>();
                    for (int j : temp) {
                        list.add(j);
                    }
                    //记录顺子
                    lists.add(list);
                    temp.clear();
                } else {
                    temp.clear();
                }
            }
        }
        return lists;
    }

    /**
     * 连对
     */
    public static void getLianDui(int[] poker) {
        int[] a = getPoker(poker);
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < a.length - 3; i++) {
            if (a[i] > 1) {
                count++;
                temp.add(i + 3);
                //System.out.println(i);
            }
            if (a[i] < 2 || i == 11) { //i==0 连对中断 或者 已到最后
                if (count > 2) { //连对数量
                    count = 0;
                    List<Integer> list = new ArrayList<>();
                    for (int j : temp) {
                        list.add(j);
                    }
                    //记录连对
                    lists.add(list);
                    temp.clear();
                } else {
                    temp.clear();
                }
            }

        }
        System.out.println(lists);
    }

    /**
     * 飞机
     */
    public static void getFeiJi(int[] poker) {
        int[] a = getPoker(poker);
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> lists = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < a.length - 3; i++) {
            if (a[i] > 2) {
                count++;
                temp.add(i + 3);
                //System.out.println(i);
            }
            if (a[i] < 3 || i == 11) { //i==0 飞机中断 或者 已到最后
                if (count > 1) { //飞机数量
                    count = 0;
                    List<Integer> list = new ArrayList<>();
                    for (int j : temp) {
                        list.add(j);
                    }
                    //记录连对
                    lists.add(list);
                    temp.clear();
                } else {
                    temp.clear();
                }
            }
        }
        System.out.println(lists);
    }

    /**
     * 对子
     */
    public static void getDuiZI(int[] poker) {
        int[] a = getPoker(poker);
        List<Integer> lists = new ArrayList<>();
        for (int i = 0; i < a.length - 3; i++) {
            if (a[i] == 2) {
                lists.add(i + 3);
            }
        }
        System.out.println(lists);
    }


    //根据牌获取最新的牌
    public static String getPoker(String a) {
        //String a = "3,3,15,4";
        String[] s = a.split(",");
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            if (Integer.parseInt(s[i]) == 14) {
                sb.append("56,");
                continue;
            } else if (Integer.parseInt(s[i]) == 15) {
                sb.append("57,");
                continue;
            }

            int temp = Integer.parseInt(s[i]);//3
            if (i != 0) {
                int pre = Integer.parseInt(s[i - 1]);//3
                if (pre == temp) { //相等
                    count++;
                    temp = temp * 4 + count; //转成12
                } else {
                    temp = temp * 4;
                    count = 0;
                }
            } else {
                temp = temp * 4;
            }
            sb.append(temp + ",");
        }
//        System.out.println(sb.toString());
        return sb.substring(0, sb.length() - 1);
    }

    //根据牌的点数，获取牌值
    public static String getPoker(String[] poker, List<Integer> list) {
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < poker.length; j++) {
            for (int i = 0; i < list.size(); i++) {
                if (poker[j].equals("15")) {
                    sb.append("57,");
                    break;
                }
                if (list.get(i) / 4 == Integer.parseInt(poker[j])) {
                    //System.out.println(list.remove(i));
                    sb.append(list.remove(i) + ",");
                    break;
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }


}
