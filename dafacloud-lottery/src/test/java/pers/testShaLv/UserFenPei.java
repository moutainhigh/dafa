package pers.testShaLv;

import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserFenPei {

    @Test(description = "压测用户分配")
    public static void test01() {
        List<String> users005 =
                FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/005.txt");
        List<String> users006 =
                FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/006.txt");
        List<String> users007 =
                FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/007.txt");


        //写入的文件
        File file005300 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv01/005300.txt");
        File file006200 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv01/006200.txt");
        File file007100 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv01/007100.txt");

        File file005a300 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv02/005300.txt");
        File file006b200 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv02/006200.txt");
        File file007c100 = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/sv02/007100.txt");

        writeUser(file005300, users005, 300);
        writeUser(file006200, users006, 200);
        writeUser(file007100, users007, 100);

        writeUser(file005a300, users005, 300);
        writeUser(file006b200, users006, 200);
        writeUser(file007c100, users007, 100);


    }

    /**
     * 将用户分配到不同的文件
     * file:写入到文件
     * list:所有用户
     * int:写入数据条数
     */
    public static void writeUser(File file, List<String> users, int num) {
        System.out.println(users.size());
        if (users.size() < num) {
            System.out.println("用户数据不够拆分");
            return;
        }
        try {
            if (!file.exists()) {
                file.createNewFile(); //不能创建目录
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);//false 是否追加
            for (int i = 0; i < num; i++) {
                fileOutputStream.write(users.remove(i).getBytes());
                if (i != (num - 1))
                    fileOutputStream.write("\r".getBytes());
            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(users.size());
    }
}
