package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.Mensajes;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;

@SuppressWarnings("javadoc")
/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Cabecera" (de Desktop y Movil)
 * @author jorge.munoz
 *
 */
public class SecCabeceraMobil extends SecCabecera {
	
	final static String XPathHeader = "//header";
	public enum Icono {
		Lupa(
			"//div[@class='menu-search-button' or @class='user-icon-button']",
			"//div[@class='menu-search-button' or @class='user-icon-button']"),
		IniciarSesion(
			"//div[@class='user-icon-button' and @id='login_mobile_any']",
			"//a[@class[contains(.,'myAccount')] and @data-origin='login']"),
		MiCuenta(
			"//div[@class='user-icon-button' and @id='login_mobile']",
			"//a[@class[contains(.,'myAccount')] and not(@data-origin)]"),
		Favoritos(
			"//div[@class='user-icon-button' and @id[contains(.,'favorites_mobile')]]",
			"//a[@class[contains(.,'favorites')]]"),
		Bolsa(
			"//div[@class='user-icon-button' and @id[contains(.,'bolsa_mobile')]]",
			"//div[@id[contains(.,'bolsaMobile')]]");
		
		private String xpathShop;
		private String xpathOutlet;
		private Icono(String xpathRelativeShop, String xpathRelativeOutlet) {
			this.xpathShop = XPathHeader + xpathRelativeShop;
			this.xpathOutlet = XPathHeader + xpathRelativeOutlet;
		}
		
		String getXPath(AppEcom app) {
			switch (app) {
			case outlet:
				return xpathOutlet;
			case shop:
			default:
				return xpathShop;
			}
		}
		
		String getXPathLink(AppEcom app) {
			if (app==AppEcom.outlet && this.equals(Bolsa)) {
				return (getXPath(app) + "//a");
			}
			
			return (getXPath(app));
		}
	}
	
    private final static String XPathInputBuscador = "//form[not(@class)]/input[@class='search-input']";
    private final static String XPathSmartBanner = XPathHeader + "/div[@id='smartbanner']";
    private final static String XPathLinkCloseSmartBanner = XPathSmartBanner + "//a[@class='sb-close']";       
    private final static String XPathLinkLogoMangoShop = "//a[@class='header-content-logo']";
    private final static String XPathLinkLogoMangoOutlet = "//a[@class[contains(.,'headerMobile__logoLink')]]";
    private final static String XPathIconoMenuHamburguesa = XPathHeader + "//div[@class[contains(.,'menu-open-button')]]";
    
    private SecCabeceraMobil(AppEcom app, WebDriver driver) {
    	this.app = app;
    	this.driver = driver;
    }
    
    public static SecCabeceraMobil getNew(AppEcom app, WebDriver driver) {
    	return (new SecCabeceraMobil(app, driver));
    }

    
    @Override
    String getXPathLogoMango() {
    	switch (app) {
    	case outlet:
    		return XPathLinkLogoMangoOutlet;
    	case shop:
    	default:
    		return XPathLinkLogoMangoShop;
    	}
    }
    
    @Override
    String getXPathNumberArtIcono() {
    	return (Icono.Bolsa.getXPath(app) + "//span[@class[contains(.,'cartNum')] or @class[contains(.,'-items')]]");
    }
    
    @Override
    public boolean isVisibleIconoBolsa() {
    	return (isVisible(Icono.Bolsa));
    }
    
    @Override
    public void clickIconoBolsa() throws Exception {
    	click(Icono.Bolsa);
    }
    
    @Override
    public void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception {
    	clickIfClickableUntil(Icono.Bolsa, maxSecondsToWait);
    }
    
    @Override
    public void hoverIconoBolsa() {
    	hoverIcono(Icono.Bolsa);
    }
    
