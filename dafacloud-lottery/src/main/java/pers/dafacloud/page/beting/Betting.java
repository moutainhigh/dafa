package pers.dafacloud.page.beting;

import java.util.List;

import org.testng.Assert;
import pers.dafacloud.utils.common.Log;
import pers.dafacloud.utils.getBetData.ReadCSV;
import pers.dafacloud.utils.enums.Path;
import pers.dafacloud.utils.httpUtils.Request;
import pers.dafacloud.page.pageLogin.Login;
import net.sf.json.JSONObject;
import pers.dafacloud.pojo.BetContent;

public class Betting {

	static Path bettingPath = Path.betting;

	/** bet方法 */
	public static void bet(BetContent betContent) {
		if (betContent == null) {
			Log.infoError("body空");
			return;
		}
		if ("N".equals(betContent.getBettingIssue()))
			betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
		String result = Request.doPost(bettingPath.value, betContent.toString());
		//BodyInsertIntoMysql.bodyIntoMysql(bodyAndResult(body, betResult));
		Log.info(result);
		Log.info(betContent.toString());
		//Reporter.log(result);
		try { //投注间隔2秒
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(true,result.contains("成功"),"投注失败");

	}

	/**
	 * 投注内容封装，投注内容写入到body中
	 */
	public static String body() {
		List<BetContent> contentList = ReadCSV.getBetDateFromCSV(); //读取csv文件，初始化投注内容
		for (int i = 0; i < contentList.size(); i++) {
			BetContent betContent = contentList.get(i);
			String lotteryCode = betContent.getLotteryCode();
			if("123".equals(lotteryCode)){ //根据彩种类型获取返点
				betContent.setBettingPoint(GetBetRebate.allRebate.getString(""));
			}
			if ("N".equals(InitializaIssueEndtime.issueFive))
				betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
			System.out.println(betContent);
			Betting.bet(betContent);//执行投注
		}
		return null;
	}

	/**
	 * 把投注结果和 用户添加body 中
	 */
	public static String bodyAndResult(String body, String betResult) {
		JSONObject jsonArray = JSONObject.fromObject(betResult);	
		//return body + "&Value=" + jsonArray.get("Value") + "&userCenter=" + Params.userCenter;
		return null;
	}

	public static void main(String[] args) {
		Login login = new Login();
		login.loginDafaCloud("dukedf0002","123456");
		//InitializaIssueEndtime.executeInitializa();//初始化期数倒计时
		//GetBetRebate.getAllRebate();//初始化返点
		String s = Request.doPost("http://caishen03.com/v1/betting/addChaseBetting", "" +
				"chaseBettingData={\"isStopAfterWinning\":1," +
				"\"startIssue\":\"201906031110\",\"lotteryCode\":\"1008\",\"bettingPoint\":\"8\"" +
				",\"playDetailCode\":\"1008A11\",\"bettingUnit\":1,\"bettingAllAmount\":1800" +
				",\"chaseCount\":2,\"eachInfo\":[{\"bettingNumber\":\"-,-,-,-,0 1 2 3 4 5 7 8 9\"" +
				",\"bettingCount\":9}],\"eachOrder\":[{\"bettingIssue\":\"201906031110\"" +
				",\"graduationCount\":50,\"bettingAmount\":900},{\"bettingIssue\":\"201906031110\"" +
				",\"graduationCount\":50,\"bettingAmount\":900}]}");
		System.out.println(s);
//		bet();

	}
}
