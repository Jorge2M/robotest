package com.mng.robotest.domains.compra.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecDireccionEnvioDesktop extends PageBase {

	private static final String XPATH_SECTION = "//micro-frontend[@id='checkoutDeliveryAddressDesktop']";
	private static final String XPATH_EDIT_DIRECCION_BUTTON = XPATH_SECTION + "//button";
	private static final String XPATH_NOMBRE_ENVIO = XPATH_SECTION + "//div[@class[contains(.,'tpavy')]]"; 
	private static final String XPATH_DIRECCION_ENVIO = "//*[@data-testid='checkout.delivery.address']";
	
	public void clickEditDireccion() {
		waitLoadPage(); //For avoid StaleElementReferenceException
		click(XPATH_EDIT_DIRECCION_BUTTON).waitLink(2).exec();
	}

	public String getTextNombreEnvio() {
		return getElement(XPATH_NOMBRE_ENVIO).getText();
	}
	
	public String getTextDireccionEnvio() {
		if (state(Present, XPATH_DIRECCION_ENVIO).check()) {
			return getElement(XPATH_DIRECCION_ENVIO).getText();
		}
		return "";
	}
}
