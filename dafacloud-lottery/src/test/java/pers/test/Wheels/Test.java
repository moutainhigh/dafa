package pers.test.Wheels;

import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;

public class Test {

    public static void main(String[] args) {

        String[] s = {"海棠", "cindy", "duke", "wesley"};

        HttpConfig httpConfig = HttpConfig.custom()
                .url("http://partycms.dafacloud-test.com/v1/zhongQiu/getBoBing");
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < s.length; i++) {
                String result = DafaRequest.get(httpConfig);
                System.out.println(s[i] + " - " + result);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
