package com.mng.robotest.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjDesktop extends PageMercpagoDatosTrj {
	
	private static final String XPATH_VISA_ICON_NUM_TARJ = "//span[@id='paymentmethod-logo']";
	private static final String XPATH_BOTON_CONTINUAR = "//button[@id='submit']";
	
	@Override
	public boolean isPageUntil(int maxSeconds) {
		return state(Visible, XPATH_INPUT_CVC).wait(maxSeconds).check();
	}
	
	@Override
	public void sendCaducidadTarj(String fechaVencimiento) {
		getElement(XPATH_INPUT_FEC_CADUCIDAD).sendKeys(fechaVencimiento);
	}
	
	@Override
	public void sendCvc(String cvc) {
		sendKeysWithRetry(cvc, By.xpath(XPATH_INPUT_CVC), 3, driver);
	}
	
	public boolean isVisibleVisaIconUntil(int maxSeconds) {
		return state(Visible, XPATH_VISA_ICON_NUM_TARJ).wait(maxSeconds).check();
	}

	public void clickBotonForContinue() {
		click(XPATH_BOTON_CONTINUAR + " | " + XPATH_BOTON_PAGAR).exec();
	}
}
