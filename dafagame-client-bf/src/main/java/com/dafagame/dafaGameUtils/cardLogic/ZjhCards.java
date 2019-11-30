/**
 * Copyright (c) 2015, http://www.wuleyou.com/ All Rights Reserved. 
 * 
 */
package com.dafagame.dafaGameUtils.cardLogic;

import java.util.ArrayList;
import java.util.List;

/**  
 * @Description:炸金花的3张牌
 */
public class ZjhCards {
	// 玩家的牌数据
	public final List<ZjhCard> cards;
	// 当前牌型
	public final ZjhCardsType type;
	// 最大牌
	public final ZjhCard maxCard;
	// 中间
	public final ZjhCard midCard;
	// 最小牌
	public final ZjhCard minCard;
	
	/**
	 * @param cards
	 */
	public ZjhCards(List<ZjhCard> cards) {
		super();
		this.cards = cards;
		this.type = ZjhCardsTypeGetter.geCardsType(cards);
		
		List<ZjhCard> cardsTmp = new ArrayList<>(cards);
		ZjhCard maxCard = cardsTmp.get(0);
		for (int i = 0; i < cardsTmp.size(); i++) {
			ZjhCard card = cardsTmp.get(i);
			if (card.power > maxCard.power) {
				maxCard = card;
			}
		}

		
		this.maxCard = maxCard;
		
		cardsTmp.remove(maxCard);
		ZjhCard first = cardsTmp.get(0);
		ZjhCard second = cardsTmp.get(1);
		
		if (first.power < second.power) {
			this.minCard = first;
			this.midCard = second;
		} else {
			this.minCard = second;
			this.midCard = first;
		}
	}

	@Override
	public String toString() {
		return maxCard.index + "," + midCard.index + "," + minCard.index + "";
	}

	public List<Integer> toIndexList(){
		List<Integer> c = new ArrayList<>();
		c.add(maxCard.index);
		c.add(midCard.index);
		c.add(minCard.index);
		return c;
	}

	public List<Integer> toOrdinalList(){
		List<Integer> c = new ArrayList<>();
		c.add(maxCard.ordinal());
		c.add(midCard.ordinal());
		c.add(minCard.ordinal());
		return c;
	}
}
