package com.mng.robotest.tests.domains.compra.payments.sofort.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSofort2on extends PageBase {
	
	private static final String XP_BUTTON_ACCEPT_COOKIES = "//div[@id='Modal']//div[@id='modal-button-container']//button[@data-url[contains(.,'accept-all')]]";
	private static final String XP_SELECT_PAISES = "//select[@id[contains(.,'Country')]]";
	private static final String XP_INPUT_BANK_CODE = "//input[@id[contains(.,'BankCodeSearch')]]";
	private static final String XP_SUBMIT_BUTTON = "//form//button[@class[contains(.,'primary')]]";

	private String getXPathBankOption(String bank) {
		return "//input[@data-label='" + bank + "']";
	}
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XP_SELECT_PAISES).wait(seconds).check();
	}
	
	public void acceptCookies() {
		if (state(Visible, XP_BUTTON_ACCEPT_COOKIES).check()) {
			click(XP_BUTTON_ACCEPT_COOKIES).exec();
		}
	}
	
	public void selectPais(String pais) {
		new Select(getElement(XP_SELECT_PAISES)).selectByValue(pais);
	}	
	
	public void inputBankcode(String bankCode) {
		getElement(XP_INPUT_BANK_CODE).sendKeys(bankCode);
	}
	
	public void selectBank(String bank) {
		click(getXPathBankOption(bank)).exec();
	}

	public void clickSubmitButtonPage3() {
		click(XP_SUBMIT_BUTTON).exec();
	}
}
