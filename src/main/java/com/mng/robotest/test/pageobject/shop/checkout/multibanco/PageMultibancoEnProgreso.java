package com.mng.robotest.test.pageobject.shop.checkout.multibanco;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMultibancoEnProgreso extends PageBase {
	
	private static final String XPATH_CABECERA_EN_PROCESO = "//h2[text()[contains(.,'Pagamento em progresso')]]";
	private static final String XPATH_BUTTON_NEXT_STEP = "//input[@id='mainSubmit' and @type='submit']";
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_CABECERA_EN_PROCESO)).wait(maxSeconds).check());
	}
	
	public boolean isButonNextStep() {
		return (state(Present, By.xpath(XPATH_BUTTON_NEXT_STEP)).check());
	}

	public void clickButtonNextStep() {
		click(By.xpath(XPATH_BUTTON_NEXT_STEP)).exec();
	}
}
