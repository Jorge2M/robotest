package com.mng.robotest.tests.domains.compra.payments.paypal.pageobjects;

import org.openqa.selenium.By;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PagePaypalLogin extends PageBase {
	
	private static final String XP_CONTAINER = "//div[@class[contains(.,'contentContainer')]]";
	private static final String XP_INPUT_LOGIN = XP_CONTAINER + "//input[@id='email']";
	private static final String XP_INPUT_PASSWORD = XP_CONTAINER + "//input[@id='password' and not(@disabled='disabled')]";
	private static final String XP_INICIAR_SESION_BUTTON = XP_CONTAINER + "//div/button[@id='btnLogin' or @id='login']";
	
	public boolean isPage() {
		return isPageUntil(0);
	}
	public boolean isPageUntil(int seconds) {
		return state(Present, XP_INPUT_PASSWORD).wait(seconds).check();
	}
	
	public void inputUserAndPassword(String userMail, String password) {
		waitLoadPage(); //For avoid StaleElementReferenceException
		sendKeysWithRetry(userMail, By.xpath(XP_INPUT_LOGIN), 2, driver);
		if (state(Visible, XP_INPUT_PASSWORD).check()) {
			getElement(XP_INPUT_PASSWORD).sendKeys(password);
		} else {
			new PagePaypalLogin().clickIniciarSesion();
			if (state(Visible, XP_INPUT_PASSWORD).wait(3).check()) {
				getElement(XP_INPUT_PASSWORD).sendKeys(password);
			}
		}
	}

	public void clickIniciarSesion() {
		click(XP_INICIAR_SESION_BUTTON).exec();
	}
}
