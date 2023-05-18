package com.mng.robotest.domains.registro.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PagePoliticaPrivacidad extends PageBase {

	private static final String XPATH_CABECERA = 
			"//*[text()[contains(.,'Política de privacidad')] or text()[contains(.,'Privacy policy')]]";
	
	public boolean isPageUntil(int seconds) {
		return state(Present, XPATH_CABECERA).wait(seconds).check();
	}
	

}
