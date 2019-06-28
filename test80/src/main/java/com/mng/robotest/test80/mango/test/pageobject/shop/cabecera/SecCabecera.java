package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecSearch;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;

public abstract class SecCabecera extends WebdrvWrapp {
	
	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final SecSearch secSearch;
	
	abstract String getXPathLogoMango();
	abstract String getXPathNumberArtIcono();
	public abstract boolean isVisibleIconoBolsa();
	public abstract void clickIconoBolsa() throws Exception;
	public abstract void clickIconoBolsaWhenDisp(int maxSecondsToWait) throws Exception;
	public abstract void hoverIconoBolsa();
	
	protected SecCabecera(Channel channel, AppEcom app, WebDriver driver) {
		this.channel = channel;
		this.app = app;
		this.driver = driver;
		this.secSearch = SecSearch.getNew(channel, app, driver);
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
	
	public SecCabeceraShop getShop() {
		return (SecCabeceraShop)this;
	}
	
	public SecCabeceraOutletDesktop getOutletDesktop() {
		return (SecCabeceraOutletDesktop)this;
	}
	
	public SecCabeceraOutletMobil getOutletMobil() {
		return (SecCabeceraOutletMobil)this;
	}
	
    public void buscarTexto(String referencia) throws Exception {
    	secSearch.search(referencia);
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
    
    //-- Específic functions for movil_web (Shop & Outlet)
    
	private final static String XPathHeader = "//header";
    private final static String XPathSmartBanner = XPathHeader + "/div[@id='smartbanner']";
    private final static String XPathLinkCloseSmartBanner = XPathSmartBanner + "//a[@class='sb-close']";    
    private final static String XPathIconoMenuHamburguesa = XPathHeader + "//div[@class[contains(.,'menu-open-button')]]";
    
    /**
     * Si existe, cierra el banner de aviso en móvil (p.e. el que sale proponiendo la descarga de la App)
     */
    public void closeSmartBannerIfExistsMobil() throws Exception {
        if (isElementVisible(driver, By.xpath(XPathLinkCloseSmartBanner))) {
            clickAndWaitLoad(driver, By.xpath(XPathLinkCloseSmartBanner));
        }
    }
    
    /**
     * Función que abre/cierra el menú lateral de móvil según le indiquemos en el parámetro 'open'
     * @param open: 'true'  queremos que el menú lateral de móvil se abra
     *              'false' queremos que el menú lateral de móvil se cierre
     */
    public void clickIconoMenuHamburguerMobil(boolean toOpenMenus) throws Exception {
        boolean menuVisible = SecMenuLateralMobil.isMenuInStateUntil(toOpenMenus, 1, app, driver);
        int i=0;
        TypeOfClick typeClick = TypeOfClick.webdriver;
        while ((menuVisible!=toOpenMenus) && i<5) {
            try {
                isVisibleIconoMenuHamburguesaUntil(5);
                clickIconoMenuHamburguesaWhenReady(typeClick, driver);
                typeClick = TypeOfClick.next(typeClick);
                menuVisible = SecMenuLateralMobil.isMenuInStateUntil(toOpenMenus, 2, app, driver);
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
    
    public void clickIconoMenuHamburguesaWhenReady(TypeOfClick typeOfClick, WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathIconoMenuHamburguesa), typeOfClick);
    }
}
