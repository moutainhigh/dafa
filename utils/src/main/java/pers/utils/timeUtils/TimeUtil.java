package pers.utils.timeUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(getDiffMillSecond("2019-11-20 09:50:09.718", "2019-11-20 09:51:09.200"));
    }


    /**
     * 计算两个时间差毫秒
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return difference 时间差（毫秒）
     */
    public static long getDiffMillSecond(String startTime, String endTime) {
        long difference = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
            Date dateTime1 = sdf.parse(startTime);
            Date dateTime2 = sdf.parse(endTime);
            difference = dateTime2.getTime() - dateTime1.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return difference;
    }


    /**
     * 获取凌晨时间，入参表示加减天数
     * DAY_OF_MONTH:一个月的第几天,也就是口头所说的几号,比如值为1,就代表是1号;
     * DAY_OF_WEEK:星期几,这个值在Calendar中有对应的常量比如,星期天就用:Calendar.SUNDAY,星期一就是Calendar.MONDAY;
     * DAY_OF_WEEK_IN_MONTH:这个月的第几周,配合DAY_OF_WEEK,就知道这个月的具体那一天,外国人就喜欢 几月的第几周的星期几 这样的日期表示方式;
     * DAY_OF_YEAR:一年当中的第几天
     */
    public static String getLCTime(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, i);//加减天
        //calendar.add(Calendar.MONTH, i);//加减月
        Date time = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(time);
    }

    /**
     * 获取凌晨时间戳 13位 1564459932000
     */
    /*
    public static long getLCMillSecond() {
        long now = System.currentTimeMillis() / 1000l;
        System.out.println(now);
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond;
        return dayTime * 1000;
    }
    */

    /**
     * 获取当前系统时间戳 13位 1564459932000 ， 等价于 System.currentTimeMillis();
     */
    public static long getMillSecond(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");//12小时制
        long time = 0;
        try {
            time = simpleDateFormat.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 毫秒转日期yyyy-MM-dd HH:mm:ss
     */
    public static String millSecondToDateString(long millSecond) {
        Date date = new Date();
        date.setTime(millSecond);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }


    /**
     * 入参格式：
     * YYYY-MM-dd
     * YYYYMMDD
     * yyyyMMdd YYYY年底时候回加一年
     * MM-dd HH:mm:ss
     */
    public static String getDateFormat(String dateFormat) {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat(dateFormat);
        Date date = new Date();
        String sfm = simpleDateFormat.format(date);
        return sfm;

    }

    /**
     * 计算当前时间到结束时间的时间差秒数
     */
    public static long diffSecond(String endDates) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date date = new Date();
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = simpleDateFormat.parse(simpleDateFormat.format(date));
            endDate = simpleDateFormat.parse(endDates);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long different = endDate.getTime() - startDate.getTime();
        return different / 1000;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowDate   当前时间
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return boolean
     * @author
     */
    public static boolean isEffectiveDate(String nowDate, String startDate, String endDate) throws Exception {
        String format = "HH:mm:ss";
        Date startTime = new SimpleDateFormat(format).parse(startDate);
        Date endTime = new SimpleDateFormat(format).parse(endDate);
        Date nowTime = new SimpleDateFormat(format).parse(nowDate);

        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        long now = nowTime.getTime();
        long begin = startTime.getTime();
        long end = endTime.getTime();

        if (now > begin && now < end) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 生成一年的日历
     */
    public static List<String> year() {
        int year = 2018;
        int m = 1;// 月份计数
        List<String> list = new ArrayList<String>();
        while (m < 13) {
            int month = m;
            Calendar cal = Calendar.getInstance();// 获得当前日期对象
            cal.clear();// 清除信息
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);// 1月从0开始
            int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//每个月的天数
            for (int j = 1; j <= count; j++) {
                //list.add(year+"-"+String.format("%02d", month)+"-"+String.format("%02d", j));
                list.add(year + String.format("%02d", month) + String.format("%02d", j));
                //System.out.print(year+"-"+String.format("%02d", month)+"-"+String.format("%02d", j) + "\t");

            }
            m++;
        }
        return list;
    }

    /**
     * 网上拷贝 生成一年的日历
     */
    public static void yearRiLi() {
        int year = 2018;
        int m = 1;// 月份计数
        while (m < 13) {
            int month = m;
            Calendar cal = Calendar.getInstance();// 获得当前日期对象
            cal.clear();// 清除信息
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);// 1月从0开始
            int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int week = cal.get(Calendar.DAY_OF_WEEK);
            System.out.printf("\t\t\t%d年%d月\n\n", year, month);
            System.out.print("日\t一\t二\t三\t四\t五\t六\n");
            int i;
            for (i = 0; i < week - 1; i++) {
                System.out.print("\t");
            }
            for (int j = 1; j <= count; j++) {
                System.out.print(j + "\t");
                if ((i + j) % 7 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            m++;
        }
    }

    public static void getCurrent() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);//加1月

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);//
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        System.out.println(String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second));
    }

}
