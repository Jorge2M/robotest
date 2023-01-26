package com.mng.robotest.domains.registro.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PagePoliticaPrivacidad extends PageBase {

	private static final String XPATH_CABECERA = "//h1[text()[contains(.,'Política de privacidad')]]";
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_CABECERA).wait(seconds).check();
	}
	

}
