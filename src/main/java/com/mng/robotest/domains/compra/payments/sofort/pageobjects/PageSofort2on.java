package com.mng.robotest.domains.compra.payments.sofort.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSofort2on extends PageBase {
	
	private static final String XPATH_BUTTON_ACCEPT_COOKIES = "//div[@id='Modal']//div[@id='modal-button-container']//button[@data-url[contains(.,'accept-all')]]";
	private static final String XPATH_SELECT_PAISES = "//select[@id[contains(.,'Country')]]";
	private static final String XPATH_INPUT_BANK_CODE = "//input[@id[contains(.,'BankCodeSearch')]]";
	private static final String XPATH_SUBMIT_BUTTON = "//form//button[@class[contains(.,'primary')]]";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_SELECT_PAISES).wait(seconds).check();
	}
	
	public void acceptCookies() {
		if (state(State.Visible, XPATH_BUTTON_ACCEPT_COOKIES).check()) {
			click(XPATH_BUTTON_ACCEPT_COOKIES).exec();
		}
	}
	
	public void selectPais(String pais) {
		new Select(getElement(XPATH_SELECT_PAISES)).selectByValue(pais);
	}	
	
	public void inputBankcode(String bankCode) {
		getElement(XPATH_INPUT_BANK_CODE).sendKeys(bankCode);
	}

	public void clickSubmitButtonPage3() {
		click(XPATH_SUBMIT_BUTTON).exec();
	}
}
