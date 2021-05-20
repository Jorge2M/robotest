package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalAvisoCambioPais extends PageObjTM {

	private final static String XPathModal = "//div[@aria-labelledby[contains(.,'changeCountryModal')]]";
	private final static String XPathButtonConfCambio = XPathModal + "//button[@name='continue']";
	
	public ModalAvisoCambioPais(WebDriver driver) {
		super(driver);
	}

	public boolean isVisibleUntil(int maxSeconds) {
		By byModal = By.xpath(XPathModal);
		return (state(Visible, byModal).wait(maxSeconds).check());
	}

	public boolean isInvisibleUntil(int maxSeconds) {
		By byModal = By.xpath(XPathModal);
		return (state(Invisible, byModal).wait(maxSeconds).check());
	}

	public void clickConfirmarCambio() {
		waitForPageLoaded(driver);
		By byConfirmar = By.xpath(XPathButtonConfCambio);
		moveToElement(byConfirmar, driver);
		click(byConfirmar).exec();
	}
}
