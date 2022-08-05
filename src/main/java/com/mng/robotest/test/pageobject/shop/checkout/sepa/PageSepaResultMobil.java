package com.mng.robotest.test.pageobject.shop.checkout.sepa;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSepaResultMobil extends PageObjTM {

	private static final String XPATH_BUTTON_PAY = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XPATH_STAGE_3HEADER = "//h2[@id='stageheader' and text()[contains(.,'3:')]]";

	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_STAGE_3HEADER)).check());
	}

	public void clickButtonPay() {
		click(By.xpath(XPATH_BUTTON_PAY)).exec();
	}
}
