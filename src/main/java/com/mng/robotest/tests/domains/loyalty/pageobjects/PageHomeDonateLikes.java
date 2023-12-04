package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.tests.domains.base.PageBase;

import org.openqa.selenium.By;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeDonateLikes extends PageBase {

	public enum ButtonLikes implements ElementPage {
		BUTTON_50_LIKES(50), 
		BUTTON_100_LIKES(100), 
		BUTTON_200_LIKES(200),
		BUTTON_800_LIKES(1000);
		
		int numLikes;
		ButtonLikes(int numLikes) {
			this.numLikes = numLikes;
		}
		public int getNumLikes() {
			return numLikes;
		}
		public String getXPath() {
			return "//button/span[text()[contains(.,' " + numLikes + "')]]";
		}
		
		public By getBy() {
			return By.xpath(getXPath());
		}
	}
	
	private static final String XP_PAGE = "//*[@class[contains(.,'loyalty_loyaltySpace')]]";
	private static final String XP_ICON_OPERATION_DONE = "//*[@class[contains(.,'icon-outline-done')]]";

	public boolean checkIsPage(int seconds) {
		return state(VISIBLE, XP_PAGE).wait(seconds).check();
	}
	
	public boolean isVisibleAny(int seconds, ButtonLikes... listButtons) {
		if (isVisible(listButtons[0], seconds)) {
			return true;
		}
		for (int i=1; i<listButtons.length; i++) {
			if (isVisible(listButtons[i], 0)) {
				return true;
			}
		}
		return false;
	}
	public boolean isVisible(ButtonLikes buttonLikes, int seconds) {
		return state(VISIBLE, buttonLikes.getXPath()).wait(seconds).check();
	}
	
	public void clickButton(ButtonLikes buttonLikes) {
		click(buttonLikes.getXPath()).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return state(VISIBLE, XP_ICON_OPERATION_DONE).wait(seconds).check();
	}
}