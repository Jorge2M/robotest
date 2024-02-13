package com.mng.robotest.testslegacy.pageobject.shop;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDevoluciones extends PageBase {
	
	public enum Devolucion {
		EN_TIENDA("En tienda", "myPurchases.returns.tab.store"),
		EN_PUNTO_DE_ENTREGA("En punto de entrega", "myPurchases.returns.tab.dropPoint"),
		RECOGIDA_GRATUITA_A_DOMICILIO("Recogida gratuita a domicilio", "myPurchases.returns.tab.home");

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
	
	private static final String XP_CAPA = "//*[@data-testid='myReturns.home.page']";
	private static final String XP_BUTTON_SOLICITAR_RECOGIDA = "//*[@data-testid='myPurchases.returns.seeInstructions.button']";

	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_CAPA).wait(seconds).check();
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
