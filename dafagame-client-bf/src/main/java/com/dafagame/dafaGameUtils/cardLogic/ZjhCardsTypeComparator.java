/**
 * Copyright (c) 2015, http://www.wuleyou.com/ All Rights Reserved. 
 * 
 */  
package com.dafagame.dafaGameUtils.cardLogic;

import static com.dafagame.dafaGameUtils.cardLogic.ZjhCardsType.*;


/**
 * @Description:炸金花牌型比较
 */
public class ZjhCardsTypeComparator {

	private ZjhCardsTypeComparator() {}

	/**
	 * @Description:比牌
	 * @param my
	 * @param other
	 * @return true:我大 false：我小
	 */
	public static boolean compare(ZjhCards my, ZjhCards other) {

		if (my.type == ER_SAN_WU && other.type == BAO_ZI && 1 == other.minCard.num) {
			return true;
		} else if (my.type == BAO_ZI && 1 == my.midCard.num && other.type == ER_SAN_WU) {
			return false;
		} else if (my.type.power > other.type.power) {
			return true;
		} else if (my.type.power < other.type.power) {
			return false;
		}  else if (my.type.power == other.type.power) {// 牌型相同
			if (my.type == SHUN_ZI || my.type == SHUN_JIN) {// 顺子

				ZjhCard myMax = my.maxCard;
				ZjhCard otherMax = other.maxCard;

				if(my.maxCard.power == 14 && my.midCard.power == 3){
					myMax = my.midCard;
				}

				if(other.maxCard.power == 14 && other.midCard.power == 3){
					otherMax = other.midCard;
				}

				if(myMax.power != otherMax.power){
					return myMax.power > otherMax.power;
				}

				return myMax.type.ordinal() > otherMax.type.ordinal();
			}
			
			if (my.type == DUI_ZI) {// 对子先比较对子的大小，再比较单牌的大小
				if (my.midCard.power == other.midCard.power) {
					ZjhCard myCompareCard = my.maxCard.power == my.midCard.power? my.minCard : my.maxCard;
					ZjhCard otherCompareCard = other.maxCard.power == other.midCard.power? other.minCard : other.maxCard;
					
					if(myCompareCard.power != otherCompareCard.power){
						return myCompareCard.power > otherCompareCard.power;
					}

					return myCompareCard.type.ordinal() > otherCompareCard.type.ordinal();
				} else {
					// midCard对子牌
					return my.midCard.power > other.midCard.power;
				}
			}
			
			if (my.maxCard.power != other.maxCard.power) {
				return my.maxCard.power > other.maxCard.power;
			} else if (my.midCard.power != other.midCard.power) {
				return my.midCard.power > other.midCard.power;
			} else if (my.minCard.power != other.minCard.power) {
				return my.minCard.power > other.minCard.power;
			}
			
			return my.maxCard.type.ordinal() > other.maxCard.type.ordinal();
			
		} else {
			return false;
		}
	}
}
