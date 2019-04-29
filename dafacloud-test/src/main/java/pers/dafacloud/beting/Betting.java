package pers.dafacloud.beting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import pers.dafacloud.common.Log;
import pers.dafacloud.common.ReadCSV;
import pers.dafacloud.enums.Path;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.pageLogin.Login;
import net.sf.json.JSONObject;
import pers.dafacloud.pojo.BetContent;

public class Betting {

	static Path bettingPath = Path.betting;

	/** bet方法 */
	public static void bet(String body) {
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
			//BodyInsertIntoMysql.bodyIntoMysql(bodyAndResult(body, betResult));
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
	public static String body() {
		List<BetContent> contentList = ReadCSV.readCSV(); //初始化投注内容
		for (int i = 0; i < contentList.size(); i++) {
			BetContent betContent = contentList.get(i);
			String lotteryCode = betContent.getLotteryCode();
			if("123".equals(lotteryCode)){ //根据彩种获取返点
				betContent.setBettingPoint(GetBetRebate.allRebate.getString(""));
			}
			if ("N".equals(InitializaIssueEndtime.issueFive))
				betContent.setBettingIssue(String.valueOf(InitializaIssueEndtime.issueFive));
			System.out.println(betContent);
			Betting.bet(betContent.toString());//执行投注
		}
		return null;
	}

	/**
	 * 把投注结果和 用户添加body 中
	 */
	public static String bodyAndResult(String body, String betResult) {
		JSONObject jsonArray = JSONObject.fromObject(betResult);	
		//return body + "&Value=" + jsonArray.get("Value") + "&user=" + Params.user;
		return null;
	}

	public static void main(String[] args) {
		Login login = new Login();
		login.loginDafaCloud("duke01","123456");
		InitializaIssueEndtime.executeInitializa();//初始化期数倒计时
		GetBetRebate.getAllRebate();//初始化返点

	}
}
