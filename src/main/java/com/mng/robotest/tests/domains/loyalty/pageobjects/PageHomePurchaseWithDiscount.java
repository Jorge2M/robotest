package com.mng.robotest.tests.domains.loyalty.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageHomePurchaseWithDiscount extends PageBase {

	private static final String ID_BLOCK_LOYALTY = "loyaltyLoyaltySpace";
	private static final String XP_BUTTON_PURCHASE_NOW = "//a[text()='Comprar ahora']";
	
	public boolean checkIsPage() {
		return state(VISIBLE, ID_BLOCK_LOYALTY).check();
	}

	public boolean areVisibleButtonPurchaseNow() {
		return state(VISIBLE, XP_BUTTON_PURCHASE_NOW).wait(2).check();
	}
	
}