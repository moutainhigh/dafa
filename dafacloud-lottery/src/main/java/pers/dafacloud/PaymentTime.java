package pers.dafacloud;

import pers.utils.fileUtils.FileUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PaymentTime {


    //平均充值时间
    public static void main(String[] args) {
        List<String> list = FileUtil.readFile("/Users/duke/Documents/github/dafa/dafacloud-lottery/src/main/resources/paymentTime.txt");
        long total = 0L;
        for (String s : list) {
            String[] grm = s.split(",");
            total = total+get(grm[0],grm[1]);
        }
        System.out.println("total:"+total);
        System.out.println("avg秒:"+(total/list.size())/1000);

    }

    public static long get(String d1, String d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        long minutes = 0L;
        try {
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);
            System.out.println(d1+","+d2);
            minutes = date2.getTime() - date1.getTime();// 这样得到的差值是微秒级别
            System.out.println(minutes/10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minutes;

    }


}
