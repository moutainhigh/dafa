package ddz;

import org.testng.annotations.Test;
import pers.utils.dafaRequest.DafaRequest;
import pers.utils.httpclientUtils.HttpConfig;

import java.util.ArrayList;
import java.util.List;

public class CardCompare {
    private static HttpConfig httpConfig = HttpConfig.custom();
//    private static String host = "http://192.168.8.193:7161/cardCompare?";//本地环境
//    private static String host = "http://18.162.42.162:7161/cardCompare?";//test环境

    private static String host = "http://192.168.32.71:7161/cardCompare?";//c本地
    //-1B大

    public static void main(String[] args) throws Exception {

        String result = DafaRequest.get(httpConfig.url(host + "A=4,5,6,7&B=53,55"));

        System.out.println(result);
    }

    @Test
    public static void test01() {
        String result = DafaRequest.get(httpConfig.url(host + "A=4,5,6,7&B=53,55"));
        System.out.println(result);
    }

    //单张 ： 3>k A 2 小王 大王 炸弹 火箭
    @Test
    public static void test02() {
        System.out.println("单张==============================================================================");
        //单张
        cardCompare("3--4");//
        cardCompare("13--1");
        cardCompare("13--2");
        cardCompare("2--1");
        cardCompare("9--15");
        cardCompare("7--14");
        cardCompare("15--14");
        cardCompare("6,6,6,6--11");
        cardCompare("2,2,2,2--15");
        cardCompare("2--14,15");
        //异常
        cardCompare("3--4,4");
        cardCompare("13--7,7,7");
        System.out.println("对子==============================================================================");
        //对子
        cardCompare("3,3--4,4");
        cardCompare("6,6--6,6");
        cardCompare("7,7--1,1");
        cardCompare("2,2--1,1");
        cardCompare("2,2--13,13");

        cardCompare("2,2--14,15");
        cardCompare("1,1--14,15");
        cardCompare("3,3--14,15");
        cardCompare("8,8,8,8--2,2");
        cardCompare("2,2,2,2--1,1");
        cardCompare("1,1,1,1--2,2");
        cardCompare("1,1,1,1--2,2");
        //异常
        cardCompare("1,2--3,3"); //不是对子
        cardCompare("4,4,4--3,3"); //三张-对子
        cardCompare("6,6,6,3,3--3,3"); //三带对-对子
        cardCompare("6,6,6,6,9,9--3,3"); //4带单-对子
        cardCompare("6,6,6,6,9,9,11,11--3,3"); //4带对-对子
        System.out.println("顺子==============================================================================");
        //顺子
        cardCompare("3,4,5,6,7--4,5,6,7,8");//基本
        cardCompare("3,4,5,6,7--3,4,5,6,7");//相等
        cardCompare("3,4,5,6,7,8--4,5,6,7,8,9");//6张
        cardCompare("7,8,9,10,11,12--8,9,10,11,12,13");//6张，跨10
        cardCompare("9,10,11,12,13--10,11,12,13,1");//含1
        cardCompare("3,4,5,6,7,8,9,10,11,12,13,1--10,11,12,13,1");//含1
        cardCompare("6,6,6,6--10,11,12,13,1");//炸弹
        cardCompare("3,4,5,6,7,8,9,10,11,12,13--4,5,6,7,8,9,10,11,12,13,1");//3-k,4-A
        cardCompare("12,12,12,12--4,5,6,7,8,9,10,11,12,13,1");//炸弹QQQQ
        cardCompare("3,3,3,3--4,5,6,7,8,9,10,11,12,13,1");//炸弹QQQQ
        cardCompare("14,15--4,5,6,7,8,9,10,11,12,13,1");//火箭

        //异常
        cardCompare("3,4,5,7,8--5,6,7,8,9");//不是顺子
        cardCompare("1,2,3,4,5--9,10,11,12,13");//含1,2
        cardCompare("2,3,4,5,6--9,10,11,12,13");//含2
        cardCompare("9,10,11,12,13--11,12,13,1,2");//含2
        cardCompare("3,4,5,6,7,8,9,10,11,12,13,1--4,5,6,7,8,9,10,11,12,13,1");//数量不一样，含1
        cardCompare("3,4,5,6,7,8,9,10,11,12,13,1--4,5,6,7,8,9,10,11,12,13,1,2");//含2
        cardCompare("3,4,5,6,7--4,5,6,7,8,9");//数量不一样
        cardCompare("3,4,5,6,7--10,11,12,13,14");//含小王
        cardCompare("11,12,13,14,15--3,4,5,6,7");//含大小王
        System.out.println("3不带==============================================================================");
        //3不带
        cardCompare("3,3,3--5,5,5");//基本
        cardCompare("13,13,13--1,1,1");//kkk-AAA
        cardCompare("7,7,7--2,2,2");//含2
        cardCompare("2,2,2--1,1,1");//含1，2
        cardCompare("11,11,11,11--1,1,1");//炸弹
        cardCompare("3,3,3,3--2,2,2");//炸弹
        cardCompare("3,3,3,3--13,13,13");//炸弹
        cardCompare("14,15--13,13,13");//火箭
        //异常
        cardCompare("1,2,2--13,13,13");//不是3张
        cardCompare("6,6,7--2,2,2");//不是3张
        cardCompare("13,13,14--2,2,2");//含小王
        cardCompare("13,14,15--2,2,2");//含大小王
        cardCompare("2,2,2,3--3,3,3");//3带1-3不带
        cardCompare("2,2,2,3--3,3,3");//3带1-3不带
        System.out.println("3带1==============================================================================");
        //3带1
        cardCompare("3,3,3,2--5,5,5,1");//基本
        cardCompare("1,1,1,2--2,2,2,1");//炸弹
        cardCompare("3,3,3,2--2,2,2,1");//带2
        cardCompare("3,3,3,15--2,2,2,3");//带小王，带大王
        cardCompare("3,3,3,3--2,2,2,15");//炸弹
        cardCompare("1,1,1,1--2,2,2,15");//炸弹
        cardCompare("14,15--2,2,2,1");//火箭
        System.out.println("3带对==============================================================================");
        //3带对
        cardCompare("3,3,3,2,2--5,5,5,1,1");//基本
        cardCompare("1,1,1,2,2--6,6,6,2,2");//1带2
        cardCompare("3,3,3,2,2--5,5,5,1,1");//333带22
        cardCompare("2,2,2,3,3--5,5,5,1,1");//222带33
        cardCompare("3,3,3,3--2,2,2,1,1");//炸弹
        cardCompare("2,2,2,2--13,13,13,1,1");//炸弹'
        cardCompare("14,15--2,2,2,1,1");//火箭
        //异常
        cardCompare("3,3,3,1,2--5,5,5,1,1");//带两个单张
        cardCompare("3,3,3,6,7--5,5,5,1,1");//带两个单张
        cardCompare("3,3,3,14,15--5,5,5,1,1");//带对王
        cardCompare("2,2,2,14,15--5,5,5,6,6");//带对王
        cardCompare("1,1,1,14,15--5,5,5,6,6");//带对王
        cardCompare("1,1,1,14--5,5,5,6,6");//3带1-3带2
        cardCompare("1,1,1--5,5,5,6,6");//3带0-3带2
        cardCompare("1,1,1--5,5,5,6");//3带0-3带1
        System.out.println("连对==============================================================================");
        //连对
        cardCompare("3,3,4,4,5,5--4,4,5,5,6,6");//基本
        cardCompare("3,3,4,4,5,5--3,3,4,4,5,5");//相等
        cardCompare("12,12,13,13,1,1--3,3,4,4,5,5");//QKA
        cardCompare("12,12,13,13,1,1--11,11,12,12,13,13");//QKA-JQK
        cardCompare("3,3,4,4,5,5,6,6,7,7,8,8--7,7,8,8,9,9,10,10,11,11,12,12");//6连对
        cardCompare("11,11,11,11--7,7,8,8,9,9,10,10");//炸弹
        cardCompare("14,15--7,7,8,8,9,9,10,10,11,11,12,12");//火箭
        //异常
        cardCompare("5,5,6,6--11,11,12,12");//数量不够
        cardCompare("13,13,1,1,2,2--10,10,11,11,12,12");//KA2
        cardCompare("1,1,2,2,3,3--9,9,10,10,11,11,12,12");//123
        cardCompare("3,3,5,5,6,6,7,7--9,9,10,10,11,11");//不是连对
        cardCompare("3,3,5,5,6,6,7,7--9,9,10,10,11,11,12,12");//不是连对
        cardCompare("3,3,4,4,5,5,6,6--4,4,5,5,6,6");//数量不一样
        cardCompare("3,3,4,4,5,5,6,6,7,7,8,8,9,9--7,7,8,8,9,9,10,10,11,11,12,12");//数量不一样
        cardCompare("3,3,3,3,5,5--4,4,5,5,6,6");//4带2-连对
        cardCompare("12,12,13,13,14,15--9,9,10,10,11,11,12,12");//带王
        System.out.println("飞机不带==============================================================================");
        cardCompare("5,5,5,6,6,6--8,8,8,9,9,9");//不带
        cardCompare("5,5,5,6,6,6--13,13,13,1,1,1");//KA
        cardCompare("5,5,5,6,6,6,7,7,7--12,12,12,13,13,13,1,1,1");//QKA
        cardCompare("3,3,3,3--12,12,12,13,13,13,1,1,1");//炸弹
        cardCompare("14,15--12,12,12,13,13,13,1,1,1");//火箭
        //异常
        cardCompare("5,5,5,6,6,6,7,7,7--8,8,8,9,9,9");//数量不对
        cardCompare("1,1,1,2,2,2--8,8,8,9,9,9");//A2
        cardCompare("1,1,1,2,2,2--8,8,8,9,9,9");//KA2
        System.out.println("飞机带单==============================================================================");
        cardCompare("5,5,5,1,6,6,6,2--8,8,8,3,9,9,9,7");//带单
        cardCompare("5,5,5,6,6,6,3,3--8,8,8,3,9,9,9,7");//带1对
        cardCompare("5,5,5,6,6,6,3,14--8,8,8,3,9,9,9,15");//带1个王
        cardCompare("5,5,5,6,6,6,7,7,7,8,9,10--8,8,8,9,9,9,10,10,10,5,6,7");//3连带单
        cardCompare("5,5,5,6,6,6,7,7,7,1,1,10--8,8,8,9,9,9,10,10,10,11,11,7");//3连带
        cardCompare("13,13,13,1,1,1,3,4--8,8,8,9,9,9,10,11");//KKKAAA
        cardCompare("12,12,12,13,13,13,1,1,1,3,4,5--8,8,8,9,9,9,10,10,10,3,11,12");//QQQKKKAAA
        cardCompare("5,5,5,5--8,8,8,3,9,9,9,7");//炸弹
        cardCompare("14,15--8,8,8,3,9,9,9,7");//火箭
        //异常
        cardCompare("5,5,5,1,6,6,6--8,8,8,9,9,9,7");//1个带单，一个不带
        cardCompare("5,5,5,6,6,6--8,8,8,7,9,9,9,7");//a不带，b带单
        cardCompare("5,5,5,6,6,6,3,3,4--8,8,8,7,9,9,9,7");//1个带对，一个带单
        cardCompare("5,5,5,6,6,6,14,15--8,8,8,7,9,9,9,7");//带两王 ,B大？
        System.out.println("飞机带对==============================================================================");
        cardCompare("5,5,5,6,6,6,10,10,11,11--8,8,8,9,9,9,3,3,4,4");
        cardCompare("5,5,5,6,6,6,7,7,7,11,11,12,12,3,3--8,8,8,9,9,9,10,10,10,3,3,4,4,2,2");//3飞机带对
        cardCompare("5,5,5,6,6,6,7,7,7,11,11,12,12,13,13--8,8,8,9,9,9,10,10,10,3,3,4,4,2,2");//带连对
        cardCompare("6,6,6,7,7,7,1,1,2,2--8,8,8,9,9,9,3,3,4,4");//带AA22
        cardCompare("12,12,12,13,13,13,5,5,6,6--8,8,8,9,9,9,3,3,4,4");//QK
        cardCompare("13,13,13,1,1,1,5,5,6,6--8,8,8,9,9,9,3,3,4,4");//KA
        cardCompare("5,5,5,5--8,8,8,9,9,9,7,7");//炸弹
        cardCompare("14,15--8,8,8,9,9,9,7,7");//火箭
        cardCompare("3,3,3,4,4,4,5,5,5,5--8,8,8,9,9,9,7,7,6,6");//飞机带炸弹

        //异常
        cardCompare("5,5,5,6,6,6,3,3,4,4--8,8,8,9,9,9,10,10");//a带对，b带单
        cardCompare("5,5,5,6,6,6--8,8,8,9,9,9,10,10");//a不带，b带单
        cardCompare("5,5,5,6,6,6,3,3,4,4--8,8,8,9,9,9");//a带对，b不带
        cardCompare("5,5,5,6,6,6,3,4,4--8,8,8,9,9,9");//a:1个带对，1个带单
        cardCompare("5,5,5,6,6,6,3--8,8,8,9,9,9");//a:1个不带，1个带单
        cardCompare("5,5,5,6,6,6,4,4--8,8,8,9,9,9");//a:1个不带，1个带对 --即带单
        cardCompare("1,1,1,2,2,2,5,5,6,6--8,8,8,9,9,9,3,3,4,4");//A2
        cardCompare("13,13,13,1,1,1,2,2,2,4,4,5,5,6,6--7,7,7,8,8,8,9,9,9,10,10,11,11,12,12");//KA2
        cardCompare("5,5,5,6,6,6,14,15,2,2--8,8,8,9,9,9,3,3,4,4");//带双王


        System.out.println("4不带==============================================================================");
        cardCompare("11,11,11,11--3,3,3,3");//基本
        cardCompare("11,11,11,11--1,1,1,1");//1
        cardCompare("2,2,2,2--1,1,1,1");//1-2
        cardCompare("2,2,2,2--13,13,13,13");//2-K
        cardCompare("14,15--13,13,13,13");//火箭
        cardCompare("14,15--2,2,2,2");//火箭
        cardCompare("14,15--1,1,1,1");//火箭
        //连炸

        System.out.println("4不带1==============================================================================");
        cardCompare("11,11,11,11,3--2,2,2,2,3");//基本
        cardCompare("11,11,11,11,15--3,3,3,3,13");//带王

        System.out.println("4 带 2==============================================================================");
        cardCompare("11,11,11,11,1,1--2,2,2,2,3,3");//带一对
        cardCompare("13,13,13,13,1,14--3,3,3,3,2,15");//带王
        cardCompare("3,3,3,3--13,13,13,13,1,14");//炸弹
        cardCompare("2,2,2,2--11,11,11,11,6,6");//炸弹
        cardCompare("2,2,2,2--11,11,11,11,6,6");//火箭
        cardCompare("14,15--11,11,11,11,6,6");//火箭
        cardCompare("11,11,11,11,14,15--3,3,3,3,13,13");//带两王
        System.out.println("4 带 3==============================================================================");
        cardCompare("11,11,11,11,1,1,2--12,12,12,12,3,3");//带一对+1
        cardCompare("11,11,11,11,1,2,9--3,3,3,3,1,2");//带3单
        cardCompare("11,11,11,11,1,1,1--3,3,3,3,1,2");//带3单
        System.out.println("4 带 2对==============================================================================");
        cardCompare("11,11,11,11,1,1,2,2--3,3,3,3,5,5,6,6");//带4个2对
        cardCompare("11,11,11,11,1,1,2,2--3,3,3,3,1,1,11,11");//带4个2对
        cardCompare("3,3,3,3--4,4,4,4,1,1,11,11");//炸弹-
        cardCompare("14,15--3,3,3,3,1,1,11,11");//火箭-4带对
        //异常
        cardCompare("11,11,11,11,1,2,3,4--3,3,3,3,1,2");//带4个单张
        cardCompare("11,11,11,11,1,1,2,2--3,3,3,3,1,1");//4带对-4带单
        cardCompare("11,11,11,11,1,1,2,2--3,3,3,1,1");//4带对-3带对

    }

