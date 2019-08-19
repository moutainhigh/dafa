package pers.dafacloud.utils.common;

import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void main(String[] args) {
        //
        //System.out.println(readFile("D:/userCenter.txt"));
        //测试相对路径
       // System.out.println(readFile("userCenter.txt"));


    }


    public static List<String> readFile(String pathname) {
        List<String> userList = new ArrayList<>();
        //String pathname = "D:/userCenter.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径a
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        if (!filename.exists()){
            System.out.println("找不到对应文件："+pathname);
            //return null;
        }
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
