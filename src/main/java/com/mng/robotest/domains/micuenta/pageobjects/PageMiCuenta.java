package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMiCuenta extends PageBase {
	
	private static final String XPATH_LINK_MIS_DATOS = "//a[@href[contains(.,'account/personalinfo')]]";
	private static final String XPATH_LINK_MIS_COMPRAS = "//a[@href[contains(.,'/mypurchases')]]";
	private static final String XPATH_LINK_SUSCRIPCIONES = "//a[@href[contains(.,'account/suscriptions')]]";
	private static final String XPATH_LINK_DEVOLUCIONES = "//span[@data-event-category='devoluciones']";
	private static final String XPATH_LINK_REEMBOLSOS = "//a[@data-event-category='mi-cuenta-reembolsos']";
	
	public boolean isPageUntil(int seconds) {
		return state(Visible, XPATH_LINK_MIS_DATOS).wait(seconds).check();
	}
	
	public void clickMisCompras() {
		click(XPATH_LINK_MIS_COMPRAS).exec();
		if (!state(Invisible, XPATH_LINK_MIS_COMPRAS).wait(1).check()) {
			click(XPATH_LINK_MIS_COMPRAS).exec();
		}
	}
	
	public void clickSuscripciones() {
		click(XPATH_LINK_SUSCRIPCIONES).exec();
	}
	
	public void clickDevoluciones() {
		click(XPATH_LINK_DEVOLUCIONES).exec();
	}
	
	public void clickReembolsos() {
		click(XPATH_LINK_REEMBOLSOS).type(javascript).exec();
	}
	
	public void clickMisDatos() {
		click(XPATH_LINK_MIS_DATOS).exec();
	}	
}
