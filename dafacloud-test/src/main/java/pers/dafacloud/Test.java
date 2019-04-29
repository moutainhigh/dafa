package pers.dafacloud;

import java.io.File;

public class Test {

   static String s  = System.getProperty("user.dir")+ File.separator+"template.txt";

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(File.separator);
        System.out.println(s);
        System.out.println("czxcxz");
    }
}
