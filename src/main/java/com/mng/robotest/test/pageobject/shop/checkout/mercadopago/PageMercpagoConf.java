package com.mng.robotest.test.pageobject.shop.checkout.mercadopago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageMercpagoConf extends PageBase {
	
	private static final String XPATH_SECTION_REVIEW_DESKTOP = "//section[@class='review-step']";
	private static final String XPATH_BUTTON_PAGAR = "//button[@class[contains(.,'ch-btn')] and @type='submit']";
	
	public String getXPathSectionReview() {
		switch (channel) {
		case mobile:
			return XPATH_BUTTON_PAGAR;
		default:
		case desktop:
			return XPATH_SECTION_REVIEW_DESKTOP;
		}
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return state(Present, getXPathSectionReview()).wait(maxSeconds).check();
	}

	public void clickPagar() {
		click(XPATH_BUTTON_PAGAR).exec();
	}
}