    @Override
    public void buscarReferenciaNoWait(String referencia) throws Exception {
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Mensajes.getXPathCapaCargando())));
        buscarRefNoWait(referencia);
    }
    
    public boolean isVisible(Icono icono) {
    	return (isElementVisible(driver, By.xpath(icono.getXPath(app))));
    }
    
    public boolean isClicable(Icono icono) {
    	return (isClickableUntil(icono, 0));
    }
    
    public boolean isClickableUntil(Icono icono, int maxSeconds) {
    	return (isElementClickableUntil(driver, By.xpath(icono.getXPathLink(app)), maxSeconds));
    }
    
    public void clickIfClickableUntil(Icono icono, int maxSecondsToWait) 
    throws Exception {
    	if (isClickableUntil(icono, maxSecondsToWait))
    		click(icono);
    }
    
    public void click(Icono icono) throws Exception {
    	clickAndWaitLoad(driver, By.xpath(icono.getXPathLink(app)), TypeOfClick.javascript);
    }
    
    public void clickIconoLupaIfBuscadorNotVisible() throws Exception {
    	if (!isBuscadorVisibleUntil(0))
    		click(Icono.Lupa);
    }
    
    public void hoverIcono(Icono icono) {
    	moveToElement(By.xpath(icono.getXPathLink(app)), driver);
    }
    
    public boolean isBuscadorVisibleUntil(int maxSeconds) {
    	return (isElementVisibleUntil(driver, By.xpath(XPathInputBuscador), maxSeconds));
    }
    
    /**
     * Busca un determinado artículo por su referencia y no espera a la página de resultado
     */
    public void buscarRefNoWait(String referencia) throws Exception {
        clickLupaAndWaitInput(2/*maxSecondsToWait*/);
        setTextAndReturn(referencia);
    }
    
    public boolean clickLupaAndWaitInput(int maxSecondsToWait) throws Exception {
    	clickIconoLupaIfBuscadorNotVisible();
        return (isElementPresentUntil(driver, By.xpath(XPathInputBuscador), maxSecondsToWait));
    }
    
    /**
     * Introducimos la referencia en el buscador y seleccionamos RETURN
     */
    public void setTextAndReturn(String referencia) {
    	if (isBuscadorVisibleUntil(2)) {
	        WebElement input = getElementVisible(driver, By.xpath(XPathInputBuscador));
	        input.clear();
	        sendKeysWithRetry(5, input, referencia);
	        input.sendKeys(Keys.RETURN);
    	}
    }    
    
    public boolean logoMangoGoesToIdioma(IdiomaPais idioma) {
    	String xpathLogo = getXPathLogoMango();
        String xpathLogoIdiom = xpathLogo + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
        return (isElementPresent(driver, By.xpath(xpathLogoIdiom)));
    }
    
    /**
     * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
     */
    public void closeSmartBannerIfExists() throws Exception {
        if (isElementVisible(driver, By.xpath(XPathLinkCloseSmartBanner))) 
            clickAndWaitLoad(driver, By.xpath(XPathLinkCloseSmartBanner));
    }
    
    /**
     * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
     * @param open: 'true'  queremos que el menú lateral de móvil se abra
     *              'false' queremos que el menú lateral de móvil se cierre
     */
    public void clickIconoMenuHamburguer(boolean toOpenMenus) throws Exception {
        boolean menuVisible = SecMenuLateralMobil.isMenuInStateUntil(toOpenMenus, 1/*maxSecondsToWait*/, app, driver);
        int i=0;
        while ((menuVisible!=toOpenMenus) && i<5) {
            try {
                isVisibleIconoMenuHamburguesaUntil(5/*maxSecondsToWait*/);
                clickIconoMenuHamburguesaWhenReady(driver);
                menuVisible = SecMenuLateralMobil.isMenuInStateUntil(toOpenMenus, 2/*maxSecondsToWait*/, app, driver);
            }
            catch (Exception e) {
                LogManager.getLogger(fmwkTest.log4jLogger).warn("Exception in click icono Hamburguer", e);
            }
            
            i+=1;
        }
    }

    public boolean isVisibleIconoMenuHamburguesaUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathIconoMenuHamburguesa), maxSecondsToWait));
    }
    
    public void clickIconoMenuHamburguesaWhenReady(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathIconoMenuHamburguesa));
    }
}
