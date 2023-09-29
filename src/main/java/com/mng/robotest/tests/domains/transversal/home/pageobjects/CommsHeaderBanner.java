package com.mng.robotest.tests.domains.transversal.home.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class CommsHeaderBanner extends PageBase {

	private static final String XPATH_HEADER_BANNER = "//micro-frontend[@data-testid[contains(.,'userCommsHeaderBanner')]]";
	private static final String XPATH_BANNER_LOYALTY = XPATH_HEADER_BANNER + "//*[@data-testid='commsBanner.mangoLikesYou']"; 
	
	public boolean isHeaderBanner() {
		return state(Visible, XPATH_HEADER_BANNER).check();
	}

	public boolean isHeaderBannerMangoLikesYou(int seconds) {
		return state(Visible, XPATH_BANNER_LOYALTY).wait(seconds).check(); 
	}
}
