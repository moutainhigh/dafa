package pers.utils.listUtils;

import org.testng.annotations.Test;
import org.testng.util.Strings;
import pers.utils.fileUtils.FileUtil;

import java.util.*;

public class ListRemoveRepeat {

    public static void main(String[] args) {


    }

    @Test(description = "测试")
    public static void test01() {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("abc");
        list.add("abc");
        list.add("bcd");
        list.add("bcd");
        List<String> listnew = removeRepeat(list);
        System.out.println(listnew);
        System.out.println(removeRepeatCount(list));
    }

    @Test(description = "测试")
    public static void test02() {
        List<String> list = FileUtil.readFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/b.txt");
        List<String> listnew = removeRepeat(list);
        //System.out.println(listnew);
        for(String s : listnew){
            System.out.println(s);
        }
        System.out.println(removeRepeatCount(list));
    }

    /**
     * list去除重复数据
     */
    public static List<String> removeRepeat(List<String> list) {
        List<String> listNew = new ArrayList<>();
        Set set = new HashSet();
        for (String str : list) {
            if (set.add(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }

    /**
     * list去重并且记录重复次数
     */
    public static List<String> removeRepeatCount(List<String> list) {
        List<String> listNew = new ArrayList<>();
        Set set = new HashSet();
        for (String str : list) {
            if (set.add(str)) {
                listNew.add(str);
            }
        }
        for (int i = 0; i < listNew.size(); i++) {
            int count = Collections.frequency(list, listNew.get(i));
            listNew.set(i, listNew.get(i) + "：" + count + "次");
            //System.out.println(count);
        }
        return listNew;
    }

}
