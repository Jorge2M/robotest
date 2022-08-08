package com.mng.robotest.domains.loyalty.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;


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
		
		public By getBy() {
			return By.xpath("//button/span[text()[contains(.,'" + numLikes + "')]]");
		}
	}
	
	private static final String XPATH_PAGE = "//*[@class[contains(.,'loyalty_loyaltySpace')]]";
	private static final String XPATH_ICON_OPERATION_DONE = "//*[@class[contains(.,'icon-outline-done')]]";

	public boolean checkIsPage(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_PAGE)).wait(maxSeconds).check());
	}
	
	public boolean isVisible(ButtonLikes buttonLikes, int maxSeconds) {
		return (state(Visible, buttonLikes.getBy()).wait(maxSeconds).check());
	}
	
	public void clickButton(ButtonLikes buttonLikes) {
		click(buttonLikes.getBy()).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_ICON_OPERATION_DONE)).wait(maxSeconds).check());
	}
}