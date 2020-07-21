package pers.utils.timeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateUtils {


    public static void main(String[] args) {
        //getToday();
        //System.out.println(LocalDate.now().plusDays(-1).toString());
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00")));
    }

    //public static void getToday() {
    //    LocalDate d = LocalDate.now().toString();
    //    System.out.println(d);
    //}

}
