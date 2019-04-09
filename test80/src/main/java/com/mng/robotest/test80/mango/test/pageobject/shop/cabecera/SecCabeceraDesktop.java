package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecBuscadorDesktop;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraDesktop extends SecCabecera {
	
    private final static String XPathIconoBolsaOutlet = "//div[@class[contains(.,'shoppingBagButton')]]";
    private final static String XPathIconoBolsaShop = "//div[@id[contains(.,'shoppingBag')]]//span";

    public final static String XPathLinkLogoMango = "//div[@class='nav-logo' or @class[contains(.,'header-content-logo')] or @class[contains(.,'logo_menu')] or @class='logo']/a";
    private final static String XPathDivNavTools = "//div[@id='navTools']";
    
    //TODO cuando suba a PRO el tema de los Likes en el menú de usuario habrá que eliminar el XPathLikesCabecera
    private final static String XPahtLikesCabecera = "//a[@id='userMenuTrigger']/span[text()[contains(.,'LIKES')]]";
    
    private SecCabeceraDesktop(AppEcom app, WebDriver driver) {
    	this.app = app;
    	this.driver = driver;
    }
    
    public static SecCabeceraDesktop getNew(AppEcom app, WebDriver driver) {
    	return (new SecCabeceraDesktop(app, driver));
    }
    
    @Override
    String getXPathLogoMango() {
    	return XPathLinkLogoMango;
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
        if (isElementClickableUntil(driver, By.xpath(xpathBolsaLink), maxSecondsToWait))
        	clickAndWaitLoad(driver, By.xpath(xpathBolsaLink));
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
    
    public boolean isVisibleLikes() {
    	return (WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPahtLikesCabecera), 1));
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
