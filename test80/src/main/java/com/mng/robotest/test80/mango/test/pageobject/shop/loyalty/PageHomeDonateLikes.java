package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeDonateLikes extends WebdrvWrapp {

	WebDriver driver;

	String idBlockLoyalty = "loyaltyLoyaltySpace";
	String xpathButtonsDonateLikes = "//button[contains(text(), 'Donar 200 Likes') or contains(text(), 'Donar 400 Likes') or contains(text(), 'Donar 800 Likes')]";

	private PageHomeDonateLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeDonateLikes getNewInstance(WebDriver driver) {
		return (new PageHomeDonateLikes(driver));
	}
	
	public boolean checkIsPage() {
		return (WebdrvWrapp.isElementVisible(driver, By.id(idBlockLoyalty)));
	}

	public boolean areVisibleButtonsDonateLikes() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathButtonsDonateLikes), 2));
	}
}