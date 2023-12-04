package com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageAmexResult extends PageBase {

	private static final String XP_SECTION_OK = "//div[@class[contains(.,'code ok')]]";
	private static final String XP_CONTINUE_BUTTON = "//input[@class[contains(.,'btn-continue')]]";
	
	public PageAmexResult(WebDriver driver) {
		super(driver);
	}
	
	public boolean isResultOkUntil(int seconds) {
		return state(PRESENT, XP_SECTION_OK).wait(seconds).check();
	}
	
	public boolean isPresentContinueButton() {
		return state(PRESENT, XP_CONTINUE_BUTTON).check();
	}

	public void clickContinuarButton() {
		click(XP_CONTINUE_BUTTON).exec();
	}
}
