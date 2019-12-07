package pers.utils.other;


import java.util.ArrayList;
import java.util.List;

public class KibanaUtils {

    /**
     * 计算增加虚拟注单之后，数据矫正比例
     */
    public static void longhuXuni(List<String> list) {
        int count = 0;
        List<String> listResult = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s0 = list.get(i);
            String[] sa = s0.split("`");

            String s = sa[1];
            int pso = Integer.parseInt(s.substring(2, 3));
            String[] ss = s.split(", ");
            int xjAmount = Integer.parseInt(ss[0].substring(8));
            String[] ssa = ss[1].split(":")[1].split(",");

            int long1 = Integer.parseInt(ssa[0]);
            int hu1 = Integer.parseInt(ssa[1]);

            int long2 = long1;
            int hu2 = hu1;

            if (pso == 0) {
                long2 = long1 + xjAmount;
            } else {
                hu2 = hu1 + xjAmount;
            }
            String s10 = String.format("insert into test1(name1,name2,name3,name4,name5) values(%s,%s,%s,%s,%s)",
                    sa[0], long1, hu1, long2, hu2);
            String s11 = String.format("%s,%s,%s,%s,%s", sa[0], long1, hu1, long2, hu2);
            System.out.println(s10);
            listResult.add(s11);
            boolean a1 = long1 > hu1;
            boolean a2 = long2 > hu2;

            if (a1 != a2) {
                count++;
                //System.out.println(count);
            }
        }


        System.out.println((float) count / list.size());
    }

}
