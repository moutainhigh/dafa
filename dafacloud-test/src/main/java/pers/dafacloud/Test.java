package pers.dafacloud;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

   static String s  = System.getProperty("user.dir")+ File.separator+"reportTemplate.txt";

    public static void main(String[] args) {
       /* System.out.println(System.getProperty("user.dir"));
        System.out.println(File.separator);*/

        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run(){
                System.out.println("11");
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");//设置日期格式
                System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },0,1000);*/

        Date d = new Date(1557032880000l);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = sdf.format(d);
        System.out.println(s);





    }
}
