package com.dafagame.config;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;


public class ZjhConfig {

    String str = "{21:[0,8.122],22:[75.94,1.235],23:[91.17,0.365],24:[97.26,0],25:[99.59,0],26:[99.9,0]," +
            "31:[0,6.102],32:[58.17,2.064],33:[82.84,0.67],34:[94.47,0],35:[99.24,0],36:[99.74,0]," +
            "41:[0,4.354],42:[44.06,2.707],43:[76.14,0.894],44:[92.28,0],45:[99.03,0],46:[99.69,0]," +
            "51:[0,3.08],52:[31.37,3.265],53:[68.93,1.198],54:[89.64,0],55:[98.78,0],56:[99.54,0]}";


    //胜率配置key 人数 + 牌型值
    //public Map<String, double[]> p = new HashMap<>();

    //胜率期望区间 key 人数
    public Map<Integer, double[]> cp = new HashMap<>();

    //String str="";

    public Map<String, double[]> p = JSON.parseObject(str, HashMap.class);


//    //key1 5种百分比区间  key2 3种期望类型
//    public static Map<Integer, Map<Integer, double[]>> newHandPro;


    public static void main(String[] args) {
        ZjhConfig zjhConfig = new ZjhConfig() ;
        System.out.println(zjhConfig.p.get(21));

    }


}
