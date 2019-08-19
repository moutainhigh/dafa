package pers.dafacloud.utils.randomHaoMa;

import java.util.Map;

public class GetHaoMa {

	final static int[] lottery11x5= {1121,1212,1228,1214};
	final static int[] lotteryk3= {2231,2233,2246};
	
	public static void main(String[] args) {
		

	}
	
	public static Map<String, String>  getHaoMa(int lotteryid,String betTypes) {
		
		if(useLoop(lottery11x5, lotteryid)) {
			return GetRandomHaoMa11x5.rxJi(betTypes);
		}else if (useLoop(lotteryk3, lotteryid)) {
			return GetRandomHaoMaK3.rxJi(betTypes);
		}
		return null;
		
	}
	public static boolean useLoop(int[] arr,int targetValue){
	    for(int s:arr){
	        if(s==targetValue)
	            return true;
	        }  
	        return false;
	    }

}
