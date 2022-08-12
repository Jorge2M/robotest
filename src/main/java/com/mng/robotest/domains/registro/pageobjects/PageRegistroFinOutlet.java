package com.mng.robotest.domains.registro.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageRegistroFinOutlet extends PageBase {

	private static final String XPATH_BUTTON_IR_SHOPPING = "//div[@class[contains(.,'ir-de-shopping')]]/input[@type='submit']";
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_BUTTON_IR_SHOPPING))
				.wait(maxSeconds).check());
	}
	
	public void clickIrDeShopping() {
		waitForPageLoaded(driver); //Para evitar StaleElement Exception
		click(By.xpath(XPATH_BUTTON_IR_SHOPPING)).type(javascript).exec();
		if (isVisibleButtonIrDeShopping()) {
			click(By.xpath(XPATH_BUTTON_IR_SHOPPING)).exec();
		}
	}
	
	public boolean isVisibleButtonIrDeShopping() {
		return (state(Visible, By.xpath(XPATH_BUTTON_IR_SHOPPING), driver).check());
	}
}
