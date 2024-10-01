package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeDonateLikesNew extends PageBase implements PageHomeDonateLikes {

	private static final String XP_PAGE = "//*[@data-testid='spaceMangoLikesYou.experienceDetail.modal']";
	private static final String XP_BUTTONS_SECTION = "//*[@data-testid='spaceMangoLikesYou.nonProfitDetailFooter.section']";
	private static final String XP_BUTTON_LIKES = XP_BUTTONS_SECTION + "//button";
	private static final String XP_BUTTON_DONATE = "//*[@data-testid='spaceMangoLikesYou.nonProfitDetailFooter.button']";
	private static final String XP_SNACKBAR_OK = "//*[@data-testid='snackbar-notification']//*[text()='¡Hecho!']";
	
	private String getXPathButtonLikes(int numLikes) {
		return XP_BUTTON_LIKES + "//self::*[text()='" + numLikes + "']";
	}
	
	@Override
	public boolean isPage(int seconds) {
		return state(PRESENT, XP_PAGE).wait(seconds).check();
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
		return state(VISIBLE, getXPathButtonLikes(numLikes)).wait(seconds).check();
	}
	
	@Override
	public void clickButton(int numLikes) {
		click(getXPathButtonLikes(numLikes)).exec();
		click(XP_BUTTON_DONATE).exec();
	}
	 
	@Override
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return state(VISIBLE, XP_SNACKBAR_OK).wait(seconds).check();
	}

}
