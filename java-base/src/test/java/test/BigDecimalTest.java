package test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalTest {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(7);
        BigDecimal b = a.divide(new BigDecimal(-6), 2, RoundingMode.DOWN);
        BigDecimal c = a.divide(new BigDecimal(-6), 6, RoundingMode.UP);
        System.out.println(b);
        System.out.println(c);

    }
}
