package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageGiropayInputBank {

	private static final String XPathInputBank = "//input[@placeholder[contains(.,'Bankname')]]";
	private static final String XPathLinkBakOption = "//a[@class='ui-menu-item-wrapper']";
	private static final String XPathButtonConfirm = "//div[@id='idtoGiropayDiv']/input"; 
	
	public static boolean checkIsPage(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputBank), driver).check());
	}
	
	public static void inputBank(String idBank, WebDriver driver) {
		WebElement input = driver.findElement(By.xpath(XPathInputBank));
		input.clear();
		input.sendKeys(idBank);
		state(Visible, By.xpath(XPathLinkBakOption), driver).wait(1).check();
		click(By.xpath(XPathLinkBakOption), driver).exec();
	}
	
	public static void confirm(WebDriver driver) {
		click(By.xpath(XPathButtonConfirm), driver).exec();
	}
}
