package com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PageQiwiInputTlfn extends PageBase {

	private static final String XPATH_INPUT_PHONE = "//input[@id='QIWIMobilePhone']";
	private static final String XPATH_LINK_ACEPTAR = "//input[@name='Submit_Card_1' and not(@disabled)]";
	
	public void inputQiwiPhone(String phone) {
		getElement(XPATH_INPUT_PHONE).sendKeys(phone);
	}
	
	public boolean isPresentInputPhone() {
		return state(Present, XPATH_INPUT_PHONE).check();
	}	
	
	public boolean isVisibleLinkAceptar(int maxSeconds) {
		return state(Visible, XPATH_LINK_ACEPTAR).wait(maxSeconds).check();
	}
	
	public void clickLinkAceptar() {
		click(XPATH_LINK_ACEPTAR).exec();
	}
}