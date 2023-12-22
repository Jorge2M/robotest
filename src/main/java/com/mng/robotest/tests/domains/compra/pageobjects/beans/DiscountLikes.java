package com.mng.robotest.tests.domains.compra.pageobjects.beans;

public class DiscountLikes {

	private final int likes;
	private final float discount;
	
	private static final int EURO_LIKES_EQUIVALENCE = 200;
	
	private DiscountLikes(int likes, float discount) {
		this.likes = likes;
		this.discount = discount;
	}
	
	public static final DiscountLikes from(int likes, float discount) {
		return new DiscountLikes(likes, discount);
	}
	
	public static final DiscountLikes from(float discount) {
		int likes = (int) Math.ceil(discount * EURO_LIKES_EQUIVALENCE);
		return new DiscountLikes(likes, discount);
	}	
	
	public int getLikes() {
		return likes;
	}
	
	public float getDiscount() {
		return discount;
	}

}
