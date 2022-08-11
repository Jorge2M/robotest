package com.mng.robotest.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalAvisoCambioPais extends PageBase {

	private static final String XPATH_MODAL = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	private static final String XPATH_BUTTON_CONF_CAMBIO = XPATH_MODAL + "//button[@name='continue']";

	public boolean isVisibleUntil(int maxSeconds) {
		By byModal = By.xpath(XPATH_MODAL);
		return (state(Visible, byModal).wait(maxSeconds).check());
	}

	public boolean isInvisibleUntil(int maxSeconds) {
		By byModal = By.xpath(XPATH_MODAL);
		return (state(Invisible, byModal).wait(maxSeconds).check());
	}

	public void clickConfirmarCambio() {
		waitForPageLoaded(driver);
		By byConfirmar = By.xpath(XPATH_BUTTON_CONF_CAMBIO);
		moveToElement(byConfirmar, driver);
		click(byConfirmar).exec();
	}
}
