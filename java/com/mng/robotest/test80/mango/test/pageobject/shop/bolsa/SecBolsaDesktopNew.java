package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;


import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;


public class SecBolsaDesktopNew extends SecBolsaDesktop {
 
	private final LineasArtBolsa lineasArtBolsa;
	
    private static final String XPathPanelBolsa = "//div[@id='openedShoppingBag']";
    private static final String XPathBotonComprar = "//button[@data-testid='bag.fullpage.checkout.button']";
    
	private static final String XPathPrecioSubTotal = 
			XPathPanelBolsa + 
			"//div[@class[contains(.,'layout-content')]]" + 
			"//div[@class[contains(.,'right')]]/div";
    
    public SecBolsaDesktopNew(Channel channel, AppEcom app, WebDriver driver) {
    	super(app, driver);
    	lineasArtBolsa = new LineasArtBolsaDesktopNew(channel, driver);
    }
    
    @Override
    String getXPathPanelBolsa() {
        return XPathPanelBolsa;
    }
    
    @Override
    String getXPathBotonComprar() {
        return XPathBotonComprar; 
    }
    
	@Override
    String getXPathPrecioSubTotal() {
        return XPathPrecioSubTotal; 
    }  
	
	@Override
	public LineasArtBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}

}
