package com.mng.robotest.tests.domains.compra.payments.yandex.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageYandex1rst extends PageBase {

	private static final String XP_INPUT_EMAIL = "//input[@name='cps_email']";
	private static final String XP_BUTTON_CONTINUE = "//div[@class[contains(.,'payment-submit')]]//button";
	private static final String XP_INPUT_TELEFONO = "//input[@name[contains(.,'phone')]]";
	private static final String XP_RETRY_BUTTON = "//span[@class='button__text' and text()[contains(.,'Повторить попытку')]]";

	public boolean isPage() {
		return (driver.getTitle().toLowerCase().contains("yandex"));
	}

	public boolean isValueEmail(String emailUsr) {
		String valueEmail = getValueInputEmail();
		return (valueEmail.contains(emailUsr));
	}
	
	public String getValueInputEmail() {
		return getElement(XP_INPUT_EMAIL).getAttribute("value");
	}

	public void clickContinue() {
		click(XP_BUTTON_CONTINUE).exec();
	}

	public void inputTelefono(String telefono) {
		getElement(XP_INPUT_TELEFONO).clear();
		sendKeysWithRetry(telefono, By.xpath(XP_INPUT_TELEFONO), 2, driver);
	}

	public boolean retryButtonExists() {
		return state(PRESENT, XP_RETRY_BUTTON).check();
	}

	public void clickOnRetry() {
		click(XP_RETRY_BUTTON).waitLoadPage(2).exec();
	}
}
