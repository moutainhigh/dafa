package pers.dafacloud;


import pers.dafacloud.pojo.BetGameContent;
import pers.dafacloud.runWSJavaxSX.Testws;
import pers.dafacloud.utils.common.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) {
        System.out.println(FileUtils.class.getResource(""));
        System.out.println(FileUtils.class.getResource("/"));
        System.out.println(FileUtils.class.getClassLoader().getResource("users.txt").getPath());
        System.out.println(System.getProperty("user.dir"));

        List<BetGameContent> listBetGameContent = new ArrayList<>();
        int[] i = {20,20,3};
        for (int j = 0; j < i.length; j++) {
            for (int k = 0; k < i[j]; k++) {
                BetGameContent betGameContent = new BetGameContent();
                betGameContent.setProto("700");
                betGameContent.setPos(String.valueOf(j+1));
                listBetGameContent.add(betGameContent);
            }
        }

        for (BetGameContent betGameContent:listBetGameContent)
            System.out.println(betGameContent);

    }

}
