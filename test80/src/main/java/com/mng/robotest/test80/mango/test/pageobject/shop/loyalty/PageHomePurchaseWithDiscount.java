package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomePurchaseWithDiscount extends WebdrvWrapp {

	WebDriver driver;

	String idBlockLoyalty = "loyaltyLoyaltySpace";
	String xpathButtonPurchaseNow = "//a[text()='Comprar ahora']";

	private PageHomePurchaseWithDiscount(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomePurchaseWithDiscount getNewInstance(WebDriver driver) {
		return (new PageHomePurchaseWithDiscount(driver));
	}
	
	public boolean checkIsPage() {
		return (WebdrvWrapp.isElementVisible(driver, By.id(idBlockLoyalty)));
	}

	public boolean areVisibleButtonPurchaseNow() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathButtonPurchaseNow), 2));
	}
}