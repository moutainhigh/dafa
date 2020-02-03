package pers.dafacloud.scheduled;

import org.apache.ibatis.session.SqlSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pers.dafacloud.dao.SqlSessionFactoryUtils;
import pers.dafacloud.mapper.LotteryOpenMessageMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 定时任务写入open message表,测试提前写入奖号
 */
//@Service
public class InsertOpenLottery {
    private static SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession("pre");
    private static LotteryOpenMessageMapper lotteryOpenMessageMapper = sqlSession.getMapper(LotteryOpenMessageMapper.class);

    //@Scheduled(cron = "1 * * * * * ")
    //public void aa1407() {
    //    task(1407);
    //}
    //
    //@Scheduled(cron = "1 * * * * * ")
    //public void aa1008() {
    //    task(1008);
    //}
    //
    //@Scheduled(cron = "1 * * * * * ")
    //public void aa1304() {
    //    task(1304);
    //}
    //
    //@Scheduled(cron = "1 * * * * * ")
    //public void aa1300() {
    //    task(1300);
    //}
    //
    //@Scheduled(cron = "1 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    //public void aa1412() {
    //    task(1412);
    //}
    //
    //@Scheduled(cron = "1 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    //public void aa1009() {
    //    task(1009);
    //}
    //
    //@Scheduled(cron = "1 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    //public void aa1306() {
    //    task(1306);
    //}
    //
    //@Scheduled(cron = "1 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    //public void aa1305() {
    //    task(1305);
    //}
    //
    //@Scheduled(cron = "1 0,3,6,9,12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57 * * * * ")
    //public void aa1413() {
    //    task(1413);
    //}
    //
    //@Scheduled(cron = "1 0,3,6,9,12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57 * * * * ")
    //public void aa1010() {
    //    task(1010);
    //}
    //
    //@Scheduled(cron = "1 0,3,6,9,12,15,18,21,24,27,30,33,36,39,42,45,48,51,54,57 * * * * ")
    //public void aa1314() {
    //    task(1314);
    //}


    //@Scheduled(cron = "00 * * * * * ")
    //public void ab1418() {
    //    task(1418);
    //}
    //
    //@Scheduled(cron = "00 * * * * * ")
    //public void ab1018() {
    //    task(1018);
    //}
    //
    //@Scheduled(cron = "00 * * * * * ")
    //public void ab1312() {
    //    task(1312);
    //}
    //
    //@Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    ////@Scheduled(cron = "58 4,9,14,19,24,29,34,39,44,49,54,59 * * * * ")
    //public void ab() {
    //    task(1419);
    //}
    //
    //@Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    ////@Scheduled(cron = "58 4,9,14,19,24,29,34,39,44,49,54,59 * * * * ")
    //public void ab1() {
    //    task(1019);
    //}
    //
    //@Scheduled(cron = "0 0,5,10,15,20,25,30,35,40,45,50,55 * * * * ")
    ////@Scheduled(cron = "58 4,9,14,19,24,29,34,39,44,49,54,59 * * * * ")
    //public void ab2() {
    //    task(1313);
    //}

    /*
    1008 1分时时彩
    1009 5分时时彩
    1300 1分六合彩
    1305 5分六合彩
    1304 1分pk10
    1306 5分pk10
    1407 1分快3
    1412 5分快3

    1413 3分快3
    1010 3分时时彩
    1314 3分pk10
    */
    private void task(int code) {
        int i = 0;
        switch (code) {
            case 1407:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1407", "6,6,6", getCurrentIssue(1), currentTime());
                break;
            case 1008:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1008", "9,9,9,9,9", getCurrentIssue(1), currentTime());
                break;
            case 1304:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1304", "01,02,03,04,05,06,07,08,09,10", getCurrentIssue(1), currentTime());
                break;
            case 1300:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1300", "22,01,26,35,04,27+03", getCurrentIssue(1), currentTime());
                break;
            case 1412:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1412", "6,6,6", getCurrentIssue(5), currentTime());
                break;
            case 1009:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1009", "9,9,9,9,9", getCurrentIssue(5), currentTime());
                break;
            case 1306:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1306", "01,02,03,04,05,06,07,08,09,10", getCurrentIssue(5), currentTime());
                break;
            case 1305:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1305", "22,01,26,35,04,27+03", getCurrentIssue(5), currentTime());
                break;
            case 1413:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1413", "6,6,6", getCurrentIssue(3), currentTime());
                break;
            case 1010:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1010", "9,9,9,9,9", getCurrentIssue(3), currentTime());
                break;
            case 1314:
                i = lotteryOpenMessageMapper.InsertOpenLottery("1314", "01,02,03,04,05,06,07,08,09,10", getCurrentIssue(3), currentTime());
                break;

            //1418	14	快3	站长快3
            //1419	14	快3	站长5分快3

            //1018	10	时时彩	站长时时彩
            //1019	10	时时彩	站长5分时时彩

            //1312	13	快乐彩	站长PK10
            //1313	13	快乐彩	站长5分PK10

            //1310	13	快乐彩	站长六合彩
            //1311	13	快乐彩	站长5分六合彩
            case 1418:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1418", "6,6,6", getCurrentIssue1(1, 0), currentTime());
                break;
            case 1018:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1018", "9,9,9,9,9", getCurrentIssue1(1, 0), currentTime());
                break;
            case 1312:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1312", "01,02,03,04,05,06,07,08,09,10", getCurrentIssue1(1, 0), currentTime());
                break;

            case 1419:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1419", "6,6,6", getCurrentIssue1(5, 0), currentTime());
                break;
            case 1019:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1019", "9,9,9,9,9", getCurrentIssue1(5, 0), currentTime());
                break;
            case 1313:
                i = lotteryOpenMessageMapper.InsertTenantOpenLottery("1313", "01,02,03,04,05,06,07,08,09,10", getCurrentIssue1(5, 0), currentTime());
                break;
        }
        System.out.println(i);
    }


    private String currentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String dateTime = LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
        return dateTime;
    }

    /**
     * 入参n 表示 几分彩
     * 示例 ：1是1分彩，5是5分彩
     */
    private static String getCurrentIssue0(int n, int nn) {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        //System.out.println(String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second));

        //当前期数
        int current = (hour * 60 + minute) / n + nn;
        String currentIssue;
        if (n == 1) //1分系列是
            currentIssue = String.format("%s%02d%02d%04d", year, month, day, current);
        else //5分系列
            currentIssue = String.format("%s%02d%02d%03d", year, month, day, current);
        return currentIssue;
    }

    private static String getCurrentIssue(int n) {
        return getCurrentIssue0(n, 1);
    }

    private static String getCurrentIssue1(int n, int nn) {
        return getCurrentIssue0(n, nn);
    }

}
