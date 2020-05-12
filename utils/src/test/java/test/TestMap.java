package test;

import org.testng.ITestResult;

import javax.security.auth.Subject;
import java.util.*;
import java.util.stream.Collectors;


public class TestMap {
    public static void main(String[] args) {
        //Map<String, Integer> map = new HashMap<>();
        //map.put("b", 2);
        //map.put("a", 3);
        //map.put("c", 4);
        //map.put("d", 5);
        //System.out.println(map);
        //
        //Map<String, Integer> map2 = new LinkedHashMap<>();
        //map2.put("b", 2);
        //map2.put("a", 3);
        //map2.put("c", 4);
        //map2.put("d", 5);
        //System.out.println(map2);

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(6);
        list.add(5);
        System.out.println(list);
        list.sort((o1, o2) -> {
            if (o1 > o2)
                return 1;
            else if (o1 < o2)
                return -1;
            else
                return 0;
        });
        //System.out.println(list);



    }
}
