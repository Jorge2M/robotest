package com.mng.robotest.tests.domains.transversal.home.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class CommsHeaderBanner extends PageBase {

	private static final String XP_HEADER_BANNER = "//micro-frontend[@data-testid[contains(.,'userCommsHeaderBanner')]]";
	private static final String XP_BANNER_LOYALTY = XP_HEADER_BANNER + "//*[@data-testid='commsBanner.mangoLikesYou']"; 
	
	public boolean isHeaderBanner() {
		return state(Visible, XP_HEADER_BANNER).check();
	}

	public boolean isHeaderBannerMangoLikesYou(int seconds) {
		return state(Visible, XP_BANNER_LOYALTY).wait(seconds).check(); 
	}
}
