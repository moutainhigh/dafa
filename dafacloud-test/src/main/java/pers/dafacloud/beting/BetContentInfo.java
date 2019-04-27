package pers.dafacloud.beting;
//	static String bodyTemp = "action=ok&lotId=%s&termNo=%s&multiple=1&betCount=%s&"
//+ "betAmount=%s&termCount=1&betContent=%s;&betTypes=%s;&termNos=&Multiples=&winStop=FALSE&stopWinCount=1&CouponId=0";
public class BetContentInfo {
	private String action;
	private int  lotId;
	private int  termNo;
	private int  multiple;
	private int  betCount;
	
	private int betAmount;
	private int termCount;
	

	private String betContent;
	private String betTypes;
	private String termNos;
	private int Multiples;
	private boolean winStop;
	private int stopWinCount;
	private int CouponId;
	
	@Override
	public String toString() {
		return "action=" + action + "&lotId=" + lotId + "&termNo=" + termNo + "&multiple=" + multiple
				+ "&betCount=" + betCount + "&betAmount=" + betAmount +"&termCount="+ termCount+"&betContent=" + betContent + "&betTypes="
				+ betTypes + "&termNos=" + termNos + "&Multiples=" + Multiples + "&winStop=" + winStop
				+ "&stopWinCount=" + stopWinCount + "&CouponId=" + CouponId;
	}
	public void setTermCount(int termCount) {
		this.termCount = termCount;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public void setLotId(int lotId) {
		this.lotId = lotId;
	}

	public void setTermNo(int termNo) {
		this.termNo = termNo;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public void setBetCount(int betCount) {
		this.betCount = betCount;
	}

	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}

	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}

	public void setBetTypes(String betTypes) {
		this.betTypes = betTypes;
	}

	public void setTermNos(String termNos) {
		this.termNos = termNos;
	}

	public void setMultiples(int multiples) {
		Multiples = multiples;
	}

	public void setWinStop(boolean winStop) {
		this.winStop = winStop;
	}

	public void setStopWinCount(int stopWinCount) {
		this.stopWinCount = stopWinCount;
	}

	public void setCouponId(int couponId) {
		CouponId = couponId;
	}
}
