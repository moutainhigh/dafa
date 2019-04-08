package pers.dafacloud.common;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void main(String[] args) {
        System.out.println(readfile("D:/user.txt"));
    }
    public static List<String> readfile(String pathname) {
        List<String> userList = new ArrayList<>();
        //String pathname = "D:/user.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        try {
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
            String line = null;
            while ((line=br.readLine())!= null) {
                /*if (!line.equals("")){
                    System.out.println(line);
                }*/
                //System.out.println(line);
                userList.add(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
