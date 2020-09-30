package pers.dafacloud.controller;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.dafacloud.dafaLottery.Betting;
import pers.dafacloud.dafaLottery.LotteryObj;
import pers.dafacloud.entity.User;
import pers.dafacloud.server.BetContentServer;
import pers.dafacloud.server.BetUsersServer;
import pers.dafacloud.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class RunBettingLotteryController {
    private static Logger logger = LoggerFactory.getLogger(RunBettingLotteryController.class);

    @Autowired
    BetUsersServer betUsersServer;

    @Autowired
    BetContentServer betContentServer;
    //String host, String urlTenantCode, int betContentType, String[][] runLottery, int threadStepTimeMill

    @PostMapping("/runBettingLottery")
    public Response runBettingLottery(@RequestParam Map<String, Object> reqMap, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }

        if (!Betting.isStop) {
            return Response.fail("程序正在运行，请先停止");
        }

        String host = reqMap.get("host").toString();
        if (StringUtils.isEmpty(host)) {
            return Response.fail("host空");
        }

        if (!host.contains("http"))
            return Response.fail("host需包含http或者https协议");

        String urlTenantCode = reqMap.get("urlTenantCode").toString();
        if (StringUtils.isEmpty(urlTenantCode)) {
            return Response.fail("站长编码不能填空");
        }

        int betContentType = Integer.parseInt(reqMap.get("betContentType").toString());
        int threadStepTimeMill = Integer.parseInt(reqMap.get("threadStepTimeMill").toString());

        JSONArray ja;
        try {
            ja = JSONArray.parseArray(reqMap.get("runLottery").toString());
        } catch (Exception e) {
            return Response.fail("执行彩种解析失败");
        }
        if (ja == null || ja.size() == 0)
            return Response.fail("请选择运行彩种");

        List<LotteryObj> lotteryObjList = new ArrayList<>();
        for (Object o : ja) {
            JSONArray lotteryJa = JSONArray.parseArray(o.toString());
            String lotteryCode = lotteryJa.getString(1);
            int userCount;
            int bettingStepTime;
            if (StringUtils.isEmpty(lotteryCode)) {
                return Response.fail("执行彩种存在空");
            }
            try {
                userCount = lotteryJa.getInteger(2);
                bettingStepTime = lotteryJa.getInteger(3);
            } catch (Exception e) {
                return Response.fail("用户数或下注间隔解析失败");
            }

            lotteryObjList.add(new LotteryObj(lotteryCode, userCount, bettingStepTime));
        }
        Betting.isStop = false;
        logger.info(userInfo.getUsername() + "执行下注成功");
        new Thread(() -> new Betting(host, urlTenantCode, betContentType, lotteryObjList, false, threadStepTimeMill)).start();
        return Response.success("执行中");
    }

    @GetMapping("/runBettingLottery0")
    public Response runBettingLottery0(String host, String urlTenantCode, int betContentType, String[] runLottery, int threadStepTimeMill) {
        Betting.isStop = false;
        List<LotteryObj> lotteryObjList = new ArrayList<>();
        lotteryObjList.add(new LotteryObj("1407", 2, 5000));
        lotteryObjList.add(new LotteryObj("1008", 2, 5000));
        lotteryObjList.add(new LotteryObj("1304", 2, 5000));
        lotteryObjList.add(new LotteryObj("1300", 2, 5000));

        lotteryObjList.add(new LotteryObj("1413", 2, 5000));
        lotteryObjList.add(new LotteryObj("1010", 2, 5000));
        lotteryObjList.add(new LotteryObj("1314", 2, 5000));

        lotteryObjList.add(new LotteryObj("1412", 2, 5000));
        lotteryObjList.add(new LotteryObj("1009", 2, 5000));
        lotteryObjList.add(new LotteryObj("1306", 2, 5000));
        lotteryObjList.add(new LotteryObj("1305", 2, 5000));

        lotteryObjList.add(new LotteryObj("1418", 2, 5000));
        lotteryObjList.add(new LotteryObj("1419", 2, 5000));
        lotteryObjList.add(new LotteryObj("1018", 2, 5000));
        lotteryObjList.add(new LotteryObj("1019", 2, 5000));
        lotteryObjList.add(new LotteryObj("1312", 2, 5000));
        lotteryObjList.add(new LotteryObj("1313", 2, 5000));
        new Thread(() -> new Betting(host, urlTenantCode, betContentType, lotteryObjList, false, threadStepTimeMill)).start();
        return Response.success("执行中");
    }

    @GetMapping("/getData")
    public Response test(int type) {
        System.out.println(betUsersServer.getBetUsersList(type, 1, "dev1", "dafa"));
        System.out.println(betContentServer.getBetContentList(1, "1407"));
        return Response.success("ok");
    }

    @PostMapping("/stopRun")
    public Response stop(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User userInfo;
        try {
            userInfo = (User) session.getAttribute("user");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail("获取用户信息失败");
        }
        if (Betting.isStop) {
            return Response.success("没有在运行");
        } else {
            Betting.isStop = true;
            logger.info(userInfo.getUsername() + "执行停止下注成功");
            return Response.success("停止运行");
        }
    }


}
