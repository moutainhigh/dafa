package pers.dafacloud.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestBaiduWebsocket {

    public static void main(String[] args) throws Exception{
        ICallableTaskFrameWork callableTaskFrameWork = new CallableTaskFrameWork();
        List<CallableTemplate<Map<String, String>>> tasks = new ArrayList<CallableTemplate<Map<String, String>>>();
        for (int i = 0; i < 5; i++) {
            Websockettask2 wt = new Websockettask2("ws://119.29.3.36:6700/api/","111");
            tasks.add(wt);
        }
        //通过多线程一次性发起，并拿到返回结果集
        List<Map<String, String>> results = callableTaskFrameWork
                .submitsAll(tasks);
        // 解析返回结果集
        for (Map<String, String> map : results) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "\t" + entry.getValue());
            }
        }
        System.out.println("结束");

    }

}
