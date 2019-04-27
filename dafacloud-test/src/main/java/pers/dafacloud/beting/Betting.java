package pers.dafacloud.beting;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import pers.dafacloud.common.Log;
import pers.dafacloud.enums.Path;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.pageLogin.Login;
import net.sf.json.JSONObject;
import pers.dafacloud.pojo.BetContent;

public class Betting {

	static Path bettingPath = Path.betting;

	/** bet方法 */
	public static void bet( String body) {
		if (body == "") {
			Log.infoError("body" + body);
			return;
		}
		//添加header
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		//
		String betResult = Request.doPost(bettingPath.value, body, headers);
		//
		if (betResult.contains("成功")) {
			BodyInsertIntoMysql.bodyIntoMysql(bodyAndResult(body, betResult));
			Log.info(betResult);
			Log.info(body);
		} else {
			Log.info(body);
			Log.infoError(betResult);
		}
	}

	/**
	 * 投注内容封装，投注内容写入到body中
	 */
	public static String body(String lotId, int termNo, Map<String, String> map) {
		if (termNo == 0) {
			return "";
		}
		if (MapUtils.isEmpty(map)) {
			return "";
		}
		String body = String.format("", lotId, termNo, map.get("betCount"), map.get("betAmount"),
				map.get("betContent"), map.get("betTypes"));
		return body;
	}

	/**
	 * 把投注结果和 用户添加body 中
	 */
	public static String bodyAndResult(String body, String betResult) {
		JSONObject jsonArray = JSONObject.fromObject(betResult);	
		return body + "&Value=" + jsonArray.get("Value") + "&user=" + Params.user;
	}

	public static void main(String[] args) {
		Login login = new Login();
		login.getDafaCooike("duke01","123456");
		//JSONObject allRebate= GetBetRebate.getAllRebate();
		int bettingCount = 1;//注数
		double bettingUnit = 1;//金额模式
		int graduationCount = 1;//倍数
		double bettingAmount = bettingCount*bettingUnit*graduationCount;
		BetContent betContent = new BetContent();
		betContent.setLotteryCode("1205");//彩种
		betContent.setPlayDetailCode("1205A11");//玩法
		betContent.setBettingNumber("1,-,-");//号码
		betContent.setBettingCount(bettingCount);//注数
		betContent.setBettingAmount(bettingAmount);//金额
		betContent.setBettingPoint("3");//返点
		betContent.setBettingUnit(bettingUnit);//金额模式
		betContent.setBettingIssue("20190426146");//期号
		betContent.setGraduationCount(graduationCount);//倍数
		System.out.println(betContent);
		//Betting.bet("bettingData=[{\"lotteryCode\":\"1205\",\"playDetailCode\":\"1205A11\",\"bettingNumber\":\"1,-,-\",\"bettingCount\":1,\"bettingAmount\":2,\"bettingPoint\":\"3\",\"bettingUnit\":1,\"bettingIssue\":\"20190426124\",\"graduationCount\":1}]");
		Betting.bet(betContent.toString());
	}
}
