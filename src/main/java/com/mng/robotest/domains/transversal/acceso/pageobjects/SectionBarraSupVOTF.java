package com.mng.robotest.domains.transversal.acceso.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class SectionBarraSupVOTF extends PageBase {

	public static final String TITLE_USERNAME = "USERNAME: "; 
	private static final String XPATH_BARRA = "//div[@class[contains(.,'barraTele')]]";
	
	public boolean isPresentUsuario(String usuarioVOTF) {
		String usuarioLit = TITLE_USERNAME + usuarioVOTF;
		return (
			state(Present, XPATH_BARRA).check() &&
			getElement(XPATH_BARRA).getText().toLowerCase().contains(usuarioLit.toLowerCase()));
	}
	
}