    @Test(description = "测试")
    public static void test03() {
//        cardCompare("7,7,7,7,3,3,2,2--5,5,6,6,8,8,8,8");//a压b
        cardCompare("5,5,6,6,8,8,8,8--7,7,7,7,3,3,2,2");//
        //20:02:59.778 >>> [main] >>> INFO  >>> pers.utils.logUtils.Log - GET请求地址：http://192.168.32.71:7161/cardCompare?A=28,29,30,31,8,9,12,13&B=32,33,34,35,24,25,20,21
        //===={"typeB":"SI_DAI_DUI","compareResultStr":"B大","typeA":"SI_DAI_DUI","compareResult":-1}
        //牌型2：8,8,8,8,6,6,5,5--7,7,7,7,2,2,3,3
        //20:02:59.918 >>> [main] >>> INFO  >>> pers.utils.logUtils.Log - GET请求地址：http://192.168.32.71:7161/cardCompare?A=32,33,34,35,24,25,20,21&B=28,29,30,31,8,9,12,13
        //===={"typeB":"SI_DAI_DUI","compareResultStr":"A大","typeA":"SI_DAI_DUI","compareResult":1}

    }

    //比较牌
    public static void cardCompare(String pokers){
        String[] s = pokers.split("--");
        String[] poker1 = s[0].split(",");
        String[] poker2 = s[1].split(",");
        List<Integer> list = new ArrayList<>();
        for (int i = 4; i < 58; i++) {
            list.add(i);
        }
        String poker1s= getPoker(poker1,list);
        String poker2s= getPoker(poker2,list);
        String poker = "A=" + poker1s + "&B=" + poker2s + "";
        //System.out.println("牌型1：" +  poker1s + "--" + poker2s );
        System.out.println("牌型2：" + pokers);
        String result = DafaRequest.get(httpConfig.url(host + poker));
        System.out.println("===="+result.replace("<br/>",""));
    }
    //根据牌的点数，获取牌值
    public static String  getPoker(String[] poker,List<Integer> list){
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < poker.length; j++) {
            for (int i = 0; i < list.size(); i++) {
                if(poker[j].equals("15")){
                    sb.append("57,");
                    break;
                }
                if (list.get(i) / 4 == Integer.parseInt(poker[j])) {
                    //System.out.println(list.remove(i));
                    sb.append(list.remove(i) + ",");
                    break;
                }
            }
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static void cardCompare2(String a, String b) {
        String poker = "A=" + getPoker(a) + "&B=" + getPoker(b) + "";
        System.out.println("牌型：" + poker);
        String result = DafaRequest.get(httpConfig.url(host + poker));
        System.out.println(result);
    }


    //根据牌获取最新的牌
    public static String getPoker(String a) {
        //String a = "3,3,15,4";
        String[] s = a.split(",");
        StringBuffer sb = new StringBuffer();
        int count = 0;
        for (int i = 0; i < s.length; i++) {
            if (Integer.parseInt(s[i]) == 14) {
                sb.append("56,");
                continue;
            } else if (Integer.parseInt(s[i]) == 15) {
                sb.append("57,");
                continue;
            }

            int temp = Integer.parseInt(s[i]);//3
            if (i != 0) {
                int pre = Integer.parseInt(s[i - 1]);//3
                if (pre == temp) { //相等
                    count++;
                    temp = temp * 4 + count; //转成12
                } else {
                    temp = temp * 4;
                    count = 0;
                }
            } else {
                temp = temp * 4;
            }
            sb.append(temp + ",");
        }
//        System.out.println(sb.toString());
        return sb.substring(0, sb.length() - 1);
    }


}
