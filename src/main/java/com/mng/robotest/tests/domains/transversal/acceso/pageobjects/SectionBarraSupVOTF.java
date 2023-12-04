package com.mng.robotest.tests.domains.transversal.acceso.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SectionBarraSupVOTF extends PageBase {

	public static final String TITLE_USERNAME = "USERNAME: "; 
	private static final String XP_BARRA = "//div[@class[contains(.,'barraTele')]]";
	
	public boolean isPresentUsuario(String usuarioVOTF) {
		String usuarioLit = TITLE_USERNAME + usuarioVOTF;
		return (
			state(PRESENT, XP_BARRA).check() &&
			getElement(XP_BARRA).getText().toLowerCase().contains(usuarioLit.toLowerCase()));
	}
	
}
