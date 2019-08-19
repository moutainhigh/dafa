package pers.utils.fileUtils;

import net.sf.json.JSONObject;
import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    @Test(description = "读文件")
    public static void test01() {
        System.out.println(readFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/b.txt"));
    }
    @Test(description = "写文件")
    public static void test02() {
        writeFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/b.txt","cc");
    }

    /**
     * 读文件
     */
    public static List<String> readFile(String pathname) {
        List<String> data = new ArrayList<>();
        File file = new File(pathname);
        if (!file.exists()) {
            System.out.println("找不到文件");
            return null;
        }
        try {
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file));
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) { //可以剔除空行,但不能剔除空格的行
                /*if (!line.equals("")){
                    System.out.println(line);
                }*/
                //System.out.println(line);
                if(line!=null||!"".equals(line))
                    data.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 写文件
     * */
    public static void writeFile(String path,String content) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path, true);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.write("\r".getBytes());
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static JSONObject getLotteryConfig(String path) {
        StringBuffer sb = new StringBuffer();
        File file = new File(path);
        if (!file.exists())
            return null;
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in = new BufferedReader(inputStreamReader);
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
