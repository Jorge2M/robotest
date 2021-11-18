package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.d3d;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;


public class PageD3DLogin extends PageObjTM {
	
	static String XPathInputUser = "//input[@id='username']";
	static String XPathInputPassword = "//input[@id='password']";
	static String XPathButtonSubmit = "//input[@class[contains(.,'button')] and @type='submit']";
	
	public PageD3DLogin(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPageUntil(int maxSecondsToWait) {
		return (titleContainsUntil(driver, "3D Authentication", maxSecondsToWait));
	}
	
	public void inputUserPassword(String user, String password) {
		driver.findElement(By.xpath(XPathInputUser)).sendKeys(user);
		driver.findElement(By.xpath(XPathInputPassword)).sendKeys(password);
	}
		   
	public void clickButtonSubmit() {
		click(By.xpath(XPathButtonSubmit)).type(TypeClick.javascript).exec();
	}
}
