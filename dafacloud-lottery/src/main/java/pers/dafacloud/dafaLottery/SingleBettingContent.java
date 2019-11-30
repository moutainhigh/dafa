package pers.dafacloud.dafaLottery;

public class SingleBettingContent {


    public static void ssc() {
        String[] codes = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < codes.length; i++) {
            for (int j = 0; j < codes.length; j++) {
                for (int k = 0; k < codes.length; k++) {
                    for (int l = 0; l < codes.length; l++) {
                        //sb.append();
                        System.out.println(codes[i] +codes[j] +codes[k] +codes[l]);
                    }
                }
            }
        }

    }

    public static void main(String[] args) {
        //System.out.println(ssc());
        ssc();
    }
}
