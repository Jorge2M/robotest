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
		return (state(Visible, By.xpath(XPathModal), driver)
				.wait(maxSeconds).check());
	}

	public boolean isInvisibleUntil(int maxSeconds) {
		return (state(Invisible, By.xpath(XPathModal), driver)
				.wait(maxSeconds).check());
	}

	public void clickConfirmarCambio() {
		waitForPageLoaded(driver);
		moveToElement(By.xpath(XPathButtonConfCambio), driver);
		click(By.xpath(XPathButtonConfCambio)).exec();
	}
}
