package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecSearchDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletDesktop extends SecCabeceraOutlet {
	
	public enum ElementOutlet implements ElementPage {
		registrate("//a[@data-origin='register']"),
		iniciarsesion("//a[@data-origin='login']"),
		cerrarsesion("//span[@class[contains(.,'_logout')]]"),
		pedidos("//a[@class[contains(.,'_pedidos')]]"),
		ayuda("//a[@class[contains(.,'_pedidos')]]"),
		bolsa("//div[@class[contains(.,'shoppingCart')]]");
		
		private String xpath;
		private ElementOutlet(String xpath) {
			this.xpath = xpath;
		}
		
		public String getXPath() {
			return xpath;
		}
	}

	private final static String XPathNumArticles = "//span[@id[contains(.,'bolsa_articulosNum')]]";
    private final static String XPathLinkLogoMangoOutlet = "//a[@class[contains(.,'headerMobile__logoLink')]]";
    
    private SecCabeceraOutletDesktop(AppEcom app, WebDriver driver) {
    	super(app, driver);
    }
    
    public static SecCabeceraOutletDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletDesktop(app, driver));
    }

    @Override
    String getXPathLogoMango() {
    	return XPathLinkLogoMangoOutlet;
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	return (isVisibleElement(ElementOutlet.bolsa));
    }

    
    @Override
    public void clickIconoBolsa() throws Exception {
    	clickElement(ElementOutlet.bolsa);
    }

    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsWait) throws Exception {
    	boolean isIconoClickable = isElementInStateUntil(ElementOutlet.bolsa, StateElem.Clickable, maxSecondsWait, driver);
        if (isIconoClickable) {
        	clickIconoBolsa();
        }
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverElement(ElementOutlet.bolsa);
    }

    @Override
    public void buscarTexto(String referencia) throws Exception {
    	SecSearchDesktop.search(referencia, driver);
    }
    
    public boolean isVisibleElement(ElementOutlet element) {
    	return (isElementInState(element, StateElem.Visible, driver));
    }
    
    public void clickElement(ElementOutlet element) throws Exception {
    	clickAndWait(element, driver);
    }
    
    public void hoverElement(ElementOutlet element) {
        By elementBy = By.xpath(element.getXPath());
        moveToElement(elementBy, driver);
    }
}
