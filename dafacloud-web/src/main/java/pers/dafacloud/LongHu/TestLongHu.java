package pers.dafacloud.LongHu;


import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class TestLongHu {


    @Test
    public void test02(){
        String s="4,11";
        AbstractGameLogic lh = LongHuGameLogic.instance();
        int[] result = lh.check(s);
        for (int k = 0; k < result.length; k++) {
            System.out.print(result[k]);
        }

    }


    @Test
    public void test01(){
        List<String> list = new ArrayList<>();
        AbstractGameLogic lh = LongHuGameLogic.instance();
        for (int i = 4; i <=55; i++) {
            for (int j = 4; j <= 55; j++) {
                if (i != j) {
                    String s = i + "," + j;
                    System.out.print(s+"\t||");
                    //list.add(s);
                    int[] result = lh.check(s);
                    /*for (int k = 0; k < result.length; k++) {
                        System.out.print(result[k]);
                    }*/

                    for (int k = 0; k < result.length; k++) {
                        System.out.print(result[k]);
                        if (k==0&&result[k] == 1) {
                            System.out.print("龙");
                        }
                        if (k==1&&result[k] == 1) {
                            System.out.print("虎");
                        }
                        if (k==2&&result[k] == 1) {
                            System.out.print("和");
                        }
                        System.out.print(",");
                    }

                    System.out.print("\t");
                    int one = i >> 2;
                    int two = j >> 2;
                    System.out.print(one + "," + two);
                    if (one > two) {
                        System.out.print("龙");
                    }
                    if (one < two) {
                        System.out.print("虎");
                    }
                    if (one == two) {
                        System.out.print("和");
                    }
                }


                System.out.println();
            }
        }

    }
}
