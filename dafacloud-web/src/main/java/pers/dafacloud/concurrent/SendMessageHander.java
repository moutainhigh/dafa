package pers.dafacloud.concurrent;

import java.util.HashMap;
import java.util.Map;

//继承 线程池
public class SendMessageHander extends CallableTemplate<Map<String, String>> {

    private String email;
    private String content;
    public SendMessageHander(String email,String content) {
        this.email = email;
        this.content = content;
    }

    @Override
    public Map<String, String> process() {
        SendMessageService sendMessageService = new SendMessageService();
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendMessageService.sendMessage(email+"="+i, content+"="+i);
            map.put(email+"="+i, content+"="+i);
        }

        return map;
    }

}

