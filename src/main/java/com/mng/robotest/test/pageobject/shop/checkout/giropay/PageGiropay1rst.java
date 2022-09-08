package com.mng.robotest.test.pageobject.shop.checkout.giropay;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageGiropay1rst extends PageBase {
	
	private static final String XPATH_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XPATH_CABECERA_STEP = "//h2[@id[contains(.,'stageheader')]]";
	private static final String XPATH_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XPATH_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XPATH_ICONO_GIROPAY_MOBIL = XPATH_LIST_OF_PAYMENTS + "//input[@class[contains(.,'giropay')]]";
	private static final String XPATH_ICONO_GIROPAY_DESKTOP = XPATH_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'giropay')]]";
	
	private String getXPathIconoGiropay() {
		if (channel.isDevice()) {
			return XPATH_ICONO_GIROPAY_MOBIL;
		}
		return XPATH_ICONO_GIROPAY_DESKTOP;
	}
	
	public boolean isPresentIconoGiropay() {
		return state(Present, getXPathIconoGiropay()).check();
	}
	
	public boolean isPresentCabeceraStep() {
		return state(Present, XPATH_CABECERA_STEP).check();
	}
	
	public boolean isPresentButtonPagoDesktopUntil(int maxSeconds) {
		return state(Present, XPATH_BUTTON_PAGO_DESKTOP).wait(maxSeconds).check();
	}

	public void clickIconoGiropay() {
		click(getXPathIconoGiropay()).exec();
	}

	public void clickButtonContinuePay() {
		if (channel.isDevice()) {
			clickButtonContinueMobil();
		} else {
			clickButtonPagoDesktop();
		}
	}

	public void clickButtonPagoDesktop() {
		click(XPATH_BUTTON_PAGO_DESKTOP).exec();
	}

	public void clickButtonContinueMobil() {
		click(XPATH_BUTTON_CONTINUE_MOBIL).exec();
	}
	
}
