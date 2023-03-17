package com.mng.robotest.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageRedirectPasarelaLoading extends PageBase {

	public static final String XPATH_IS_PAGE = "//div[@class[contains(.,'payment-redirect')]]/div[@class='loading' or @class='logo']";

	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_IS_PAGE).wait(seconds).check();
	}

	public boolean isPageNotVisibleUntil(int seconds) {
		return state(Invisible, XPATH_IS_PAGE).wait(seconds).check();
	}
}
