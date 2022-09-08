package com.mng.robotest.test.pageobject.shop.checkout.dotpay;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDotpayAcceptSimulation extends PageBase {
	
	private static final String XPATH_ENCABEZADO = "//h1[text()[contains(.,'Simulation of payment')]]";
	private static final String XPATH_RED_BUTTON_ACEPTAR = "//input[@id='submit_success' and @type='submit']";

	public boolean isPage(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_ENCABEZADO)).wait(maxSeconds).check());
	}
	
	public boolean isPresentRedButtonAceptar() {
		return (state(Present, By.xpath(XPATH_RED_BUTTON_ACEPTAR)).check());
	}

	public void clickRedButtonAceptar() {
		click(By.xpath(XPATH_RED_BUTTON_ACEPTAR)).exec();
	}
}
