package com.mng.robotest.test.pageobject.shop.checkout;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageRedirectPasarelaLoading extends PageBase {

	public static final String XPATH_IS_PAGE = "//div[@class[contains(.,'payment-redirect')]]/div[@class='loading' or @class='logo']";

	public boolean isPageUntil(int maxSeconds) {
		return state(Present, XPATH_IS_PAGE).wait(maxSeconds).check();
	}

	public boolean isPageNotVisibleUntil(int maxSeconds) {
		return state(Invisible, XPATH_IS_PAGE).wait(maxSeconds).check();
	}
}
