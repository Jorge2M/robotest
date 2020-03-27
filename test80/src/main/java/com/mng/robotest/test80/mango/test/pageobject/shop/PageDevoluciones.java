package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;

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

		public void click(WebDriver driver) throws Exception {
			clickAndWaitLoad(driver, By.xpath(xpathLink));
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

    public static void clickSolicitarRecogida(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSolicitarRecogida));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un par de veces
        for (int i=0; i<2; i++) {
	        if (isVisibleSolicitarRecogidaButton(driver)) {
	        	clickAndWaitLoad(driver, By.xpath(XPathButtonSolicitarRecogida), TypeOfClick.javascript);
	        }
        }
    }

	public static boolean isVisibleSolicitarRecogidaButton(WebDriver driver) throws Exception {
		return (state(Visible, By.xpath(XPathButtonSolicitarRecogida), driver).check());
	}
}
