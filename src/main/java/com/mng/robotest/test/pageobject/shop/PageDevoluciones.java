package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.transversal.PageBase;

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
			this.xpathLinkPlegada = xpathBase.replace(tagClass, "@class[contains(.,'opened')]]");
			this.xpathLinkDesplegada = xpathBase.replace(tagClass, "@class[contains(.,'closed')]]");
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
			return (state(Present, By.xpath(this.xpathLink), driver).check());
		}

		public void click(WebDriver driver) {
			PageBase.click(By.xpath(xpathLink), driver).exec();
		}
		
		public void waitForInState(boolean plegada, int maxSeconds, WebDriver driver) {
			By byLink = By.xpath(getXPath(plegada));
			state(Present, byLink, driver).wait(maxSeconds).check();
		}
	}
	
	private static final String XPATH_IS_PAGE_DEVOLUCIONES = "//div[@class='devoluciones']";
	private static final String XPATH_BUTTON_SOLICITAR_RECOGIDA = "//div[@class[contains(.,'devoluciones_button_container')]]/span";

	public boolean isPage() {
		return (state(Present, By.xpath(XPATH_IS_PAGE_DEVOLUCIONES)).check());
	}

	public void clickSolicitarRecogida() {
		click(By.xpath(XPATH_BUTTON_SOLICITAR_RECOGIDA)).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un par de veces
		for (int i=0; i<2; i++) {
			if (isVisibleSolicitarRecogidaButton()) {
				click(By.xpath(XPATH_BUTTON_SOLICITAR_RECOGIDA)).type(TypeClick.javascript).exec();
			}
		}
	}

	public boolean isVisibleSolicitarRecogidaButton() {
		return (state(Visible, By.xpath(XPATH_BUTTON_SOLICITAR_RECOGIDA)).check());
	}
}
