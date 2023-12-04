package com.mng.robotest.testslegacy.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageDevoluciones extends PageBase {
	
	public enum Devolucion {
		EN_TIENDA("DEVOLUCIÓN GRATUITA EN TIENDA"),
		EN_DOMICILIO("RECOGIDA GRATUITA A DOMICILIO"),
		POR_CORREO("DEVOLUCIÓN POR CORREO"),
		PUNTO_CELERITAS("DEVOLUCIÓN GRATUITA PUNTO CELERITAS");
		
		String literal;
		String xpathLink;
		String xpathLinkPlegada;
		String xpathLinkDesplegada;
		private Devolucion(String literal) {
			this.literal = literal;
			String tagClass = "@tagClass";
			String xpathBase = "//button[" + tagClass + " and text()[contains(.,'" + this.literal + "')]]";
			this.xpathLink = xpathBase.replace(tagClass, "@class[contains(.,'panel-heading')]");
			this.xpathLinkPlegada = xpathBase.replace(tagClass, "@class[contains(.,'opened')]");
			this.xpathLinkDesplegada = xpathBase.replace(tagClass, "@class[contains(.,'closed')]");
		}
		
		private String getXPath(boolean plegada) {
			if (plegada) {
				return xpathLinkPlegada;
			}
			return xpathLinkDesplegada;
		}
		
		public String getLiteral() {
			return this.literal;
		}
		
		public boolean isPresentLink(WebDriver driver) {
			return state(PRESENT, By.xpath(this.xpathLink), driver).check();
		}

		public void click(WebDriver driver) {
			new PageBase(driver).click(xpathLink).exec();
		}
		
		public void waitForInState(boolean plegada, int seconds, WebDriver driver) {
			By byLink = By.xpath(getXPath(plegada));
			state(PRESENT, byLink, driver).wait(seconds).check();
		}
	}
	
	private static final String XP_IS_PAGE_DEVOLUCIONES = "//div[@class='devoluciones']";
	private static final String XP_BUTTON_SOLICITAR_RECOGIDA = "//div[@class[contains(.,'devoluciones_button_container')]]/*";

	public boolean isPage() {
		return state(PRESENT, XP_IS_PAGE_DEVOLUCIONES).check();
	}

	public void clickSolicitarRecogida() {
		click(XP_BUTTON_SOLICITAR_RECOGIDA).waitLink(1).exec();
	}

	public boolean isVisibleSolicitarRecogidaButton() {
		return state(VISIBLE, XP_BUTTON_SOLICITAR_RECOGIDA).check();
	}
}
