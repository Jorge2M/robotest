package com.mng.robotest.domains.compra.payments.paypal.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalLogin extends PageBase {
	
	private static final String XPATH_CONTAINER = "//div[@class[contains(.,'contentContainer')]]";
	private static final String XPATH_INPUT_LOGIN = XPATH_CONTAINER + "//input[@id='email']";
	private static final String XPATH_INPUT_PASSWORD = XPATH_CONTAINER + "//input[@id='password' and not(@disabled='disabled')]";
	private static final String XPATH_INICIAR_SESION_BUTTON = XPATH_CONTAINER + "//div/button[@id='btnLogin' or @id='login']";
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_INPUT_PASSWORD).wait(seconds).check();
	}
	
	public void inputUserAndPassword(String userMail, String password) {
		waitLoadPage(); //For avoid StaleElementReferenceException
		sendKeysWithRetry(userMail, By.xpath(XPATH_INPUT_LOGIN), 2, driver);
		if (state(Visible, XPATH_INPUT_PASSWORD).check()) {
			getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
		} else {
			new PagePaypalLogin().clickIniciarSesion();
			if (state(Visible, XPATH_INPUT_PASSWORD).wait(3).check()) {
				getElement(XPATH_INPUT_PASSWORD).sendKeys(password);
			}
		}
	}

	public void clickIniciarSesion() {
		click(XPATH_INICIAR_SESION_BUTTON).exec();
	}
}
