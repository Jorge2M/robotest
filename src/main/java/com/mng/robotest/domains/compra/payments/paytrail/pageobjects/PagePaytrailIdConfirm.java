package com.mng.robotest.domains.compra.payments.paytrail.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.transversal.PageBase;

public class PagePaytrailIdConfirm extends PageBase {
	
	private static final String XPATH_INPUT_ID = "//input[@name[contains(.,'PMTCONNB')]]";
	private static final String XPATH_BUTTON_CONFIRMAR = "//input[@name[contains(.,'SAVEBTN')]]";
	
	public boolean isPage() {
		return state(Present, XPATH_INPUT_ID).check();
	}
	
	public void inputIdConfirm(String idConfirm) {
		getElement(XPATH_INPUT_ID).clear();
		getElement(XPATH_INPUT_ID).sendKeys(idConfirm);
	}

	public void clickConfirmar() {
		click(XPATH_BUTTON_CONFIRMAR).exec();
	}
}
