package com.mng.robotest.tests.domains.compra.payments.dotpay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageDotpay1rst extends PageBase {
	
	private static final String XP_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XP_INPUT_ICONO_DOTPAY = "//input[@type='submit' and @name='brandName']";
	private static final String XP_BUTTON_PAGO = "//input[@name='pay' and @type='submit']";
	
	private String getXPathEntradaPago(String nombrePago) {
		if (channel.isDevice()) {
			return (XP_LIST_OF_PAYMENTS + "/li/input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
		}
		return (XP_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return state(PRESENT, xpathPago).check();
	}
	
	public boolean isPresentCabeceraStep(String nombrePago) {
		String xpathCab = getXPathEntradaPago(nombrePago);
		return state(PRESENT, xpathCab).check();
	}

	public boolean isPresentButtonPago() {
		return state(PRESENT, XP_BUTTON_PAGO).check();
	}

	public void clickToPay() {
		if (channel.isDevice()) {
			click(XP_INPUT_ICONO_DOTPAY).exec();
		} else {
			click(XP_BUTTON_PAGO).exec();
		}
	}
}
