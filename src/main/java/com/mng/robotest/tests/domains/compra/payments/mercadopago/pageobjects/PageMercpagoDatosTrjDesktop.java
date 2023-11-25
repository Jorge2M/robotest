package com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects;

import org.openqa.selenium.By;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjDesktop extends PageMercpagoDatosTrj {
	
	private static final String XP_VISA_ICON_NUM_TARJ = "//span[@id='paymentmethod-logo']";
	private static final String XP_BOTON_CONTINUAR = "//button[@id='submit']";
	
	@Override
	public boolean isPageUntil(int seconds) {
		return state(Visible, XP_INPUT_CVC).wait(seconds).check();
	}
	
	@Override
	public void sendCaducidadTarj(String fechaVencimiento) {
		getElement(XP_INPUT_FEC_CADUCIDAD).sendKeys(fechaVencimiento);
	}
	
	@Override
	public void sendCvc(String cvc) {
		sendKeysWithRetry(cvc, By.xpath(XP_INPUT_CVC), 3, driver);
	}
	
	public boolean isVisibleVisaIconUntil(int seconds) {
		return state(Visible, XP_VISA_ICON_NUM_TARJ).wait(seconds).check();
	}

	public void clickBotonForContinue() {
		click(XP_BOTON_CONTINUAR + " | " + XP_BOTON_PAGAR).exec();
	}
}
