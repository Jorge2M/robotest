package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecBuscadorDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraShopDesktop extends SecCabeceraShop {
	
    private final static String XPathIconoBolsaOutlet = "//div[@class[contains(.,'shoppingBagButton')]]";
    private final static String XPathIconoBolsaShop = "//div[@id[contains(.,'shoppingBag')]]//span";

    private final static String XPathDivNavTools = "//div[@id='navTools']";
    
    private SecCabeceraShopDesktop(AppEcom app, WebDriver driver) {
    	super(app, driver);
    }
    
    public static SecCabeceraShopDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecCabeceraShopDesktop(app, driver));
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return ("//*[@class='conjunto_numItemsBolsa']/span[@class[contains(.,'bolsaItems')]]");
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	String xpathIconoBolsa = getXPathIconoBolsa();
        return (isElementVisible(driver, By.xpath(xpathIconoBolsa)));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	String xpathIcono = getXPathIconoBolsa();
    	clickAndWaitLoad(driver, By.xpath(xpathIcono));
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) 
    throws Exception {
        String xpathBolsaLink = getXPathIconoBolsa();
        if (isElementClickableUntil(driver, By.xpath(xpathBolsaLink), maxSecondsToWait)) {
        	clickAndWaitLoad(driver, By.xpath(xpathBolsaLink));
        }
    }
    
    @Override
    public void hoverIconoBolsa() {
        String xpathBolsa = getXPathIconoBolsa();
        moveToElement(By.xpath(xpathBolsa), driver);
    }
    
    @Override
    public void buscarReferenciaNoWait(String referencia) throws Exception {
    	SecBuscadorDesktop.buscarReferenciaNoWait(referencia, driver);
    }

    public String getXPathIconoBolsa() {
        switch (app) {
        case outlet:
            return XPathIconoBolsaOutlet;
        case shop:
        case votf:
        default:
            return XPathIconoBolsaShop;
        }
    }
    
    public void moveToLogo(WebDriver driver) {
    	WebElement logo = driver.findElement(By.xpath(XPathLinkLogoMango));
    	moveToElement(logo, driver);
    }
    
    public void focusAwayBolsa(WebDriver driver) {
    	//The moveElement doens't works properly for hide the Bolsa-Modal
    	driver.findElement(By.xpath(XPathDivNavTools)).click();
    }

}
