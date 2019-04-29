package pers.dafacloud.beting;

import java.util.Map;

import pers.dafacloud.randomHaoMa.GetHaoMa;


public class SetBetContentInfo {
	
	//public  static BetContentInfo betContentInfo =new BetContentInfo();;
	static {
		System.setProperty("log.info.file", "lcBet01.log");
	}
	public static void main(String[] args) {
		SetBetContentInfo setBetContent = new SetBetContentInfo();
		setBetContent.setBetContent(1121, 1, "R3");
		//System.out.println(betContentInfo);
	}
	
	public  void  setBetContent(int lotId,int multiple,String betTypes) {
		/*betContentInfo.setAction("ok");
		betContentInfo.setLotId(lotId);
		//betContentInfo.setTermNo(GetIssue.getIssue(lotId));
		
		betContentInfo.setMultiple(multiple);
		
		Map<String,String> map=  GetHaoMa.getHaoMa(lotId, betTypes);;
		System.out.println(map.get("betCount"));
		System.out.println(Integer.parseInt(map.get("betCount")));
		betContentInfo.setBetCount(Integer.parseInt(map.get("betCount")));
		betContentInfo.setBetAmount(Integer.parseInt(map.get("betAmount")));
		betContentInfo.setBetContent(map.get("betContent"));
		betContentInfo.setBetTypes(betTypes+";");
		betContentInfo.setTermNos("");
		betContentInfo.setTermCount(1);
		betContentInfo.setWinStop(false);
		betContentInfo.setStopWinCount(1);
		betContentInfo.setCouponId(0);*/
	}
}
