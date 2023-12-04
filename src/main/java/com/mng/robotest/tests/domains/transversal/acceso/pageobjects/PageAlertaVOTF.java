package com.mng.robotest.tests.domains.transversal.acceso.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageAlertaVOTF extends PageBase {
	
	private static final String XP_CAPA_ALERTA = "//div[@class='alert']";
	private static final String XP_BUTTON_CONTINUAR = "//div[@class='alert']//span[@class='button']";
	
	public boolean isPage() {
		return state(PRESENT, XP_CAPA_ALERTA).check();
	}
	 
	public void clickButtonContinuar() {
		click(XP_BUTTON_CONTINUAR).exec();
	}
}
