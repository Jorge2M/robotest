package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraOutletMobil extends SecCabeceraOutlet {
	
	public enum IconoCab implements ElementPage {
		bolsa("//a[@class[contains(.,'cartlink')]]");
		
		private String xpath;
		private IconoCab(String xpath) {
			this.xpath = xpath;
		}
		
		public String getXPath() {
			return xpath;
		}
	}
		
    private final static String XPathLinkLogo = SecCabeceraOutletDesktop.XPathLinkLogo;
	private final static String XPathNumArticles = "//span[@class[contains(.,'_cartNum')]]";

    private SecCabeceraOutletMobil(Channel channel, AppEcom app, WebDriver driver) {
    	super(channel, app, driver);
    }
    
    public static SecCabeceraOutletMobil getNew(Channel channel, AppEcom app, WebDriver driver) {
    	return (new SecCabeceraOutletMobil(channel, app, driver));
    }

    @Override
    String getXPathLogoMango() {
    	return XPathLinkLogo;
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return XPathNumArticles;
    }
    
    @Override
    public boolean isInStateIconoBolsa(StateElem state) {
    	return (isElementInState(IconoCab.bolsa, state, driver));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	click(IconoCab.bolsa);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception {
    	clickIfClickableUntil(IconoCab.bolsa, maxSecondsToWait);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(IconoCab.bolsa);
    }
    
    public boolean isClickableUntil(IconoCab icono, int maxSeconds) {
    	return (isElementClickableUntil(driver, By.xpath(icono.getXPath()), maxSeconds));
    }
    
    public void clickIfClickableUntil(IconoCab icono, int maxSecondsToWait) 
    throws Exception {
    	if (isClickableUntil(icono, maxSecondsToWait)) {
    		click(icono);
    	}
    }
    
    public void click(IconoCab icono) throws Exception {
    	click(icono, app, driver);
    }
    
    public static void click(IconoCab icono, AppEcom app, WebDriver driver) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(icono.getXPath()), TypeOfClick.javascript);
    }
    
    public void hoverIcono(IconoCab icono) {
    	moveToElement(icono, driver);
    }
}
