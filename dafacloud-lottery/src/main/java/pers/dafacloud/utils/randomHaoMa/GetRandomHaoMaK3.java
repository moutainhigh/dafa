package pers.dafacloud.utils.randomHaoMa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;

import pers.dafacloud.utils.common.Log;

public class GetRandomHaoMaK3 {
	private static String[] haoMasRBTH= {"12","13","14","15","16","23","24","25","26","34","35","36","45","46","56"};//二不同号
	private static String[] haoMas1= {"1","2","3","4","5","6"};
	private static String[] haoMashezhi= {"04","05","06","07","08","09","10","11","12","13","14","15","16","17"};//和值
	private static String[] haoMasSTH= {"111","222","333","444","555","666"};//
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			rxJi("L3");
		}
	}
	/**
	 * 现用，任选几
	 * */
	public static Map<String, String> rxJi(String rx){
		String D2="D2",F2="F2",S2="S2",HZ="HZ",S3="S3",T3="T3",D3="D3",L3="L3";
		
		if(rx==D2){ //二不同号
			return rx(D2, 3,haoMasRBTH);
			
		}else if(rx=="F2"){ //二同号复选
			return rx(F2, 3,haoMas1);
			
		}else if(rx=="S2"){ //二同号单选
			return s2(S2, 2);
			
		}else if(rx=="HZ"){ //和值
			return rx(HZ, 3,haoMashezhi);
			
		}else if(rx=="S3"){ //三同好单选
			return rx(S3, 2,haoMasSTH);
			
		}else if(rx=="T3"){ //三同好通选
			return rx(T3, 1,new String[]{"111|222|333|444|555|666"});
			
		}else if(rx=="D3"){ //三不同号
			return rx(D3, 1,haoMas1);
			
		}else if(rx=="L3"){ //三连号通选
			return rx(L3, 1,new String[]{"123|234|345|456"});
		}else {
			Log.infoError("未找到对应的玩法："+rx);
			return null;
		}
	}
	
	//二不同号
	public static Map<String, String> rx(String stypes,int num,String[] haoma) {
		int randomInt=1+(int)(Math.random()*(num));
		if(stypes.equals("D3"))
		randomInt=3+(int)(Math.random()*(num));
		List<String> list=CalculateAC.rx(haoma, randomInt);
		//计算C(list,任选几)的组合数
		int betCount=stypes=="D3"?(int)CalculateAC.combinations(list, 3):list.size();
		int betAmount=betCount*2;
		String betContent=Joiner.on(",").join(list);
		//System.out.println(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		Log.info(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		Map<String, String> map=new HashMap<>();
		map.put("betCount", String.valueOf(betCount));
		map.put("betAmount", String.valueOf(betAmount));
		map.put("betContent", betContent);//投注内容
		map.put("betTypes", stypes);//玩法
		return map;
	}

	public static Map<String, String> s2(String stypes,int num) {
		List<String> listHaoMa=new ArrayList<>(Arrays.asList(haoMas1));//号码数组转list,用来清除已经选择的元素
		//第一位
		List<String> list1=CalculateAC.rx(haoMas1, 1+(int)(Math.random()*num));
		listHaoMa.removeAll(list1);
		for (int i = 0; i < list1.size(); i++) {
			list1.set(i, list1.get(i)+list1.get(i));
		}
		//第二位
		List<String> list2=CalculateAC.rx(listHaoMa.toArray(new String[listHaoMa.size()]), 1+(int)(Math.random()*num));
		
		List<List<String>> list =new ArrayList<List<String>>();
		list.add(list1);list.add(list2);
		
		return GetRandomHaoMa11x5.contentMap(list, stypes, num);
	}
}
