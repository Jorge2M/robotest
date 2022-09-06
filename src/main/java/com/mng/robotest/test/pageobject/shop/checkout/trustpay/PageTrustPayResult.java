package com.mng.robotest.test.pageobject.shop.checkout.trustpay;

import org.openqa.selenium.WebElement;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrustPayResult extends PageBase {
	
	private static final String XPATH_HEADER = "//h2[@id='stageheader']";
	private static final String XPATH_BUTTON_CONTINUE = "//input[@type='submit' and @value='continue']";
	
	public String getHeaderText() {
		WebElement titleNws = getElement(XPATH_HEADER);
		if (titleNws!=null) {
			return getElement(XPATH_HEADER).getText();
		}
		return "";
	}
	
	public boolean headerContains(String textContained) {
		return getHeaderText().contains(textContained);
	}
	
	public boolean isPresentButtonContinue() {
		return state(Present, XPATH_BUTTON_CONTINUE).check();
	}

	public void clickButtonContinue() {
		click(XPATH_BUTTON_CONTINUE).exec();
	}
}
