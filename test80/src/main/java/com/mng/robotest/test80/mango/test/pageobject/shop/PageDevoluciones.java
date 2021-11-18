package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageDevoluciones {
	
	public enum Devolucion {
		EnTienda("DEVOLUCIÓN GRATUITA EN TIENDA"),
		EnDomicilio("RECOGIDA GRATUITA A DOMICILIO"),
		PorCorreo("DEVOLUCIÓN POR CORREO"),
		PuntoCeleritas("DEVOLUCIÓN GRATUITA PUNTO CELERITAS");
		
		String literal;
		String xpathLink;
		String xpathLinkPlegada;
		String xpathLinkDesplegada;
		private Devolucion(String literal) {
			this.literal = literal;
			String tagClass = "@tagClass";
			String xpathBase = "//a[" + tagClass + " and text()[contains(.,'" + this.literal + "')]]";
			this.xpathLink = xpathBase.replace(tagClass, "@class[contains(.,'txtSecciones')]");
			this.xpathLinkPlegada = xpathBase.replace(tagClass, "@class='txtSecciones'");
			this.xpathLinkDesplegada = xpathBase.replace(tagClass, "@class=''txtSecciones collapsed'");
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
			PageObjTM.click(By.xpath(xpathLink), driver).exec();
		}
		
		public void waitForInState(boolean plegada, int maxSeconds, WebDriver driver) {
			By byLink = By.xpath(getXPath(plegada));
			state(Present, byLink, driver).wait(maxSeconds).check();
		}
	}
	
	private static String XPathIsPageDevoluciones = "//div[@class='devoluciones']";
	private static String XPathButtonSolicitarRecogida = "//div[@class[contains(.,'devoluciones_button_container')]]/span";

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathIsPageDevoluciones), driver).check());
	}

	public static void clickSolicitarRecogida(WebDriver driver) {
		click(By.xpath(XPathButtonSolicitarRecogida), driver).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un par de veces
		for (int i=0; i<2; i++) {
			if (isVisibleSolicitarRecogidaButton(driver)) {
				click(By.xpath(XPathButtonSolicitarRecogida), driver).type(TypeClick.javascript).exec();
			}
		}
	}

	public static boolean isVisibleSolicitarRecogidaButton(WebDriver driver) {
		return (state(Visible, By.xpath(XPathButtonSolicitarRecogida), driver).check());
	}
}
