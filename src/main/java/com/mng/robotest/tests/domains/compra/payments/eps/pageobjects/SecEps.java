package com.mng.robotest.tests.domains.compra.payments.eps.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecEps extends PageBase {

	private static final String XP_INPUT_BANCO = "//div[@id='eps-bank-selector']";
	private static final String INI_XP_SELECT_OPTION_BANCO = XP_INPUT_BANCO + "//option[text()[contains(.,'";
	
	private String getXPathSelectOptionBanco(String banco) {
		return INI_XP_SELECT_OPTION_BANCO + banco + "')]]";
	}

	public void selectBanco(String nombreBanco) {
		String xpathSelectOptionBanco = getXPathSelectOptionBanco(nombreBanco);
		getElement(XP_INPUT_BANCO).click();
		getElement(xpathSelectOptionBanco).click();
		getElement(XP_INPUT_BANCO).click();
	}

	public boolean isBancoSeleccionado(String nombreBanco) {
		String xpath = getXPathSelectOptionBanco(nombreBanco);
		return state(VISIBLE, xpath).check();
	}

}