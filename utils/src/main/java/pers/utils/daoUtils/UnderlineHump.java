package pers.utils.daoUtils;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnderlineHump {
    /**
     * 下划线转驼峰
     */
    public static String underlineToHump(String para) {
        StringBuilder result = new StringBuilder();
        String[] a = para.split("_");
        for (String s : a) {
            if (!para.contains("_")) {
                result.append(s);
                continue;
            }
            if (result.length() == 0) {
                result.append(s.toLowerCase());
            } else {
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;//定位
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toUpperCase();
    }

    public static void main(String[] args) {
        //测试
        ArrayList<String> list = Lists.newArrayList(
                "user_id",
                "brief",
                "create_time",
                "instagram_id",
                "device",
                "type",
                "customer_id");
        // StrUtil.toCamelCase ali工具
        //List<String> collect = list.stream().map(StrUtil::toCamelCase).collect(Collectors.toList());//简写
        //List<String> collect = list.stream().map(e -> StrUtil.toCamelCase(e)).collect(Collectors.toList());
        //collect.forEach(System.out::println); //简写
        //collect.forEach(s -> {System.out.println(s);});

        //只打印list
        //List<String> collect2 = list.stream().map(pers.utils.daoUtils.UnderlineHump::underlineToHump).collect(Collectors.toList());
        //collect2.forEach(System.out::println);

        //select
        //for (String s : list) {
        //    System.out.println(s + " " + underlineToHump(s));
        //}

        //update
        for (String s : list) {
            System.out.println("#{" + underlineToHump(s) +"}");
        }


    }
}
