package com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageTrustPayResult extends PageBase {
	
	private static final String XP_HEADER = "//h2[@id='stageheader']";
	private static final String XP_BUTTON_CONTINUE = "//input[@type='submit' and @value='continue']";
	
	public String getHeaderText() {
		WebElement titleNws = getElement(XP_HEADER);
		if (titleNws!=null) {
			return getElement(XP_HEADER).getText();
		}
		return "";
	}
	
	public boolean headerContains(String textContained) {
		return getHeaderText().contains(textContained);
	}
	
	public boolean isPresentButtonContinue() {
		return state(PRESENT, XP_BUTTON_CONTINUE).check();
	}

	public void clickButtonContinue() {
		click(XP_BUTTON_CONTINUE).exec();
	}
}
