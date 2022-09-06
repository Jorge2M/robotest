package com.mng.robotest.test.pageobject.shop.checkout.sofort;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSofort2on extends PageBase {
	
	private static final String XPATH_BUTTON_ACCEPT_COOKIES = "//div[@id='Modal']//div[@id='modal-button-container']//button[@data-url[contains(.,'accept-all')]]";
	private static final String XPATH_SELECT_PAISES = "//select[@id[contains(.,'Country')]]";
	private static final String XPATH_INPUT_BANK_CODE = "//input[@id[contains(.,'BankCodeSearch')]]";
	private static final String XPATH_SUBMIT_BUTTON = "//form//button[@class[contains(.,'primary')]]";
	
	public boolean isPageUntil(int maxSeconds) {
		return state(Present, XPATH_SELECT_PAISES).wait(maxSeconds).check();
	}
	
	public void acceptCookies() {
		By byAccept = By.xpath(XPATH_BUTTON_ACCEPT_COOKIES);
		if (state(State.Visible, byAccept).check()) {
			click(byAccept).exec();
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
