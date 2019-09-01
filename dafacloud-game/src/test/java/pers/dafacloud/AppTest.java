package pers.dafacloud;


import pers.dafacloud.pojo.BetGameContent;
import pers.dafacloud.runWSJavaxSX.Testws;
import pers.dafacloud.utils.common.FileUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Unit testCookie for simple App.
 */
public class AppTest {
    public static void main(String[] args) {

        getRepairDate3();

    }

    /**
     * 获取重复数据
     * */
    public static void getRepairDate(){
        List<String> list = FileUtils.readFile("/Users/duke/Documents/github/dafa/dafacloud-game/src/testCookie/resources/a.txt");
        HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
        for(String string:list){
            if(hashMap.get(string)!=null){  //hashMap包含遍历list中的当前元素
                Integer integer=hashMap.get(string);
                hashMap.put(string,integer+1);
                System.out.println("重复数:"+string);
            }
            else{
                hashMap.put(string,1);
            }
        }
    }

    /**
     * 获取重复数据
     * */
    public static void getRepairDate2(){
        List<String> list = FileUtils.readFile("/Users/duke/Documents/github/dafa/dafacloud-game/src/testCookie/resources/b.txt");
//        System.out.println(list);
        Map<String,String> map=new TreeMap<>();
        for(String string:list){
            String[] s = string.split(":");
            //System.out.println(s[0]+","+s[1]);
            map.put(s[0],s[1]);
        }

        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }

    public static void getRepairDate3(){
        List<String> list1 = FileUtils.readFile("/Users/duke/Documents/github/dafa/dafacloud-game/src/testCookie/resources/b.txt");
        List<String> list2 = FileUtils.readFile("/Users/duke/Documents/github/dafa/dafacloud-game/src/testCookie/resources/a.txt");
        /*for(String string1:list1){
            for(String string2:list2){
                if(string1.equals(string2)){

                }
            }

        }*/
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out :: println);

        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out :: println);




    }



}
