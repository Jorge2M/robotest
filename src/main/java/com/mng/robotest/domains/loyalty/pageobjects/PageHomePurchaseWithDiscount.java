package com.mng.robotest.domains.loyalty.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import org.openqa.selenium.By;


public class PageHomePurchaseWithDiscount extends PageObjTM {

	private static final String ID_BLOCK_LOYALTY = "loyaltyLoyaltySpace";
	private static final String XPATH_BUTTON_PURCHASE_NOW = "//a[text()='Comprar ahora']";
	
	public boolean checkIsPage() {
		return (state(Visible, By.id(ID_BLOCK_LOYALTY)).check());
	}

	public boolean areVisibleButtonPurchaseNow() {
		return (state(Visible, By.xpath(XPATH_BUTTON_PURCHASE_NOW)).wait(2).check());
	}
}