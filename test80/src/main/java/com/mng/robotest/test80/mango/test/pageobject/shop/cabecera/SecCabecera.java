package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;

public abstract class SecCabecera extends WebdrvWrapp {
	
	final WebDriver driver;
	final AppEcom app;
	
	abstract String getXPathLogoMango();
	abstract String getXPathNumberArtIcono();
	public abstract boolean isVisibleIconoBolsa();
	public abstract void clickIconoBolsa() throws Exception;
	public abstract void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception;
	public abstract void hoverIconoBolsa();
	public abstract void buscarTexto(String referencia) throws Exception;
	
	protected SecCabecera(AppEcom app, WebDriver driver) {
		this.driver = driver;
		this.app = app;
	}
	
	public static SecCabecera getNew(Channel channel, AppEcom app, WebDriver driver) {
		switch (app) {
		case shop:
		case votf:
			return SecCabeceraShop.getNew(channel, app, driver);
		case outlet:
		default:
			return SecCabeceraOutlet.getNew(channel, app, driver);
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
