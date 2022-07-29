package com.mng.robotest.domains.registro.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroFin extends PageObjTM {

	private static final String xpathButtonIrShopping = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";

	public PageRegistroFin(WebDriver driver) {
		super(driver);
	}
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(xpathButtonIrShopping))
				.wait(maxSeconds).check());
	}
	
	public void clickIrDeShopping() {
		waitForPageLoaded(driver); //Para evitar StaleElement Exception
		click(By.xpath(xpathButtonIrShopping)).type(javascript).exec();
		if (isVisibleButtonIrDeShopping()) {
			click(By.xpath(xpathButtonIrShopping)).exec();
		}
	}
	
	public boolean isVisibleButtonIrDeShopping() {
		return (state(Visible, By.xpath(xpathButtonIrShopping), driver).check());
	}
}
