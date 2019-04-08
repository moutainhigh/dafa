package pers.dafacloud.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
//https://blog.csdn.net/kity9420/article/details/80740466
public class SendMessageTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();
        SendMessageHander sendMessageHander = null;
        // 将需要发送邮件的邮件地址及内容组装好，放在一个集合中
        for (int i = 0; i < 10; i++) {
            sendMessageHander = new SendMessageHander("email-" + i, "content"
                    + i);
            tasks.add(sendMessageHander);
        }
        //通过多线程一次性发起邮件，并拿到返回结果集
        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);
        //callableTaskFrameWork.submitsAll(tasks);
        // 解析返回结果集
        for (Map<String, String> map : results) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }

    }
}
