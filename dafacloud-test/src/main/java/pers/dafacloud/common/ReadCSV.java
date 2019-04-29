package pers.dafacloud.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import pers.dafacloud.pojo.BetContent;

public class ReadCSV {
    public static void main(String[] args) throws Exception {
        readCSV();
    }

    public static  List<BetContent> readCSV() {
        String srcPath = "/Users/duke/Documents/test/betcontent1.csv";
        String charset = "GBK";
        List<BetContent> contentList = new ArrayList();
        try (CSVReader csvReader = new CSVReaderBuilder(new BufferedReader(new InputStreamReader(new FileInputStream(new File(srcPath)), charset))).build()) {
            Iterator<String[]> iterator = csvReader.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                BetContent betContent = new BetContent();
                //Arrays.stream(iterator.next()).forEach(e ->content.add(e));
                //Arrays.stream(iterator.next()).forEach(e -> System.out.println(e));
                //String[] s = Arrays.stream(iterator.next());
                count++;
                String[] sLine = iterator.next();
                if(count<3)
                    continue;
                int bettingCount = Integer.parseInt(sLine[3]);//注数
                int graduationCount = Integer.parseInt(sLine[8]);//倍数
                double bettingUnit = Double.parseDouble(sLine[6]);//单位
                double bettingAmount = bettingCount*graduationCount*bettingUnit;

                betContent.setLotteryCode(sLine[0]);//彩种
                betContent.setPlayDetailCode(sLine[1]);//玩法
                betContent.setBettingNumber(sLine[2]);//号码
                betContent.setBettingCount(bettingCount);//注数
                if ("N".equals(sLine[4]))
                    betContent.setBettingAmount(bettingAmount);//金额,可能使用已填的值
                else
                    betContent.setBettingAmount(Double.parseDouble(sLine[4]));
                betContent.setBettingPoint(sLine[5]);//返点
                betContent.setBettingUnit(Double.parseDouble(sLine[6]));//金额模式
                betContent.setBettingIssue(sLine[7]);//期号
                betContent.setGraduationCount(graduationCount);//倍数
                contentList.add(betContent);
//                System.out.println(betContent);

//                iterator.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }

    public static void readCSV1() throws Exception {
        File file = new File("/Users/duke/Documents/test/betcontent1.csv");
//        FileReader fReader = new FileReader(file);
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK"); //或GB2312,GB18030UTF-8
        BufferedReader read = new BufferedReader(isr);

        CSVReader csvReader = new CSVReader(read,';');
        String[] strs = csvReader.readNext();
        if(strs != null && strs.length > 0){
            for(String str : strs)
                if(null != str && !str.equals(""))
                    System.out.print(str);
            System.out.println("\n---------------");
        }
        List<String[]> list = csvReader.readAll();
        for(String[] ss : list){
            for(String s : ss)
                if(null != s && !s.equals(""))
                    System.out.print(s);
        }
        csvReader.close();
    }
}
