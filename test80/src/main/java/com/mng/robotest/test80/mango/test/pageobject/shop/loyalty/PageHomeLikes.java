package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeLikes extends WebdrvWrapp {

	WebDriver driver;
	
	String idLoyaltySpace = "loyaltyLoyaltySpace";
	String xpathBlockExchange = "//ul[@class='cards-list']/li";
	String xpathButtonPurchaseWithDiscount = "//span[text()='Compra con descuento']";
	String xpathButtonDonateLikes = "//span[contains(text(), 'Donar mis Likes')]";
	String xpathButtonConseguirPor1200Likes = "//span[contains(text(), 'Conseguir por 1.200 Likes')]";
	
	private PageHomeLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeLikes getNew(WebDriver driver) {
		return (new PageHomeLikes(driver));
	}
	
	public boolean checkIsPageUntil(int maxSecondsWait) {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.id(idLoyaltySpace), maxSecondsWait));
	}
	
	public boolean areVisibleBlocksExchangeLikes() {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(xpathBlockExchange), 2));
	}

	public void clickPurchaseWithDiscount() throws Exception {
		clickAndWaitLoad(driver, By.xpath(xpathButtonPurchaseWithDiscount));
	}

	public void clickDonateLikes() throws Exception {
		clickAndWaitLoad(driver, By.xpath(xpathButtonDonateLikes));
	}
	
	public void clickConseguirPor1200Likes() throws Exception {
		clickAndWaitLoad(driver, By.xpath(xpathButtonConseguirPor1200Likes));
	}
}