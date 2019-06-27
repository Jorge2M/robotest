package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPage;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public abstract class SecCabeceraShop extends SecCabecera {

	protected SecCabeceraShop(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	public static SecCabeceraShop getNew(AppEcom app, Channel channel, WebDriver driver) {
		switch (channel) {
		case desktop:
			return SecCabeceraShopDesktop.getNew(app, driver);
		case movil_web:
		default:
			return SecCabeceraShopMovil.getNew(app, driver);
		}
	}
	
	private final static String XPathLinkLogoMango = "//a[@class='logo-link']";
    private final static String XPathDivNavTools = "//div[@id='navTools']";
    private final static String XPathNumArticlesBolsa = "//span[@class='icon-button-items']";
	
    private enum IconoShop implements ElementPage {
    	buscar("//span[@class[contains(.,'-search')]]"),
    	micuenta("//span[@class[contains(.,'-account')]]"),
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

		public String getXPath(Channel channel) {
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
        By iconoBolsaBy = By.xpath(IconoShop.bolsa.getXPath());
        moveToElement(iconoBolsaBy, driver);
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	return (isElementInState(IconoShop.bolsa, StateElem.Visible, driver));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	By iconoBolsaBy = By.xpath(IconoShop.bolsa.getXPath());
    	clickAndWaitLoad(driver, iconoBolsaBy);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception {
    	
        if (isElementClickableUntil(driver, By.xpath(xpathBolsaLink), maxSecondsToWait)) {
        	clickAndWaitLoad(driver, By.xpath(xpathBolsaLink));
        }
    }
    
    public void focusAwayBolsa(WebDriver driver) {
    	//The moveElement doens't works properly for hide the Bolsa-Modal
    	driver.findElement(By.xpath(XPathDivNavTools)).click();
    }
}
