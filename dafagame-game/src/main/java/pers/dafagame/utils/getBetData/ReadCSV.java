package pers.dafagame.utils.getBetData;

import com.opencsv.CSVReader;
import pers.dafagame.pojo.BetContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    public static  List<BetContent> betContentList = new ArrayList();

    public static void main(String[] args) throws Exception {
    }

    

    public static void readCSV2() throws Exception {
        File file = new File("/Users/duke/Documents/testCookie/betcontent1.csv");
        //FileReader fReader = new FileReader(file);
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
