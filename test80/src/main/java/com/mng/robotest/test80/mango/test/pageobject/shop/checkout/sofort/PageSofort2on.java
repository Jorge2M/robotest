package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSofort2on extends PageObjTM {
	
	private static String XPathButtonAcceptCookies = "//div[@id='Modal']//div[@id='modal-button-container']//button[@data-url[contains(.,'accept-all')]]";
	private static String XPathSelectPaises = "//select[@id[contains(.,'Country')]]";
	private static String XPathInputBankCode = "//input[@id[contains(.,'BankCodeSearch')]]";
	private static String XPathSubmitButton = "//form//button[@class[contains(.,'primary')]]";
	
	public PageSofort2on(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPathSelectPaises)).wait(maxSeconds).check());
	}
	
	public void acceptCookies() {
		By byAccept = By.xpath(XPathButtonAcceptCookies);
		if (state(State.Visible, byAccept).check()) {
			click(byAccept).exec();
		}
	}
	
	public void selectPais(String pais) {
		new Select(driver.findElement(By.xpath(XPathSelectPaises))).selectByValue(pais);
	}	
	
	public void inputBankcode(String bankCode) {
		driver.findElement(By.xpath(XPathInputBankCode)).sendKeys(bankCode);
	}

	public void clickSubmitButtonPage3() {
		click(By.xpath(XPathSubmitButton)).exec();
	}
}
