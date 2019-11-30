/**
 * Copyright (c) 2015, http://www.wuleyou.com/ All Rights Reserved. 
 * 
 */  
package com.dafagame.dafaGameUtils.cardLogic;

/**  
 * @Description:扎金花牌类型
豹子（炸弹）：三张点相同的牌。eg：AAA、222。
顺金（同花顺、色托）：花色相同的顺子。eg：黑桃456、红桃789。
金花（色皮）：花色相同，非顺子。eg：黑桃368，方块145。
顺子（拖拉机）：花色不同的顺子。eg：黑桃5红桃6方块7。
对子：两张点数相同的牌。eg：223，334。
单张：三张牌不组成任何类型的牌。
特殊牌型：2，3，5。
 */
public enum ZjhCardsType {
	BAO_ZI(6),		//豹子
	SHUN_JIN(5),	//顺金
	JIN_HUA(4),		//金花
	SHUN_ZI(3),		//顺子
	DUI_ZI(2),		//对子
	DAN_ZHAN(1),	//单张
	ER_SAN_WU(0),	//235
	;
	
	// 大小
	public final int power;
	

	/**
	 * @param power
	 */
	private ZjhCardsType(int power) {
		this.power = power;
	}

}
