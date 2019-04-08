package pers.dafacloud.controller;

public class HongHeipokers {



    public static void main(String[] args) {

        getHongHeipokers("4,9,11,51,34,49");

    }

    //紅黑： 豹子6 順金5 金花4 順子3  (對子>9) 2  (對子2~8) 1   單張0
    public static void getHongHeipokers(String pokers){
        String[] s = pokers.split(",");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length; i++) {
            int p = Integer.parseInt(s[i]);
            if(p%4==0){
                sb.append("方块");
            }else if(p%4==1){
                sb.append("梅花");
            }else if(p%4==2){
                sb.append("红桃");
            }else if(p%4==3){
                sb.append("黑桃");
            }
            sb.append(p>>2);
            sb.append(",");
        }
        System.out.println(sb);


    }
}
