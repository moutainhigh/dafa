package pers.dafacloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.dafacloud.dafaLottery.Betting;
import pers.dafacloud.dafaLottery.LotteryObj;
import pers.dafacloud.utils.Response;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class RunBettingLottery {

    @GetMapping("/runBettingLottery")
    public Response runBettingLottery(String host) {
        Betting.isStop = false;
        List<LotteryObj> lotteryObjList = new ArrayList<>();
        lotteryObjList.add(new LotteryObj("1407", "yfk3", 2, 5000));
        lotteryObjList.add(new LotteryObj("1008", "yfssc", 2, 5000));
        lotteryObjList.add(new LotteryObj("1304", "yfpk10", 2, 5000));
        lotteryObjList.add(new LotteryObj("1300", "yflhc", 2, 5000));

        lotteryObjList.add(new LotteryObj("1413", "sfks", 2, 5000));
        lotteryObjList.add(new LotteryObj("1010", "sfssc", 2, 5000));
        lotteryObjList.add(new LotteryObj("1314", "sfpk10", 2, 5000));

        lotteryObjList.add(new LotteryObj("1412", "wfk3", 2, 5000));
        lotteryObjList.add(new LotteryObj("1009", "wfssc", 2, 5000));
        lotteryObjList.add(new LotteryObj("1306", "wfpk10", 2, 5000));
        lotteryObjList.add(new LotteryObj("1305", "wflhc", 2, 5000));

        lotteryObjList.add(new LotteryObj("1418", "yfk3", 2, 5000));
        lotteryObjList.add(new LotteryObj("1419", "wfk3", 2, 5000));
        lotteryObjList.add(new LotteryObj("1018", "yfssc", 2, 5000));
        lotteryObjList.add(new LotteryObj("1019", "wfssc", 2, 5000));
        lotteryObjList.add(new LotteryObj("1312", "yfpk10", 2, 5000));
        lotteryObjList.add(new LotteryObj("1313", "wfpk10", 2, 5000));
        new Thread(()-> new Betting(host, lotteryObjList, false, 100)).start();
        return Response.success("执行中");
    }

    @GetMapping("/stop")
    public Response stop() {
        //System.out.println(Betting.isStop);
        if (Betting.isStop) {
            return Response.success("没有在运行");
        } else {
            Betting.isStop = true;
            return Response.success("停止运行");
        }
    }


}
