package com.mng.robotest.tests.domains.compra.payments.giropay.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageGiropay1rst extends PageBase {
	
	private static final String XP_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XP_CABECERA_STEP = "//h2[@id[contains(.,'stageheader')]]";
	private static final String XP_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XP_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @id='mainSubmit']";
	private static final String XP_ICONO_GIROPAY_MOBIL = XP_LIST_OF_PAYMENTS + "//input[@class[contains(.,'giropay')]]";
	private static final String XP_ICONO_GIROPAY_DESKTOP = XP_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'giropay')]]";
	
	private String getXPathIconoGiropay() {
		if (channel.isDevice()) {
			return XP_ICONO_GIROPAY_MOBIL;
		}
		return XP_ICONO_GIROPAY_DESKTOP;
	}
	
	public boolean isPresentIconoGiropay() {
		return state(PRESENT, getXPathIconoGiropay()).check();
	}
	
	public boolean isPresentCabeceraStep() {
		return state(PRESENT, XP_CABECERA_STEP).check();
	}
	
	public boolean isPresentButtonPagoDesktopUntil(int seconds) {
		return state(PRESENT, XP_BUTTON_PAGO_DESKTOP).wait(seconds).check();
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
		click(XP_BUTTON_PAGO_DESKTOP).exec();
	}

	public void clickButtonContinueMobil() {
		click(XP_BUTTON_CONTINUE_MOBIL).exec();
	}
	
}
