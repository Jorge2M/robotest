package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.webdriverwrapper.ElementPage;
import com.mng.testmaker.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletMobil extends SecCabeceraOutlet {
	
	public enum IconoCabOutletMobil implements ElementPage {
		bolsa("//a[@class[contains(.,'cartLink')]]"),
		lupa("//div[@class='menu-search-button']");
		
		private String xpath;
		private IconoCabOutletMobil(String xpath) {
			this.xpath = xpath;
		}
		
		public String getXPath() {
			return xpath;
		}
	}
		
	private final static String XPathNumArticles = "//span[@class[contains(.,'_cartNum')]]";

    private SecCabeceraOutletMobil(Channel channel, AppEcom app, WebDriver driver) {
    	super(channel, app, driver);
    }
    
    public static SecCabeceraOutletMobil getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletMobil(channel, app, driver));
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isInStateIconoBolsa(StateElem state) {
    	return (isElementInStateUntil(IconoCabOutletMobil.bolsa, state, 0));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	click(IconoCabOutletMobil.bolsa);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception {
    	clickIfClickableUntil(IconoCabOutletMobil.bolsa, maxSecondsToWait);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(IconoCabOutletMobil.bolsa);
    }
    
    public boolean isElementInStateUntil(IconoCabOutletMobil icono, StateElem state, int maxSecondsWait) {
    	return (isElementInStateUntil(icono, state, maxSecondsWait, driver));
    }
    
    public boolean isClickableUntil(IconoCabOutletMobil icono, int maxSeconds) {
    	return (isElementClickableUntil(driver, By.xpath(icono.getXPath()), maxSeconds));
    }
    
    public void clickIfClickableUntil(IconoCabOutletMobil icono, int maxSecondsToWait) 
    throws Exception {
    	if (isClickableUntil(icono, maxSecondsToWait)) {
    		click(icono);
    	}
    }
    
    public void click(IconoCabOutletMobil icono) throws Exception {
    	click(icono, app, driver);
    }
    
    public static void click(IconoCabOutletMobil icono, AppEcom app, WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(icono.getXPath()), TypeOfClick.javascript);
    }
    
    public void hoverIcono(IconoCabOutletMobil icono) {
    	moveToElement(icono, driver);
    }
}
