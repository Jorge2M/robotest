package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeLikes extends WebdrvWrapp {

	WebDriver driver;
	
	String idLoyaltySpace = "loyaltyLoyaltySpace";
	String xpathBlockExchange = "//ul[@class='cards-list']/li";
	String xpathButtonPurchaseWithDiscount = "//button[text()='Compra con descuento']";
	
	private PageHomeLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeLikes getNewInstance(WebDriver driver) {
		return (new PageHomeLikes(driver));
	}
	
	public boolean checkIsPage() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.id(idLoyaltySpace), 2));
	}
	
	public boolean areVisibleBlocksExchangeLikes() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathBlockExchange), 2));
	}

	public void clickPurchaseWithDiscount() throws Exception {
		clickAndWaitLoad(driver, By.xpath(xpathButtonPurchaseWithDiscount));
	}
}