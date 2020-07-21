package pers.dafacloud.dafaLottery;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取自营彩的 下注期号
 */
public class LotteryIssuePrivate {

    /**
     * 入参n 表示 几分彩
     * 示例 ：1是1分彩，5是5分彩
     */
    public static String getCurrentIssue(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        //System.out.println(String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second));
        //当前期数
        int current = (hour * 60 + minute) / n + 1;
        String currentIssue = "";
        if (n == 1) //1分系列是
            currentIssue = String.format("%s%02d%02d%04d", year, month, day, current);
        else //3，5分系列
            currentIssue = String.format("%s%02d%02d%03d", year, month, day, current);
        return currentIssue;
    }

    public static List<String> getChaseIssueList(int n) {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        //当前期数
        int current = (hour * 60 + minute) / n + 1;
        List<String> issueList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String currentIssue;
            if (n == 1) //1分系列是
                currentIssue = String.format("%s%02d%02d%04d", year, month, day, current + i + 1);
            else //3，5分系列
                currentIssue = String.format("%s%02d%02d%03d", year, month, day, current + i + 1);

            issueList.add(currentIssue);
        }
        return issueList;
    }

    public static void main(String[] args) {
        System.out.println(getChaseIssueList(1));
    }


}
