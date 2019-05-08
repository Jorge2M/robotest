package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeDonateLikes extends WebdrvWrapp {

	WebDriver driver;

	public enum ButtonLikes implements ElementPage {
		Button50likes(50), 
		Button200likes(200),
		Button800likes(1000);
		
    	int numLikes;
    	ButtonLikes(int numLikes) {
    		this.numLikes = numLikes;
    	}
    	
    	public int getNumLikes() {
    		return numLikes;
    	}
    	
    	public String getXPath() {
    		return "//button[contains(.,'" + numLikes + "')]";
    	}
	}
	
	final static String idBlockLoyalty = "loyaltyLoyaltySpace";
	final static String xpathIconOperationDone = "//span[@class='icon-outline-done']";

	private PageHomeDonateLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeDonateLikes getNew(WebDriver driver) {
		return (new PageHomeDonateLikes(driver));
	}
	
	public boolean checkIsPage() {
		return (WebdrvWrapp.isElementVisible(driver, By.id(idBlockLoyalty)));
	}
	
	public boolean isVisible(ButtonLikes buttonLikes) {
		return WebdrvWrapp.isElementInState(buttonLikes, StateElem.Visible, driver);
	}
	
	public void clickButton(ButtonLikes buttonLikes) throws Exception {
		WebdrvWrapp.clickAndWait(buttonLikes, driver);
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSecondsWait) {
		return WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathIconOperationDone), maxSecondsWait);
	}
}