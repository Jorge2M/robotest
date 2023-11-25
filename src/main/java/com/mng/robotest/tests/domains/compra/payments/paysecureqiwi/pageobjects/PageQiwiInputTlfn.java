package com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageQiwiInputTlfn extends PageBase {

	private static final String XP_INPUT_PHONE = "//input[@id='QIWIMobilePhone']";
	private static final String XP_LINK_ACEPTAR = "//input[@name='Submit_Card_1' and not(@disabled)]";
	
	public void inputQiwiPhone(String phone) {
		getElement(XP_INPUT_PHONE).sendKeys(phone);
	}
	
	public boolean isPresentInputPhone() {
		return state(Present, XP_INPUT_PHONE).check();
	}	
	
	public boolean isVisibleLinkAceptar(int seconds) {
		return state(Visible, XP_LINK_ACEPTAR).wait(seconds).check();
	}
	
	public void clickLinkAceptar() {
		click(XP_LINK_ACEPTAR).exec();
	}
}