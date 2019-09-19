package pers.dafacloud;

import pers.utils.fileUtils.FileUtil;

import java.util.List;

public class ChangLong {

    public static void main(String[] args) {

        List<String>  list = FileUtil.readFile(ChangLong.class.getResourceAsStream("/changlong.txt"));

        for (String s : list) {
            System.out.print(s+"=");
           String[] a =  s.split(",");
           int total = 0;
            for (String s1 : a) {
                total += Integer.parseInt(s1);
            }
            if(total<11){
                System.out.print("大,");
            }else{
                System.out.print("小,");
            }
            if(total%2==0){
                System.out.println("双");
            }else{
                System.out.println("单");
            }
        }
        //System.out.println(list);
    }



}
