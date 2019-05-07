package pers.dafacloud.utils.getBetData;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import net.sf.json.JSONObject;
import pers.dafacloud.page.beting.GetBetRebate;
import pers.dafacloud.pojo.BetContent;
import pers.dafacloud.utils.common.FileUtils;

public class ReadCSV {

    public static  List<BetContent> betContentList = new ArrayList();

    public static void main(String[] args) throws Exception {
        getBetDateFromCSV();
    }

    public static  List<BetContent> getBetDateFromCSV() {
        String srcPath = "/Users/duke/Documents/test/betcontent1.csv";
        String charset = "GBK";

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
                if(count<3)//忽略前两行
                    continue;
                //获取
                String  lotteryCode = sLine[0];//彩种
                String  playDetailCode = sLine[1];//玩法
                String  bettingNumber = sLine[2];//号码
                int bettingCount = Integer.parseInt(sLine[3]);//注数
                String  bettingAmountCSV = sLine[4];//金额
                String  bettingPointCSV = sLine[5];//返点
                double bettingUnit = Double.parseDouble(sLine[6]);//单位
                String  bettingIssue = sLine[7];//期号
                int graduationCount = Integer.parseInt(sLine[8]);//倍数
                String  remark = sLine[9];//备注
                double bettingAmount = bettingCount*graduationCount*bettingUnit*2;//计算后的金额
                //读取lotteryConfig.json配置文件，获取彩种对应的返点
                JSONObject jsonObject = FileUtils.getLotteryConfig("/Users/duke/Documents/github/dafa/dafacloud-test/src/main/resources/lotteryConfig.json");
                JSONObject allRebate =  GetBetRebate.allRebate;//获取所有返点json
                String bettingPoint = allRebate.getString(jsonObject.getJSONObject(lotteryCode).getString("lotteryType"));//1.获取配置文件的lotteryType，再获取allRebate对应的返点

                //设置
                betContent.setLotteryCode(lotteryCode);//彩种
                betContent.setPlayDetailCode(playDetailCode);//玩法
                betContent.setBettingNumber(bettingNumber);//号码
                betContent.setBettingCount(bettingCount);//注数
                if ("N".equals(bettingAmountCSV))
                    betContent.setBettingAmount(bettingAmount);//使用计算的金额
                else
                    betContent.setBettingAmount(Double.parseDouble(bettingAmountCSV));//使用填入的金额
                if("N".equals(bettingPointCSV))
                    betContent.setBettingPoint(bettingPoint);//使用CSV的返点
                else
                    betContent.setBettingPoint(bettingPointCSV);//使用计算的返点
                betContent.setBettingUnit(bettingUnit);//金额模式
                betContent.setBettingIssue(bettingIssue);//期号
                betContent.setGraduationCount(graduationCount);//倍数
                betContent.setDescription(remark);
                betContentList.add(betContent);
//                System.out.println(betContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return betContentList;
    }

    public static void readCSV2() throws Exception {
        File file = new File("/Users/duke/Documents/test/betcontent1.csv");
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
