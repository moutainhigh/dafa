package pers.dafagame.utils.common;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void main(String[] args) {
        JSONObject job = getLotteryConfig("/Users/duke/Documents/github/dafa/dafagame-testCookie/src/main/resources/lotteryConfig.json");
        System.out.println(job.toString());
    }
    public static List<String> readfile(String pathname) {
        List<String> userList = new ArrayList<>();
        File file = new File(pathname);
        if (!file.exists())
            return null;
        try {
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
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

    public static  JSONObject  getLotteryConfig(String path){
        StringBuffer sb = new StringBuffer();
        File file = new File(path);
        if (!file.exists())
           return null;

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSONObject.fromObject(sb.toString());
    }

}
