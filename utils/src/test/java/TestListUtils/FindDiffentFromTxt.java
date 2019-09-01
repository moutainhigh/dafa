package TestListUtils;

import org.apache.commons.lang.StringUtils;
import org.testng.annotations.Test;
import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.TwoListDiffent;

import java.util.List;

public class FindDiffentFromTxt {

    @Test(description = "找出两文件中不同的数据")
    public static void test01() {
        List<String> a = FileUtil.readFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/comparea.txt");
        List<String> b = FileUtil.readFile("/Users/duke/Documents/github/dafa/utils/src/main/resources/compareb.txt");
        //System.out.println(TwoListDiffent.getDifferListByLoop(a,b)); //b遗漏
        //System.out.println(TwoListDiffent.getDifferListByLoop(b,a));


        System.out.println(StringUtils.join(TwoListDiffent.getDifferListByLoop(a,b),","));
        System.out.println(StringUtils.join(TwoListDiffent.getDifferListByLoop(b,a),","));
    }
}
