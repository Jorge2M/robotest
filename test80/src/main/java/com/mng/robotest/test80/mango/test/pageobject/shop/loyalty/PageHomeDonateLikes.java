package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeDonateLikes extends PageObjTM {

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
    	
    	public By getBy() {
    		return By.xpath("//button[contains(.,'" + numLikes + "')]");
    	}
	}
	
	final static String idBlockLoyalty = "loyaltyLoyaltySpace";
	final static String xpathIconOperationDone = "//span[@class='icon-outline-done']";

	private PageHomeDonateLikes(WebDriver driver) {
		super(driver);
	}
	
	public static PageHomeDonateLikes getNew(WebDriver driver) {
		return (new PageHomeDonateLikes(driver));
	}
	
	public boolean checkIsPage() {
		return (state(Visible, By.id(idBlockLoyalty)).check());
	}
	
	public boolean isVisible(ButtonLikes buttonLikes) {
		return (state(Visible, buttonLikes.getBy()).check());
	}
	
	public void clickButton(ButtonLikes buttonLikes) {
		click(buttonLikes.getBy()).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int maxSeconds) {
		return (state(Visible, By.xpath(xpathIconOperationDone)).wait(maxSeconds).check());
	}
}