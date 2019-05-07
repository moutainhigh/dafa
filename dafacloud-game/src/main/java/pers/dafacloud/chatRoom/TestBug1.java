package pers.dafacloud.chatRoom;

import pers.dafacloud.utils.concurrent.CallableTaskFrameWork;
import pers.dafacloud.utils.concurrent.ICallableTaskFrameWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestBug1 {

public static void main(String[] args) throws Exception{
        test();
}

    public static void test() throws Exception{
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();

        List<SendChatRoom> tasks = new ArrayList<>();

        SendChatRoom sendChatRoom =new SendChatRoom(
                "ws://www.dfcdn38.com/v1/broadCast/chat?type=1&key=VmvxQazLRdOTNb+rs5+6b3jRSk1VnFXs5J5Q2P43MoeaFBkRTaW9nu3wx8ZGGqSBPx/f6MMNRpG7jl0oGkeuOUQGlGyYHcdn6e5hdmK4WzQ&password=d41d8cd98f00b204e9800998ecf8427e&roomId=00002&api=/chat&guestCode=");

        tasks.add(sendChatRoom);

        List<Map<String, String>> results = callableTaskFrameWork.submitsAll(tasks);//多线程执行
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
