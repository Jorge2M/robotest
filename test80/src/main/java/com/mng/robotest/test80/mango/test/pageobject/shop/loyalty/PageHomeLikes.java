package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

public class PageHomeLikes {

	WebDriver driver;
	
	String idWrapperLoyalty = "mngLoyalty";
	String xpathBlockExchange = "//ul[@class='cards-list']/li";
	
	private PageHomeLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeLikes getNewInstance(WebDriver driver) {
		return (new PageHomeLikes(driver));
	}
	
	public boolean checkIsPage() {
		return (WebdrvWrapp.isElementVisible(driver, By.id(idWrapperLoyalty)));
	}
	
	public boolean areVisibleBlocksExchangeLikes() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathBlockExchange), 2));
	}
}