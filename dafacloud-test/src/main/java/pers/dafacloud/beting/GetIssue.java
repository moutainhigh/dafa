package pers.dafacloud.beting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.dafacloud.enums.Path;
import pers.dafacloud.httpUtils.Request;
import pers.dafacloud.pageLogin.Login;

/**
 *  奖期 和 结束销售时间 工具
 * */
public class GetIssue {

	private final static Logger Log = LoggerFactory.getLogger(GetIssue.class);
	static Path getServerTimeMillisecondPath = Path.getServerTimeMillisecond;

	public static int endTimeOne = 0;
	public static long issueOne = 0;

	public static int endTimeFive = 0;
	public static long issueFive = 0;

	/**
	 * 获取奖期，开始销售时间
	 * */
	public static long  getServerTimeMillisecond() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		String result= Request.doGet(getServerTimeMillisecondPath.value, headers);
		if (!result.contains("成功")) {
			Log.info("获取服务器时间失败："+result);
		}
		long l = JSONObject.fromObject(result).getLong("data");
		return l;
	}

	public static void cTime(){
		long currentMillTime = getServerTimeMillisecond();
		long now = System.currentTimeMillis();
		long lcMillTime = 0;
		String currentDate = "";
		try {
			SimpleDateFormat sdfOne = new SimpleDateFormat("yyyyMMdd");
			lcMillTime  =sdfOne.parse(sdfOne.format(now)).getTime();
			Date date = new Date();
			currentDate = sdfOne.format(date);
			System.out.println(currentDate);
		}catch(Exception e) {
			e.printStackTrace();
		}
		int second = (int)(currentMillTime - lcMillTime)/1000;//距离今日凌晨秒数
		int s = second%60;//秒
		int m = second/60%60;//分
		int h = second/(60*60);//时
		System.out.println("距离今日凌晨秒数："+second);
		System.out.println("当前时间："+h+":"+m+":"+s);

		//1分彩
		endTimeOne = 60-s;//倒计时秒数
		int issueOneNum = second/60+1;//期数
		//issueOne = currentDate + String.format("%04d",issueOneNum);
		issueOne = Long.parseLong(currentDate + String.format("%04d",issueOneNum));
		System.out.println("1分彩倒计时:" + endTimeOne);
		System.out.println("1分彩当前期数：" + issueOne);

		//5分彩
		endTimeFive = 5*60- second%(5*60)-1;//倒计时秒数
		issueFive = Long.parseLong(currentDate + String.format("%03d",second/(5*60)+1));//期数
		System.out.println("5分彩当前期数："+issueFive);

		int endFiveM = 5-1-endTimeFive/60;
		int endFives = 60 - endTimeFive%60;
		System.out.println("5分彩倒计时秒数:" + endTimeFive);
		System.out.println("5分彩倒计时:" + endFiveM + ":" + endFives);
	}


	public static void main(String[] args) {
		System.out.println(Long.parseLong("201904271016"));
		Login login = new Login();
		login.getDafaCooike("duke01","123456");
		cTime();
	}
}
