package pers.dafacloud.utils.common;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void main(String[] args) {
        //JSONObject job = getLotteryConfig("/Users/duke/Documents/github/dafa/dafacloud-testCookie/src/main/resources/lotteryConfig.json");
       // System.out.println(job.toString());
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
