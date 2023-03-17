package com.mng.robotest.domains.compra.payments.yandex.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageYandex1rst extends PageBase {

	private static final String XPATH_INPUT_EMAIL = "//input[@name='cps_email']";
	private static final String XPATH_BUTTON_CONTINUE = "//div[@class[contains(.,'payment-submit')]]//button";
	private static final String XPATH_INPUT_TELEFONO = "//input[@name[contains(.,'phone')]]";
	private static final String XPATH_RETRY_BUTTON = "//span[@class='button__text' and text()[contains(.,'Повторить попытку')]]";

	public boolean isPage() {
		return (driver.getTitle().toLowerCase().contains("yandex"));
	}

	public boolean isValueEmail(String emailUsr) {
		String valueEmail = getValueInputEmail();
		return (valueEmail.contains(emailUsr));
	}
	
	public String getValueInputEmail() {
		return getElement(XPATH_INPUT_EMAIL).getAttribute("value");
	}

	public void clickContinue() {
		click(XPATH_BUTTON_CONTINUE).exec();
	}

	public void inputTelefono(String telefono) {
		getElement(XPATH_INPUT_TELEFONO).clear();
		sendKeysWithRetry(telefono, By.xpath(XPATH_INPUT_TELEFONO), 2, driver);
	}

	public boolean retryButtonExists() {
		return state(Present, XPATH_RETRY_BUTTON).check();
	}

	public void clickOnRetry() {
		click(XPATH_RETRY_BUTTON).waitLoadPage(2).exec();
	}
}
