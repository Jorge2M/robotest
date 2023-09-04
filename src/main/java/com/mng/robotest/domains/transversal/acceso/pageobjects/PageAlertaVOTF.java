package com.mng.robotest.domains.transversal.acceso.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;

public class PageAlertaVOTF extends PageBase {
	
	private static final String XPATH_CAPA_ALERTA = "//div[@class='alert']";
	private static final String XPATH_BUTTON_CONTINUAR = "//div[@class='alert']//span[@class='button']";
	
	public boolean isPage() {
		return state(Present, XPATH_CAPA_ALERTA).check();
	}
	 
	public void clickButtonContinuar() {
		click(XPATH_BUTTON_CONTINUAR).exec();
	}
}
