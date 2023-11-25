package com.mng.robotest.tests.domains.compra.payments.kredikarti.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.compra.pageobjects.pci.SecTarjetaPciInIframe;

public class SecKrediKarti extends SecTarjetaPciInIframe {

	private static final String XP_CAPA_PAGO_PLAZO_MOBIL = "//table[@class[contains(.,'installment')]]";
	private static final String XP_RADIO_PAGOO_PLAZO_MOBIL = XP_CAPA_PAGO_PLAZO_MOBIL + "//div[@class[contains(.,'installment-checkbox')]]";
	private static final String XP_CAPA_PAGO_PLAZO_DESKTOP = "//div[@class[contains(.,'installments-content')]]"; 
	private static final String XP_RADIO_PAGO_PLAZO_DESKTOP = XP_CAPA_PAGO_PLAZO_DESKTOP + "//input[@type='radio' and @name='installment']";
	
	private String getXPathCapaPagoPlazo() {
		switch (channel) {
		case desktop:
			return XP_CAPA_PAGO_PLAZO_DESKTOP;
		default:
		case mobile:
			return XP_CAPA_PAGO_PLAZO_MOBIL;
		}
	}
	
	private String getXPathRadioPagoPlazo() {
		switch (channel) {
		case desktop:
			return XP_RADIO_PAGO_PLAZO_DESKTOP;
		default:
		case mobile:
			return XP_RADIO_PAGOO_PLAZO_MOBIL;
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

	public void clickRadioPagoAPlazo(int numRadio) {
		goToIframe();
		String xpathRadio = getXPathRadioPagoAPlazo(numRadio);
		state(Visible, xpathRadio).wait(1).check();
		click(xpathRadio).exec();
		click(xpathRadio).exec();
		leaveIframe();
	}
}
