package pers.dafacloud.pageLogin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;

//import bets.Params;
import pers.dafacloud.httpUtils.Request;

public class Login1 {

	public static Cookie lcTestCooike = null;

	public static void main(String[] args) {
		LoginLcTest();
	}
	
	static {
		System.setProperty("log.info.file", "lcBet01.log");
	}
	
	/**
	 *现用：通过cookieStore获取cookie
	 * */
	public static void LoginLcTest() {
//		String url = Params.login;
		String url = "";
		String body = "action=101000&UserName=13312345611&Password=46f94c8de14fb36680850768ff1b7f2a";
		// 添加头
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		// 获取带cookie的头
		String result = Request.doPost(url, body, headers);
//		@SuppressWarnings("unchecked")
//		List<Cookie> cookies = (List<Cookie>) resultMap.get("cookies");
//		for (int i = 0; i < cookies.size(); i++) {
//			System.out.println(i + "-cookies   " +cookies.get(i).getName()+":"+ cookies.get(i).getValue());
//		}
//		String result = (String) resultMap.get("body");
//		System.out.println(result);
//		lcTestCooike = cookies.get(2);
		// return cookies.get(2);
	}

	/**
	 * 手动添加cookie，测试使用，
	 */
	public static void addCookie() {
		
		BasicClientCookie cookie = new BasicClientCookie("lctest_L", "Xz/+XuQyYY0xWw8talwxn51apwHjaHyn79E12KL5FC8VFBRZofkObZOft6VubSKUEkxdcXXnd6jbnXunZ1B3EV5iCL3jOg3zMevFbIEDwkc=");
		cookie.setVersion(0);
//		cookie.setDomain(Params.host); // 设置范围
		cookie.setDomain(""); // 设置范围
		cookie.setPath("/");
		lcTestCooike = cookie;
	}

	/**
	 * 从返回头里获取cookie
	 * */ 
	/*public static String lctest2() {
		String url = "http://m.lctest.cn/Ajax/DoAjax.aspx";
		String body = "action=101000&UserName=13058a47610&Password=4cf81a6138eb1389d45eaf29f23b545e";
		*//* 请求返回结果 *//*
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, Object> resultMap = Request.doPost(url, body, headers);
		Header[] header = (Header[]) resultMap.get("Header");
		String cookie = "";
		for (Header h : header) {
			String Hvalue = h.getValue();
			if (Hvalue.contains("lctest_L")) {
				int startIndex = Hvalue.indexOf("lctest_L=");
				int endIndex = Hvalue.indexOf(";");
				cookie = Hvalue.substring(startIndex + 9, endIndex + 1);
			}
		}
		String result = (String) resultMap.get("result");
		System.out.println(result);
		System.out.println(cookie);
		return cookie;
	}*/
}
