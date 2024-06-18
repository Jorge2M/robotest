package com.mng.robotest.testslegacy.pageobject.shop;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDevoluciones extends PageBase {
	
	public enum Devolucion {
		EN_TIENDA("En tienda", "myReturns.returns.tab.returnInStore"),
		EN_PUNTO_DE_ENTREGA("En punto de entrega", "myReturns.returns.tab.dropPoint"),
		RECOGIDA_GRATUITA_A_DOMICILIO("Recogida gratuita a domicilio", "myReturns.returns.tab.homePickUp");

		private final String literal;
		private final String testid;;
		private Devolucion(String literal, String testid) {
			this.literal = literal;
			this.testid = testid;
		}

		public String getLiteral() {
			return this.literal;
		}		
		
		public String getXPath() {
			return "//*[@data-testid='" + testid + "']";
		}
	}
	
	private static final String XP_BUTTON_SOLICITAR_RECOGIDA = "//*[@data-testid='myPurchases.online.details.returns.requestPickup.button']";

	public boolean isPage(int seconds) {
		return state(VISIBLE, Devolucion.EN_TIENDA.getXPath()).wait(seconds).check();
	}
	
	public boolean isVisible(Devolucion devolucion, int seconds) {
		return state(VISIBLE, devolucion.getXPath()).wait(seconds).check();
	}

	public void click(Devolucion devolucion) {
		click(devolucion.getXPath()).exec();
	}
	
	public void clickSolicitarRecogida() {
		click(XP_BUTTON_SOLICITAR_RECOGIDA).waitLink(1).exec();
	}

	public boolean isVisibleSolicitarRecogidaButton() {
		return state(VISIBLE, XP_BUTTON_SOLICITAR_RECOGIDA).check();
	}
	
}
