package com.mng.robotest.tests.domains.registro.pageobjects;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;


public class PageRegistroAddressDataOutlet extends PageBase {

	private static final String XPATH_TITLE_ADDRESS_STEPS = "//div[@class[contains(.,'addressData')]]";
	private static final String XPATH_DESPLEGABLE_PAISES = "//select[@id[contains(.,'pais')]]";
	private static final String XPATH_BUTTON_FINALIZAR = "//div[@class[contains(.,'registerStepsModal')]]//div[@class='submitContent']//input[@type='submit']";
	private static final String XPATH_ERROR_INPUT_DIRECCION = "//div[@id[contains(.,'cfDir1ErrorLabel')]]";	
	
	private String getXPathOptionPaisSelected(String codigoPais) {
		return ("//select[@id[contains(.,'pais')]]/option[@selected='selected' and @value='" + codigoPais + "']");
	}

	public boolean isPageUntil(int secondsWait) {
		return state(Present, XPATH_TITLE_ADDRESS_STEPS).wait(secondsWait).check();
	}

	public boolean existsDesplegablePaises() {
		return state(Present, XPATH_DESPLEGABLE_PAISES).check();
	}

	public boolean isOptionPaisSelected(String codigoPais) {
		String xpathOptionPaisSelected = getXPathOptionPaisSelected(codigoPais);
		return state(Present, xpathOptionPaisSelected).check();
	}

	public void clickButtonFinalizar() {
		click(XPATH_BUTTON_FINALIZAR).exec();
	}

	public boolean isVisibleErrorInputDireccion() {
		return state(Visible, XPATH_ERROR_INPUT_DIRECCION).check();
	}
}
