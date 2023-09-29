package com.mng.robotest.tests.domains.manto.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecCabecera extends PageBase {

	private static final String XPATH_LIT_TIENDA = "//td/span[@class='txt8BDis']";
	private static final String XPATH_BUTTON_SEL_TIENDA = "//input[@type='submit' and @value[contains(.,'Seleccionar tienda')]]";
	private static final String XPATH_LINK_VOLVER_MENU = "//a[text()[contains(.,'volver al menu')]] | //a/img[@src='/images/logo-mango.png']";

	public String getLitTienda() {
		if (state(Present, XPATH_LIT_TIENDA).check()) {
			return getElement(XPATH_LIT_TIENDA).getText();	
		}
		return "";
	}

	public void clickButtonSelTienda() {
		click(XPATH_BUTTON_SEL_TIENDA).exec();
	}

	public void clickLinkVolverMenuAndWait(int seconds) {
		click(XPATH_LINK_VOLVER_MENU).waitLink(seconds).exec();
		waitForPageLoaded(driver, seconds);
	}
}
