package com.mng.robotest.domains.loyalty.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomePurchaseWithDiscount extends PageBase {

	private static final String ID_BLOCK_LOYALTY = "loyaltyLoyaltySpace";
	private static final String XPATH_BUTTON_PURCHASE_NOW = "//a[text()='Comprar ahora']";
	
	public boolean checkIsPage() {
		return state(Visible, ID_BLOCK_LOYALTY).check();
	}

	public boolean areVisibleButtonPurchaseNow() {
		return state(Visible, XPATH_BUTTON_PURCHASE_NOW).wait(2).check();
	}
}