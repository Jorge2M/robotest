package com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PagePaytrailIdConfirm extends PageBase {
	
	private static final String XP_INPUT_ID = "//input[@name[contains(.,'PMTCONNB')]]";
	private static final String XP_BUTTON_CONFIRMAR = "//input[@name[contains(.,'SAVEBTN')]]";
	
	public boolean isPage() {
		return state(PRESENT, XP_INPUT_ID).check();
	}
	
	public void inputIdConfirm(String idConfirm) {
		getElement(XP_INPUT_ID).clear();
		getElement(XP_INPUT_ID).sendKeys(idConfirm);
	}

	public void clickConfirmar() {
		click(XP_BUTTON_CONFIRMAR).exec();
	}
}
