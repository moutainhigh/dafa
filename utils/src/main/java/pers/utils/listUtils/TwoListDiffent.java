package pers.utils.listUtils;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwoListDiffent {


    /**
     * 双循环获取listA、listB的不同元素集合 <br>
     * b遗漏的元素 <br>
     * 在b集合中找a集合的每一个元素，找不到就输出，
     *
     * @param listA
     * @param listB
     * @return list<String>
     */
    public static List<String> getDifferListByLoop(List<String> listA, List<String> listB) {
        //long begin = System.nanoTime();//纳秒
        List<String> listC = new ArrayList<String>();
        for (String str : listA) {
            if (!listB.contains(str)) {
                listC.add(str);
            }
        }
        //long end = System.nanoTime();
        //System.out.println(" take " + (end - begin) + " time ");
        return listC;
    }

    // 优化方法3，减少put次数 ，未测试
    //https://blog.csdn.net/sinat_32133675/article/details/79386072
    private static List<String> getDiffrent(List<String> list1, List<String> list2) {
        List<String> diff = new ArrayList<String>();
        long start = System.currentTimeMillis();
        Map<String, Integer> map = new HashMap<String, Integer>(list1.size() + list2.size());
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList) {
            map.put(string, 1);
        }
        for (String string : minList) {
            Integer count = map.get(string);
            if (count != null) {
                map.put(string, ++count);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diff.add(entry.getKey());
            }
        }
        System.out.println("方法4 耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        return diff;
    }

    @Test(description = "测试")
    public static void test01() {
        List<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");
        List<String> b = new ArrayList<>();
        b.add("2");
        b.add("3");
        b.add("6");
        System.out.println(getDifferListByLoop(a, b));//a有，b没有，b遗漏
        System.out.println(getDifferListByLoop(b, a));//b有，a没有，a遗漏
    }

    @Test(description = "首尾")
    public static void test02() {
        String str = " Hello word  ";
        str = str.trim();
        System.out.println(str);
    }


}
