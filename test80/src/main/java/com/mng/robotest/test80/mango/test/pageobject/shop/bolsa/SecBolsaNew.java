package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Pais;


public class SecBolsaNew extends SecBolsaDesktop {
 
	private final LineasArtBolsa lineasArtBolsa;
	
	private static final String XPathPanelBolsaDesktop = "//div[@id='openedShoppingBag']";
	private static final String XPathPanelBolsaMobile = "//div[@class[contains(.,'m_bolsa')]]";
	
	//TODO eliminar el 1o cuando suba a PRO
	private static final String XPathBotonComprar = 
		"//button[@data-testid='bag.fullpage.checkout.button' or @data-testid='bag.preview.checkout.button']";


	public SecBolsaNew(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
		super(channel, app, driver);
		lineasArtBolsa = new LineasArtBolsaNew(channel, driver);
	}

	
	@Override
	String getXPathPanelBolsa() {
		if (channel==Channel.mobile) {
			return XPathPanelBolsaMobile;
		}
		return XPathPanelBolsaDesktop;
	}
	
	@Override
	String getXPathBotonComprar() {
		return XPathBotonComprar; 
	}
	
	@Override
	String getXPathPrecioSubTotal() {
		String xpathBolsa = getXPathPanelBolsa();
		return  
			xpathBolsa + 
			"//div[@class[contains(.,'layout-content')]]" + 
			"//div[@class[contains(.,'right')]]/div";
	}  
	
	@Override
	public LineasArtBolsa getLineasArtBolsa() {
		return lineasArtBolsa;
	}

}
