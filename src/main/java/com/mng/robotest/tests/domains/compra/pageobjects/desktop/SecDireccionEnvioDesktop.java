package com.mng.robotest.tests.domains.compra.pageobjects.desktop;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecDireccionEnvioDesktop extends PageBase {

	private static final String XP_SECTION = "//micro-frontend[@id='checkoutDeliveryAddressDesktop']";
	private static final String XP_EDIT_DIRECCION_BUTTON = XP_SECTION + "//button";
	private static final String XP_NOMBRE_ENVIO = XP_SECTION + "//div[@class[contains(.,'tpavy')]]"; 
	private static final String XP_DIRECCION_ENVIO = "//*[@data-testid='checkout.delivery.address']";
	
	public void clickEditDireccion() {
		waitLoadPage(); //For avoid StaleElementReferenceException
		state(VISIBLE, XP_EDIT_DIRECCION_BUTTON).wait(3).check();
		click(XP_EDIT_DIRECCION_BUTTON).exec();
	}

	public String getTextNombreEnvio() {
		return getElement(XP_NOMBRE_ENVIO).getText();
	}
	
	public String getTextDireccionEnvio() {
		if (state(PRESENT, XP_DIRECCION_ENVIO).check()) {
			return getElement(XP_DIRECCION_ENVIO).getText();
		}
		return "";
	}
	
}
