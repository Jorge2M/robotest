package com.mng.robotest.domains.loyalty.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageHomeConseguirPorLikes extends PageBase {

	private static final String XPATH_BUTTON_LIKES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XPATH_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	public boolean isPage(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_BUTTON_LIKES)).wait(maxSeconds).check());
	}
	
	public void selectConseguirButton() {
		click(By.xpath(XPATH_BUTTON_LIKES)).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_ICON_OPERATION_DONE))
				.wait(maxSeconds).check());
	}
}
