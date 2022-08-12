package com.mng.robotest.test.pageobject.shop.micuenta;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageMiCuenta extends PageBase {
	
	private static String XPATH_LINK_MIS_DATOS = "//a[@href[contains(.,'account/personalinfo')]]";
	private static String XPATH_LINK_MIS_COMPRAS = "//a[@href[contains(.,'/mypurchases')]]";
	private static String XPATH_LINK_SUSCRIPCIONES = "//a[@href[contains(.,'account/suscriptions')]]";
	private static String XPATH_LINK_DEVOLUCIONES = "//span[@data-event-category='devoluciones']";
	private static String XPATH_LINK_REEMBOLSOS = "//a[@data-event-category='mi-cuenta-reembolsos']";
	
	public boolean isPageUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_LINK_MIS_DATOS)).wait(maxSeconds).check());
	}
	
	public void clickMisCompras() {
		click(By.xpath(XPATH_LINK_MIS_COMPRAS)).exec();
	}
	
	public void clickSuscripciones() {
		click(By.xpath(XPATH_LINK_SUSCRIPCIONES)).exec();
	}
	
	public void clickDevoluciones() {
		click(By.xpath(XPATH_LINK_DEVOLUCIONES)).exec();
	}
	
	public void clickReembolsos() {
		click(By.xpath(XPATH_LINK_REEMBOLSOS)).type(javascript).exec();
	}
	
	public void clickMisDatos() {
		click(By.xpath(XPATH_LINK_MIS_DATOS)).exec();
	}	
}
