package com.mng.robotest.tests.domains.compra.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRedirectPasarelaLoading extends PageBase {

	public static final String XP_IS_PAGE = "//div[@class[contains(.,'payment-redirect')]]/div[@class='loading' or @class='logo']";

	public boolean isPage(int seconds) {
		return state(PRESENT, XP_IS_PAGE).wait(seconds).check();
	}

	public boolean isPageNotVisibleUntil(int seconds) {
		return state(INVISIBLE, XP_IS_PAGE).wait(seconds).check();
	}
}
