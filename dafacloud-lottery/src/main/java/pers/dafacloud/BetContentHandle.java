package pers.dafacloud;

import org.testng.annotations.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BetContentHandle {

    @Test(description = "测试")
    public static void test01() {

    }

    /**
     * 1.找出投注内容和彩种编码一致的数据替换掉
     * 2.找出投注内容``空的数据
     * */
    public static void main(String[] args) throws Exception {
        List<String> data = new ArrayList<>();
        File file = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/svBetContent/1407.txt");
        if (!file.exists()) {
            System.out.println("readFile:找不到文件");
            return;
        }
        try {
            // 建立一个输入流对象reader
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(file), "utf-8");
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) { //可以剔除空行,但不能剔除空格的行
                /*if (!line.equals("")){
                    System.out.println(line);
                }*/
                //System.out.println(line);
                if (line != null || !"".equals(line)) {
                    String[] betContentArray = line.split("`");
                    if (betContentArray[3].equals("1418.0000")) {
                        System.out.println(line);
                    } else {
                        data.add(line);
                    }

                    if (line.contains("``")) {
                        System.out.println("-----------------"+line);
                    } else {
                        //data.add(line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/svBetContent/1407.txt", false);
        for (int i = 0; i < data.size(); i++) {
            fileOutputStream.write(data.get(i).getBytes());
            fileOutputStream.write("\r".getBytes());
        }
        fileOutputStream.close();
        //System.out.println(data);
    }
}
