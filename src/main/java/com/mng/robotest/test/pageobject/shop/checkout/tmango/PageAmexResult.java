package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexResult extends PageBase {

	private static final String XPATH_SECTION_OK = "//div[@class[contains(.,'code ok')]]";
	private static final String XPATH_CONTINUE_BUTTON = "//input[@class[contains(.,'btn-continue')]]";
	
	public PageAmexResult(WebDriver driver) {
		super(driver);
	}
	
	public boolean isResultOkUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_SECTION_OK))
				.wait(maxSeconds).check());
	}
	
	public boolean isPresentContinueButton() {
		return (state(Present, By.xpath(XPATH_CONTINUE_BUTTON)).check());
	}

	public void clickContinuarButton() {
		click(By.xpath(XPATH_CONTINUE_BUTTON)).exec();
	}
}
