package com.mng.robotest.domains.compra.payments.klarna.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalInputPersonnumberKlarna extends PageBase {

	private static final String XPATH_INPUT_PERSON_NUMBER = "//input[@id='invoice_kp-purchase-approval-form-national-identification-number']";
	private static final String XPATH_INPUT_FECHA_NACIMIENTO = "//input[@id='invoice_kp-purchase-approval-form-date-of-birth']";
	private static final String XPATH_BUTTON_CONF = "//button[@id='invoice_kp-purchase-approval-form-continue-button']";
	private static final String XPATH_SECOND_BUTTON_CONF = "//div[@id='identification-dialog__footer-button-wrapper']//button";
	
	private String getXPathInputPersonNumber() {
		return XPATH_INPUT_PERSON_NUMBER + " | " + XPATH_INPUT_FECHA_NACIMIENTO;
	}
	
	public boolean isModal(int seconds) {
		return state(Visible, getXPathInputPersonNumber()).wait(seconds).check();
	}
	
	public void inputPersonNumber(String personnumber) {
		getElement(getXPathInputPersonNumber()).sendKeys(personnumber);
	}
	
	public void clickButtonConf() {
		click(XPATH_BUTTON_CONF).exec();
		if (state(Visible, XPATH_SECOND_BUTTON_CONF).wait(2).check()) {
			click(XPATH_SECOND_BUTTON_CONF).exec();
		}
	}
}
