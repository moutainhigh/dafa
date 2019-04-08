package pers.dafacloud.concurrent;

public class SendMessageService {

    public void sendMessage(String email,String content){

        System.out.println(email+"发送邮件。。。");
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}