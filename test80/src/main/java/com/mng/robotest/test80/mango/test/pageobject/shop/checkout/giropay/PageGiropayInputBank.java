package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageGiropayInputBank extends WebdrvWrapp {

	private static final String XPathInputBank = "//input[@placeholder[contains(.,'Bankname')]]";
	private static final String XPathLinkBakOption = "//a[@class='ui-menu-item-wrapper']";
	private static final String XPathButtonConfirm = "//div[@id='idtoGiropayDiv']/input"; 
	
	public static boolean checkIsPage(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathInputBank));
	}
	
	public static void inputBank(String idBank, WebDriver driver) throws Exception {
		WebElement input = driver.findElement(By.xpath(XPathInputBank));
		input.clear();
		input.sendKeys(idBank);
		isElementVisibleUntil(driver, By.xpath(XPathLinkBakOption), 1);
		clickAndWaitLoad(driver, By.xpath(XPathLinkBakOption));
	}
	
	public static void confirm(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathButtonConfirm));
	}
}
