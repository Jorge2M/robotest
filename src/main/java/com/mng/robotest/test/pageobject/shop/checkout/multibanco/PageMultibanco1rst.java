package com.mng.robotest.test.pageobject.shop.checkout.multibanco;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMultibanco1rst extends PageBase {
	
	private static final String TAG_EMAIL = "@TagEmail";
	private static final String XPATH_LIST_OF_PAYMENTS = "//ul[@id='paymentMethods']";
	private static final String XPATH_CABECERA_STEP = "//h2[@id[contains(.,'stageheader')]]";
	private static final String XPATH_INPUT_ICONO_MULTIBANCO = "//input[@type='submit' and @name='brandName']";
	private static final String XPATH_BUTTON_PAGO_DESKTOP = "//input[@class[contains(.,'paySubmit')] and @type='submit']";
	private static final String XPATH_BUTTON_CONTINUE_MOBIL = "//input[@type='submit' and @value='continuar']";
	private static final String XPATH_INPUT_EMAIL_WITH_TAG = "//input[@id[contains(.,'multibanco')] and @value[contains(.,'" + TAG_EMAIL + "')]]";
	
	private String getXPathEntradaPago(String nombrePago) {
		if (channel.isDevice()) {
			return (XPATH_LIST_OF_PAYMENTS + "//input[@class[contains(.,'" + nombrePago.toLowerCase() + "')]]");
		}
		return (XPATH_LIST_OF_PAYMENTS + "/li[@data-variant[contains(.,'" + nombrePago.toLowerCase() + "')]]");
	}
	
	private String getXPathButtonContinuePay() {
		if (channel.isDevice()) {
			return XPATH_BUTTON_CONTINUE_MOBIL;
		}
		return XPATH_BUTTON_PAGO_DESKTOP;
	}
	
	private String getXPathInputEmail(String email) {
		return XPATH_INPUT_EMAIL_WITH_TAG.replace(TAG_EMAIL, email);
	}
	
	public boolean isPresentEntradaPago(String nombrePago) {
		String xpathPago = getXPathEntradaPago(nombrePago);
		return state(Present, xpathPago).check();
	}
	
	public boolean isPresentCabeceraStep() {
		return state(Present, XPATH_CABECERA_STEP).check();
	}
	
	public boolean isPresentButtonPagoDesktop() {
		return state(Present, XPATH_BUTTON_PAGO_DESKTOP).check();
	}
	
	public boolean isPresentEmailUsr(String emailUsr) {
		String xpathEmail = getXPathInputEmail(emailUsr);
		return state(Present, xpathEmail).check();
	}

	public void continueToNextPage() {
		//En el caso de móvil hemos de seleccionar el icono de banco para visualizar el botón de continue
		if (channel.isDevice()) {
			if (!state(Visible, getXPathButtonContinuePay()).check()) {
				clickIconoBanco();
			}
		}
		clickButtonContinuePay();
	}

	public void clickIconoBanco() {
		click(XPATH_INPUT_ICONO_MULTIBANCO).exec();
	}

	public void clickButtonContinuePay() {
		click(getXPathButtonContinuePay()).exec();
	}

}
