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
                FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/a.txt");

        List<String> users006 =
                FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/b.txt");

        File file = new File("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/test/resouces/sva/bsv005500.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, false);//false 是否追加
            for (int i = 0; i < users005.size(); i++) {
                if (i < 300) {
                    fileOutputStream.write(users005.get(i).getBytes());
                    if (i != (300-1))
                        fileOutputStream.write("\r".getBytes());
                }

            }
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
