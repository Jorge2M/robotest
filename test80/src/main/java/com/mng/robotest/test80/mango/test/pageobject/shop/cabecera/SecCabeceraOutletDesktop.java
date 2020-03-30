package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletDesktop extends SecCabeceraOutlet {
	
	public enum LinkCabeceraOutletDesktop implements ElementPage {
		lupa("//span[@class='menu-search-icon']"),
		registrate("//a[@data-origin='register']"),
		iniciarsesion("//a[@data-origin='login']"),
		cerrarsesion("//span[@class[contains(.,'_logout')]]"),
		micuenta("//a[@href[contains(.,'/account')]]"),
		pedidos("//a[@class[contains(.,'_pedidos')]]"),
		ayuda("//a[@class[contains(.,'_pedidos')]]"),
		bolsa("//div[@class[contains(.,'shoppingCart')]]");
		
		private String xpath;
		private LinkCabeceraOutletDesktop(String xpath) {
			this.xpath = xpath;
		}
		
		public String getXPath() {
			return xpath;
		}
	}

	private final static String XPathNumArticles = "//span[@id[contains(.,'bolsa_articulosNum')]]";
    public final static String XPathLinkLogo = "//a[@class[contains(.,'headerMobile__logoLink')]]";
    
    private SecCabeceraOutletDesktop(Channel channel, AppEcom app, WebDriver driver) {
    	super(channel, app, driver);
    }
    
    public static SecCabeceraOutletDesktop getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletDesktop(channel, app, driver));
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isInStateIconoBolsa(State state) {
    	return (isElementInState(LinkCabeceraOutletDesktop.bolsa, state));
    }

    @Override
    public void clickIconoBolsa() {
    	clickElement(LinkCabeceraOutletDesktop.bolsa);
    }

    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsWait) throws Exception {
    	boolean isIconoClickable = isElementInStateUntil(LinkCabeceraOutletDesktop.bolsa, State.Clickable, maxSecondsWait, driver);
        if (isIconoClickable) {
        	clickIconoBolsa();
        }
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverElement(LinkCabeceraOutletDesktop.bolsa);
    }
    
    public boolean isElementInState(LinkCabeceraOutletDesktop element, State state) {
    	return (isElementInState(element, state, driver));
    }
    
    public boolean isElementInStateUntil(LinkCabeceraOutletDesktop element, State state, int maxSecondsWait) {
    	return (isElementInStateUntil(element, state, maxSecondsWait, driver));
    }
    
    public void clickElement(LinkCabeceraOutletDesktop element) {
    	clickAndWait(element, driver);
    }
    
    public void hoverElement(LinkCabeceraOutletDesktop element) {
        By elementBy = By.xpath(element.getXPath());
        moveToElement(elementBy, driver);
    }
}
