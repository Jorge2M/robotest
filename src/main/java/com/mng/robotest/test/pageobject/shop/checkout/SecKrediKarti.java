package com.mng.robotest.test.pageobject.shop.checkout;

import com.mng.robotest.test.pageobject.shop.checkout.pci.SecTarjetaPciInIframe;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecKrediKarti extends SecTarjetaPciInIframe {

	private static final String XPATH_CAPA_PAGO_PLAZO_MOBIL = "//table[@class[contains(.,'installment')]]";
	private static final String XPATH_RADIO_PAGOO_PLAZO_MOBIL = XPATH_CAPA_PAGO_PLAZO_MOBIL + "//div[@class[contains(.,'installment-checkbox')]]";
	private static final String XPATH_CAPA_PAGO_PLAZO_DESKTOP = "//div[@class[contains(.,'installments-content')]]"; 
	private static final String XPATH_RADIO_PAGO_PLAZO_DESKTOP = XPATH_CAPA_PAGO_PLAZO_DESKTOP + "//input[@type='radio' and @name='installment']";
	
	private String getXPathCapaPagoPlazo() {
		switch (channel) {
		case desktop:
			return XPATH_CAPA_PAGO_PLAZO_DESKTOP;
		default:
		case mobile:
			return XPATH_CAPA_PAGO_PLAZO_MOBIL;
		}
	}
	
	private String getXPathRadioPagoPlazo() {
		switch (channel) {
		case desktop:
			return XPATH_RADIO_PAGO_PLAZO_DESKTOP;
		default:
		case mobile:
			return XPATH_RADIO_PAGOO_PLAZO_MOBIL;
		}
	}
	
	private String getXPathRadioPagoAPlazo(int numRadio) {
		return ("(" + getXPathRadioPagoPlazo() + ")[" + numRadio + "]");
	}

	public boolean isVisiblePagoAPlazoUntil(int seconds) {
		goToIframe();
		boolean result = state(Visible, getXPathCapaPagoPlazo()).wait(seconds).check();
		leaveIframe();
		return result;
	}

	public void clickRadioPagoAPlazo(int numRadio) throws Exception {
		goToIframe();
		String xpathRadio = getXPathRadioPagoAPlazo(numRadio);
		click(xpathRadio).exec();
		click(xpathRadio).exec();
		leaveIframe();
	}
}
