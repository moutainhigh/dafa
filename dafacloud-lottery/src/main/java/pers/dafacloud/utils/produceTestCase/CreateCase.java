package pers.dafacloud.utils.produceTestCase;


import pers.dafacloud.model.BetContent;
import pers.dafacloud.utils.getBetData.ReadCSV;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CreateCase {

    public static void main(String[] args) {
        String filename = "J01dafak3";//类名，文件名
        writeCase(filename);
        //System.out.println(getCaseTemp());
        //System.out.println(createTestCase());
    }

    /**
     * 读取模板
     */
    public static String getCaseTemp(String filename) {
        String f = CreateCase.class.getClassLoader().getResource("caseTemplate.txt").getPath();
        File file = new File(f);//模板地址
        //System.out.println(System.getProperty("userCenter.dir"));
        StringBuffer sb = new StringBuffer();
        if (!file.exists())
            return "没有找到文件";
        try {

            FileReader fb = new FileReader(file);
            BufferedReader fr = new BufferedReader(fb);
            String str = null;
            while ((str = fr.readLine()) != null) {
                sb.append(str + "\r\n");
                //System.out.println("str:" + str);
            }

            fr.close();


        } catch (Exception e) {

        }
        return sb.toString().replaceAll("\\{classname\\}", filename);

    }

    /**
     * 生成内容
     * */
    public static String createTestCase() {
        StringBuffer sb = new StringBuffer();
        try {
            /*@Test(priority = 1,description ="二码直选")
            public void testUsersInfo(){
                BetContent betContent = listBetContent.get(0);
                betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
                Betting.bet(betContent);
            }*/
            /*String str = "  @Test(description =\"bbb\")\r\n" + "  public void " + lottery + "aaa() {	  \r\n"
                    + "	  Bet.bets(betContent.get(eee),model);	  \r\n" + "  }";*/

            String temp = "@Test(priority = 1,description =\"{desc}\")\n" +
                    "    public void {caseName}(){\n" +
                    "       BetContent betContent = listBetContent.get({num});\n" +
                    "       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));\n" +
                    "       Betting.bet(betContent);\n" +
                    "    }";
            List<BetContent>  listBetContent =  ReadCSV.getBetDateFromCSV();
            for (int i = 0; i < listBetContent.size(); i++) {
                String content = temp.replaceAll("\\{desc\\}",listBetContent.get(i).getDescription())
                        .replaceAll("\\{num\\}",String.valueOf(i))
                        .replaceAll("\\{caseName\\}","testCase"+String.format("%03d",i+1));
                sb.append(content);
            }

        } catch (Exception e) {

        }

        return sb.toString();

    }

    /**
     * 写入文件
     */
    public static void writeCase(String filename) {
        String caseTemp = getCaseTemp(filename);
        String cases = createTestCase();
        System.out.println(caseTemp.replaceAll("//插入文件", cases));
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("/Users/duke/Documents/github/dafa/dafacloud-testCookie/src/testCookie/java/pers/dafacloud/testCase/bet/" + filename + ".java"));
            bufferedWriter.write(caseTemp.replaceAll("//插入文件", cases));
            bufferedWriter.close();
        } catch (Exception e) {

        }
    }
}




