package com.mng.robotest.tests.domains.compra.payments.klarna.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalInputPersonnumberKlarna extends PageBase {

	private static final String XP_INPUT_PERSON_NUMBER = "//input[@id='invoice_kp-purchase-approval-form-national-identification-number']";
	private static final String XP_INPUT_FECHA_NACIMIENTO = "//input[@id='invoice_kp-purchase-approval-form-date-of-birth']";
	private static final String XP_BUTTON_CONF = "//button[@id='invoice_kp-purchase-approval-form-continue-button']";
	private static final String XP_SECOND_BUTTON_CONF = "//div[@id='identification-dialog__footer-button-wrapper']//button";
	
	private String getXPathInputPersonNumber() {
		return XP_INPUT_PERSON_NUMBER + " | " + XP_INPUT_FECHA_NACIMIENTO;
	}
	
	public boolean isModal(int seconds) {
		return state(Visible, getXPathInputPersonNumber()).wait(seconds).check();
	}
	
	public void inputPersonNumber(String personnumber) {
		getElement(getXPathInputPersonNumber()).sendKeys(personnumber);
	}
	
	public void clickButtonConf() {
		click(XP_BUTTON_CONF).exec();
		if (state(Visible, XP_SECOND_BUTTON_CONF).wait(2).check()) {
			click(XP_SECOND_BUTTON_CONF).exec();
		}
	}
}
