package com.mng.robotest.test.pageobject.shop.checkout;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecEps extends PageBase {

	private static final String XPATH_INPUT_BANCO = "//div[@id='eps-bank-selector']";
	private static final String INI_XPATH_SELECT_OPTION_BANCO = XPATH_INPUT_BANCO + "//option[text()[contains(.,'";
	
	private String getXPathSelectOptionBanco(String banco) {
		return INI_XPATH_SELECT_OPTION_BANCO + banco + "')]]";
	}

	public void selectBanco(String nombreBanco) {
		String xpathSelectOptionBanco = getXPathSelectOptionBanco(nombreBanco);
		getElement(XPATH_INPUT_BANCO).click();
		getElement(xpathSelectOptionBanco).click();
		getElement(XPATH_INPUT_BANCO).click();
	}

	public boolean isBancoSeleccionado(String nombreBanco) {
		String xpath = getXPathSelectOptionBanco(nombreBanco);
		return state(Visible, xpath).check();
	}

}