package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

/**
 * Cabecera Shop compatible con desktop y movil_web
 *
 */
public class SecCabeceraShop extends SecCabecera {
	
	protected SecCabeceraShop(Channel channel, AppEcom app, WebDriver driver) {
		super(channel, app, driver);
	}
	
	public static SecCabeceraShop getNew(Channel channel, AppEcom app, WebDriver driver) {
		return (new SecCabeceraShop(channel, app, driver));
	}
	
	private final static String XPathLinkLogoMango = "//a[@class='logo-link']";
    private final static String XPathDivNavTools = "//div[@id='navTools']";
    private final static String XPathNumArticlesBolsa = "//span[@class='icon-button-items']";
	
    public enum IconoShop implements ElementPage {
    	buscar("//span[@class[contains(.,'-search')]]"),
		iniciarsesion("//self::*[@id='login_any']/span[@class[contains(.,'-account')]]"),
    	micuenta("//self::*[@id='login']/span[@class[contains(.,'-account')]]"),
		favoritos("//span[@class[contains(.,'-favorites')]]"),
		bolsa("//span[@class[contains(.,'-bag')]]");

		private String xPath;
		final static String XPathIcon = "//div[@class='user-icon-button']";
		IconoShop(String xPath) {
			this.xPath = XPathIcon + xPath;
		}

		public String getXPath() {
			return this.xPath;
		}
	}
    
    @Override
    String getXPathLogoMango() {
    	return XPathLinkLogoMango;
    }

    @Override
    String getXPathNumberArtIcono() {
    	return (XPathNumArticlesBolsa);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(IconoShop.bolsa);
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	return (isVisibleIcono(IconoShop.bolsa));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	clickIcono(IconoShop.bolsa);
    }

    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsWait) throws Exception {
    	boolean isIconoClickable = isElementInStateUntil(IconoShop.bolsa, StateElem.Clickable, maxSecondsWait, driver);
        if (isIconoClickable) {
        	clickIconoBolsa();
        }
    }

    public void clickIcono(IconoShop icono) throws Exception {
    	clickAndWait(icono, driver);
    }
    
    public boolean isVisibleIcono(IconoShop icono) {
    	return (isElementInState(icono, StateElem.Visible, driver));
    }
    
    public void hoverIcono(IconoShop icono) {
        By iconoBy = By.xpath(icono.getXPath());
        moveToElement(iconoBy, driver);
    }
    
    public void focusAwayBolsa(WebDriver driver) {
    	//The moveElement doens't works properly for hide the Bolsa-Modal
    	driver.findElement(By.xpath(XPathDivNavTools)).click();
    }
}
