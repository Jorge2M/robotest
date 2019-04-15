package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class PageDevoluciones extends WebdrvWrapp {
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
	        return (isElementPresent(driver, By.xpath(this.xpathLink)));
	    }
	    
	    public void click(WebDriver driver) throws Exception {
	        clickAndWaitLoad(driver, By.xpath(xpathLink));
	    }
	    
	    public void waitForInState(boolean plegada, int maxSeconds, WebDriver driver) {
	    	By byLink = By.xpath(getXPath(plegada));
	    	isElementPresentUntil(driver, byLink, maxSeconds);
	    }
	}
	
    private static String XPathIsPageDevoluciones = "//div[@class='devoluciones']";
    private static String XPathButtonSolicitarRecogida = "//div[@class[contains(.,'devoluciones_button_container')]]/span";
    
    /**
     * @return si realmente se trata de la página de devoluciones
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathIsPageDevoluciones)));
    }  
    
    /**
     * Click del botón "Solicitar Recogida" que aparece dentro del apartado "Recogida gratuíta a domicilio"
     */
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
    	return (isElementVisible(driver, By.xpath(XPathButtonSolicitarRecogida)));
    }
}
