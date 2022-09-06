package com.mng.robotest.test.pageobject.shop.checkout.mercadopago;

import org.openqa.selenium.By;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMercpagoDatosTrjDesktop extends PageMercpagoDatosTrj {
	
	static final String XPathVisaIconNumTarj = "//span[@id='paymentmethod-logo']";
	static final String XPathBotonContinuar = "//button[@id='submit']";
	static final String XPathDivBancoToClick = "//div[@class[contains(.,'select-wrapper')]]";
	//static final String XPathOpcionBanco = "//ul[@class[contains(.,'select')]]/li";
	
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
		return state(Visible, XPathVisaIconNumTarj).wait(maxSeconds).check();
	}

	public void clickBotonForContinue() {
		click(XPathBotonContinuar + " | " + XPATH_BOTON_PAGAR).exec();
	}
}
