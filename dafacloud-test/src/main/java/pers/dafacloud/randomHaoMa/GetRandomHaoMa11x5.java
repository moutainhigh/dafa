package pers.dafacloud.randomHaoMa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

import pers.dafacloud.common.Log;

public class GetRandomHaoMa11x5 {
	public static String[] haoMas= {"01","02","03","04","05","06","07","08","09","10","11"};
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			rxJi("R5");
		}
		
	}
	/**
	 * 现用，任选几
	 * */
	public static Map<String, String> rxJi(String rx){
		String R2="R2",R3="R3",R4="R4",R5="R5",R6="R6",R7="R7",R8="R8",Q1="Q1",Q2="Q2",Z2="Z2",Q3="Q3",Z3="Z3",L3="L3",L4="L4",L5="L5";
		if(rx==R2){
			return rx(R2, 5);
			
		}else if(rx=="R3"){
			return rx(R3, 5);
			
		}else if(rx=="R4"){
			return rx(R4, 6);
			
		}else if(rx=="R5"){
			return rx(R5, 7);
			
		}else if(rx=="R6"){
			return rx(R6, 7);
			
		}else if(rx=="R7"){
			return rx(R7, 9);
			
		}else if(rx=="R8"){
			return rx(R8, 9);
			
		}else if(rx=="Q1"){
			return rx(Q1, 3);
			
		}else if(rx=="Q2"){//前2直选
			return zx2(Q2, 3);
			
		}else if(rx=="Z2"){
			return rx(Z2, 3);
			
		}else if(rx=="Q3"){//前3直选
			return zx3(Q3, 3);
			
		}else if(rx=="Z3"){
			return rx(Z3, 5);
			
		}else if(rx=="L3"){//乐三
			return zx3(L3, 1);
			
		}else if(rx=="L4"){
			return rx(L4, 4);
			
		}else if(rx=="L5"){
			return rx(L5, 5);
			
		}else {
			Log.infoError("未找到对应的玩法："+rx);
			return null;
		}
	}
	
	/**
	 * 任选公共方法C52
	 * params : stype 任选几，num 随机选择几个号
	 * rx(R2, 5) //随机选2-5个号码
 	 * */
	private static Map<String, String> rx(String stypes,int num) {
		int stype=Integer.parseInt(stypes.split("")[1]);
		//随机选择任几到num的数据
		int randomInt=stype+(int)(Math.random()*(num-stype+1));
		//System.out.println(randomInt);
		List<String> list=CalculateAC.rx(haoMas, randomInt);
		
		String betContent=Joiner.on(",").join(list);//list转字符串
		
		//计算C(list,任选几)的组合数
		int betCount=(int)CalculateAC.combinations(list, stype);
		int betAmount=stypes.equals("L4")?betCount*10:stypes.equals("L5")?betCount*14:betCount*2;
		//System.out.println(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		Log.info(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		Map<String, String> map=new HashMap<>();
		map.put("betCount", String.valueOf(betCount));
		map.put("betAmount", String.valueOf(betAmount));
		map.put("betContent", betContent+";");//投注内容
		map.put("betTypes", stypes);//玩法
		return map;
	}
	/**
	 * 直选3
	 * */
	public static Map<String, String> zx3(String stypes,int num) {
		List<String> listHaoMa=new ArrayList<>(Arrays.asList(haoMas));//号码数组转list,用来清除已经选择的元素
		//第一位
		List<String> list1=CalculateAC.rx(haoMas, 1+(int)(Math.random()*num));
		listHaoMa.removeAll(list1);//清除第一位选择的号码
		//第二位
		List<String> list2=CalculateAC.rx(listHaoMa.toArray(new String[listHaoMa.size()]), 1+(int)(Math.random()*num));
		listHaoMa.removeAll(list2);//清除第二位选择的号码
		//第三位
		List<String> list3=CalculateAC.rx(listHaoMa.toArray(new String[listHaoMa.size()]), 1+(int)(Math.random()*num));
		
		List<List<String>> list =new ArrayList<List<String>>();
		list.add(list1);list.add(list2);list.add(list3);
		return contentMap(list, stypes, num);
	}
	/**
	 * 直选2
	 * */
	public static Map<String, String> zx2(String stypes,int num) {
		List<String> listHaoMa=new ArrayList<>(Arrays.asList(haoMas));//号码数组转list,用来清除已经选择的元素
		//第一位
		List<String> list1=CalculateAC.rx(haoMas, 1+(int)(Math.random()*num));
		//System.out.println(list1);
		listHaoMa.removeAll(list1);//清除第一位选择的号码
		//第二位
		List<String> list2=CalculateAC.rx(listHaoMa.toArray(new String[listHaoMa.size()]), 1+(int)(Math.random()*num));
		//System.out.println(list2);
		
		List<List<String>> list =new ArrayList<List<String>>();
		list.add(list1);list.add(list2);
		return contentMap(list, stypes, num);
	}
	
	public static Map<String, String> contentMap(List<List<String>> list,String stypes,int num) {
		int betCount=1;
		String betContent="";
		//计算注数和投注号
		for (int i = 0; i < list.size(); i++) {
			betCount=betCount*list.get(i).size();
			betContent=betContent+StringUtils.join(list.get(i).toArray(), ",")+"|";
		}
		int betAmount=stypes.equals("L3")?betCount*6:betCount*2;
		betContent=betContent.substring(0,betContent.length()-1);
		//System.out.println(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		Log.info(betContent+"^"+stypes+"^"+betCount+"注^"+betAmount+"元");
		//返回map
		Map<String, String> map=new HashMap<>();
		map.put("betCount", String.valueOf(betCount));
		map.put("betAmount", String.valueOf(betAmount));
		map.put("betContent", betContent);
		map.put("betTypes", stypes);
		return map;
	}
	/**暂时没有用====================
	 * 任二，随机取2-7个号，
	 * 先计算出所有的组合，再取其中一个
	 * */	
	/*public static void r22() {
		String[] s= {"1","2","3","4","5","6","7","8","9","10","11"};
		//随机取2到7个数C(11,2-7),返回所有的组合
		CalculateAliCopy.list=new ArrayList<>();
		ArrayList<String[]> list=CalculateAliCopy.list;
		int x=2+(int)(Math.random()*6);
		CalculateAliCopy.combinationSelect(s, x);
		
		//返回的集合里面数据取一个数组
		int x2=(int)(Math.random()*list.size());
		System.out.println(Arrays.asList(list.get(x2)));
		//从随机取的数组里面计算C(String[],2)值多少
		CalculateAliCopy.combinationSelect(list.get(x2), 2);
		
	}*/
}
