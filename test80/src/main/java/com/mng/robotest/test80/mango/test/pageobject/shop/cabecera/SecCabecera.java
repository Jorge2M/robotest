package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public abstract class SecCabecera extends WebdrvWrapp {
	AppEcom app;
	WebDriver driver;
	abstract String getXPathLogoMango();
	abstract String getXPathNumberArtIcono();
	public abstract boolean isVisibleIconoBolsa();
	public abstract void clickIconoBolsa() throws Exception;
	public abstract void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception;
	public abstract void hoverIconoBolsa();
	public abstract void buscarReferenciaNoWait(String referencia) throws Exception;
	
	public static SecCabecera getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case desktop:
			return SecCabeceraDesktop.getNew(app, driver);
		case movil_web:
		default:
			return SecCabeceraMobil.getNew(app, driver);
		}
	}
	
    public boolean clickLogoMango() throws Exception {
        String xpathLink = getXPathLogoMango();
        if (isElementPresentUntil(driver, By.xpath(xpathLink), 2)) {
            clickAndWaitLoad(driver, By.xpath(xpathLink));
            return true;
        }
        
        return false;
    }
    
    public void hoverLogoMango() throws Exception {
        String xpathLink = getXPathLogoMango();
        if (isElementPresent(driver, By.xpath(xpathLink))) {
        	moveToElement(By.xpath(xpathLink), driver);
        }
    }
    
    public boolean validaLogoMangoGoesToIdioma(IdiomaPais idioma) {
        String xpathLink = getXPathLogoMango();
        String xpathLogoIdiom = xpathLink + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
        return (isElementPresent(driver, By.xpath(xpathLogoIdiom)));
    }
    
    public int getNumArticulosBolsa() throws Exception {
        int numArticulos = 0;
        String numArtStr = getNumberArtIcono();
        if (numArtStr.matches("\\d+$")) {
            numArticulos = Integer.valueOf(numArtStr).intValue();
        }
        return numArticulos;
    }    
    
    public boolean hayArticulosBolsa() throws Exception {
        return (getNumArticulosBolsa() > 0);
    }
    
    public String getNumberArtIcono() throws Exception {
        String articulos = "0";
        waitForPageLoaded(driver); //Para evitar staleElement en la línea posterior
        String xpathNumberArtIcono = getXPathNumberArtIcono();
        if (isElementVisible(driver, By.xpath(xpathNumberArtIcono))) {
            articulos = driver.findElement(By.xpath(xpathNumberArtIcono)).getText();
        }
        return articulos;
    }
}
