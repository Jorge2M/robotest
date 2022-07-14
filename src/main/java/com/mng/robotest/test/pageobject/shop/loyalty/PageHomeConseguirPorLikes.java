package com.mng.robotest.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPorLikes extends PageObjTM {

	private static final String XPATH_BUTTON_LIKES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XPATH_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	private PageHomeConseguirPorLikes(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomeConseguirPorLikes getNew(WebDriver driver) {
		return (new PageHomeConseguirPorLikes(driver));
	}
	
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
