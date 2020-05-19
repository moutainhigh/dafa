package pers.dafacloud.test;

public class Test1 {

    public static void main(String[] args) {
        String lotteryCode = "1009";
        //String filename = "yfk3";
        //String s = "lotteryCode`lotteryCodeA10`大`amount`1`1`1.00@lotteryCode`lotteryCodeA10`小`amount`1`1`1.00@lotteryCode`lotteryCodeA10`单`amount.0000`1`1`1.00@lotteryCode`lotteryCodeA10`双`amount.0000`1`1`1.00";
        String s = "lotteryCode`lotteryCodeK11`和大,-,-,-,-,-`amount.0000`1`1`1.00@lotteryCode`lotteryCodeK11`和小,-,-,-,-,-`amount.0000`1`1`1.00@lotteryCode`lotteryCodeK11`和单,-,-,-,-,-`amount.0000`1`1`1.00@lotteryCode`lotteryCodeK11`和双,-,-,-,-,-`amount.0000`1`1`1.00";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 600; i++) {
            if (i < 100) {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+1))).append("\n");
            }else if(i < 200) {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+10))).append("\n");
            }else if(i < 400) {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+100))).append("\n");
            }else if(i < 500) {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+1000))).append("\n");
            }else if(i < 550) {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+10000))).append("\n");
            }else  {
                sb.append(s.replaceAll("lotteryCode", lotteryCode).replaceAll("amount", String.valueOf(i+100000))).append("\n");
            }
        }
        System.out.println(sb);

    }

}
