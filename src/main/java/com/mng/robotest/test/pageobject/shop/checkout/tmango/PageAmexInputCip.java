package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexInputCip extends PageBase {

	private static final String XPATH_INPUT_CIP = "//input[@name='pin']";
	private static final String XPATH_ACCEPT_BUTTON = "//img[@src[contains(.,'daceptar.gif')]]/../../a";
	
	public PageAmexInputCip(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_INPUT_CIP))
				.wait(maxSeconds).check());
	}
	
	public void inputCIP(String CIP) {
		driver.findElement(By.xpath(XPATH_INPUT_CIP)).sendKeys(CIP);
	}

	public void clickAceptarButton() {
		click(By.xpath(XPATH_ACCEPT_BUTTON)).exec();
	}
}
