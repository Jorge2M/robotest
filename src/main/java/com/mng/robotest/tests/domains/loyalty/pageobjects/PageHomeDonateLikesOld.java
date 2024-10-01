package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeDonateLikesOld extends PageBase implements PageHomeDonateLikes {

	private static final String XP_PAGE = "//*[@class[contains(.,'loyalty_loyaltySpace')]]";
	private static final String XP_ICON_OPERATION_DONE = "//*[@class[contains(.,'icon-outline-done')]]";
	
	private String getXPathButtonDonate(int numLikes) {
		return "//button/span[text()[contains(.,' " + numLikes + "')]]";
	}
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_PAGE).wait(seconds).check();
	}
	
	@Override
	public boolean isVisibleAnyButton(int seconds, Integer... numLikes) {
		if (isVisibleButton(numLikes[0], seconds)) {
			return true;
		}
		for (int i=1; i<numLikes.length; i++) {
			if (isVisibleButton(numLikes[i], 0)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isVisibleButton(int numLikes, int seconds) {
		String xpathButton = getXPathButtonDonate(numLikes);
		return state(VISIBLE, xpathButton).wait(seconds).check();
	}
	
	@Override
	public void clickButton(int numLikes) {
		String xpathButton = getXPathButtonDonate(numLikes);
		click(xpathButton).exec();
	}
	
	@Override
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return state(VISIBLE, XP_ICON_OPERATION_DONE).wait(seconds).check();
	}
	
}