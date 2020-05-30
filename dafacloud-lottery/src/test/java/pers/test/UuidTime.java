package pers.test;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UuidTime {
    public static void main(String[] args) {
        //for (int i = 0; i < 1800; i++) {
        //    String uuidTime = UUID.randomUUID() + "_" + System.currentTimeMillis();
        //    System.out.println(uuidTime);
        //}


        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1800; i++) {
            String uuidTime = UUID.randomUUID() + "_" + System.currentTimeMillis();
            //System.out.println(uuidTime);
            set.add(uuidTime);
        }
        System.out.println(set.size());
        //set.forEach(p -> System.out.println(p));

    }
}
