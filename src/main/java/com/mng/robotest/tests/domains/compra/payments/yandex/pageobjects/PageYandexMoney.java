package com.mng.robotest.tests.domains.compra.payments.yandex.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageYandexMoney extends PageBase {

	public static final String URL_ACCESS = "https://demomoney.yandex.ru/shop.xml?scid=50215";
	private static final String XP_INPUT_PAYMENT_CODE = "//input[@id='dstAccount']";
	private static final String XP_INPUT_IMPORT = "//input[@id='amount']";
	private static final String XP_PAY_BUTTON = "//input[@class[contains(.,'b-form-button__input')]]";

	public void goToPage() {
		driver.get(URL_ACCESS);
	}
	
	public void goToPageInNewTabV(String titleTab) throws Exception {
		loadUrlInAnotherTabTitle(URL_ACCESS, titleTab, driver);
	}
	
	public void closeActualTabByTitle(String titleTab) {
		closeTabByTitle(titleTab, driver);
	}
	
	public void inputPaymentCode(String inputValue) {
		sendKeysWithRetry(inputValue, By.xpath(XP_INPUT_PAYMENT_CODE), 2, driver);
	}
	
	public void inputImport(String inputValue) {
		getElement(XP_INPUT_IMPORT).clear();
		sendKeysWithRetry(inputValue, By.xpath(XP_INPUT_IMPORT), 2, driver);
	}
	
	public boolean isVisibleInputPaymentCode() {
		return state(VISIBLE, XP_INPUT_PAYMENT_CODE).check();
	}
	
	public boolean isVisibleInputImport() {
		return state(VISIBLE, XP_INPUT_IMPORT).check();
	}

	public void clickPayButton() {
		click(XP_PAY_BUTTON).exec();
	}
}
