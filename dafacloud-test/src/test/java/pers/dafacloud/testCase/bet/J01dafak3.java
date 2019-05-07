package pers.dafacloud.testCase.bet;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pers.dafacloud.page.beting.Betting;
import pers.dafacloud.page.beting.InitializaIssueEndtime;
import pers.dafacloud.pojo.BetContent;
import pers.dafacloud.utils.getBetData.ReadCSV;
import pers.dafacloud.utils.report.ZTestReport;

import java.util.List;

@Listeners({ ZTestReport.class })
public class J01dafak3 {

    static List<BetContent>  betContentList;


    @BeforeClass
    public void beforeClass() {
        betContentList =  ReadCSV.getBetDateFromCSV();
    }

    @Test(priority = 1,description ="期号错误测试")
    public void testCase001(){
       BetContent betContent = betContentList.get(0);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase002(){
       BetContent betContent = betContentList.get(1);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase003(){
       BetContent betContent = betContentList.get(2);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase004(){
       BetContent betContent = betContentList.get(3);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase005(){
       BetContent betContent = betContentList.get(4);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase006(){
       BetContent betContent = betContentList.get(5);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase007(){
       BetContent betContent = betContentList.get(6);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase008(){
       BetContent betContent = betContentList.get(7);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase009(){
       BetContent betContent = betContentList.get(8);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase010(){
       BetContent betContent = betContentList.get(9);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase011(){
       BetContent betContent = betContentList.get(10);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }@Test(priority = 1,description ="二码直选正常场景")
    public void testCase012(){
       BetContent betContent = betContentList.get(11);
       betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
       Betting.bet(betContent);
    }
}
