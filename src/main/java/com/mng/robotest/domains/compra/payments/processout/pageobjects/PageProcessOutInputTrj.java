package com.mng.robotest.domains.compra.payments.processout.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.beans.Pago;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageProcessOutInputTrj extends PageBase {

	private static final String XPATH_FORMULARIO_TRJ = "//div[@class[contains(.,'payment-section')]]";
	private static final String XPATH_INPUT_NAME_CARD = "//input[@id='card-name']";
	private static final String XPATH_INPUT_TRJ = "//input[@id='card-number']";
	private static final String XPATH_INPUT_EXPIRACION = "//input[@id='card-expiry']";
	private static final String XPATH_INPUT_CVC = "//input[@id='card-cvc']";
	private static final String XPATH_BUTTON_PAGO = "//a[@class[contains(.,'form-submit')]]";
	
	public boolean checkIsPage() {
		return state(Visible, XPATH_FORMULARIO_TRJ).check();
	}
	
	public void inputDataTrj(Pago pago) {
		getElement(XPATH_INPUT_NAME_CARD).sendKeys("Jorge");
		getElement(XPATH_INPUT_TRJ).sendKeys(pago.getNumtarj());
		getElement(XPATH_INPUT_EXPIRACION).sendKeys(pago.getMescad() + pago.getAnycad().substring(2,4));
		getElement(XPATH_INPUT_CVC).sendKeys(pago.getCvc());
	}
	
	public boolean isPresentButtonPago() {
		return state(Visible, XPATH_BUTTON_PAGO).check();
	}
	
	public void clickButtonPay() {
		click(XPATH_BUTTON_PAGO).exec();
	}
}
