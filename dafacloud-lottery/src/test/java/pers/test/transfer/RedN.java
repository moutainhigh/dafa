package pers.test.transfer;

import pers.utils.fileUtils.FileUtil;
import pers.utils.listUtils.ListRemoveRepeat;

import java.util.List;

public class RedN {
    public static void main(String[] args) {
        String path = "/Users/duke/Documents/dafaUsers/repar/1.txt";
        List<String> list = FileUtil.readFile(path);
        System.out.println(list.size());
        List<String> list0 = ListRemoveRepeat.removeRepeatCount(list);
        System.out.println(list0.size());
        System.out.println(list0);

    }
}
