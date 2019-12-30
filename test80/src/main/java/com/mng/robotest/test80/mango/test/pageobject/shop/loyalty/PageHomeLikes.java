package com.mng.robotest.test80.mango.test.pageobject.shop.loyalty;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageHomeLikes extends WebdrvWrapp {

	WebDriver driver;
	
	String idLoyaltySpace = "loyaltyLoyaltySpace";
	String xpathPoints = "//*[@class[contains(.,'user-total-likes')] or @class[contains(.,'enough-likes')]]";
	String xpathBlockExchange = "//ul[@class='cards-list']/li";
	String xpathButtonPurchaseWithDiscount = "//span[text()='Compra con descuento']";
	String xpathButtonDonateLikes = "//span[contains(text(), 'Donar mis Likes')]";
	String xpathButtonConseguirPor1200Likes = "//span[contains(text(), 'Conseguir')]";
	
	private PageHomeLikes(WebDriver driver) {
		this.driver = driver;
	}
	
	public static PageHomeLikes getNew(WebDriver driver) {
		return (new PageHomeLikes(driver));
	}
	
	public boolean checkIsPageUntil(int maxSecondsWait) {
		return (WebdrvWrapp.isElementVisibleUntil(driver, By.id(idLoyaltySpace), maxSecondsWait));
	}
	
	public int getPoints() {
		if (isElementPresent(driver, By.xpath(xpathPoints))) {
			String textPoints = driver.findElement(By.xpath(xpathPoints)).getText();
			Pattern pattern = Pattern.compile(" [0-9,.]+ ");
			Matcher matcher = pattern.matcher(textPoints);
			if (matcher.find()) {
				return Integer.valueOf(
					matcher.group(0).
					trim().
					replace(",", "").
					replace(".", ""));
			}
		}
		return 0;
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