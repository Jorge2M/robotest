package com.mng.robotest.test80.mango.test.pageobject.shop.cabecera;

import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.pageobject.shop.buscador.SecSearch;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenusUserWrapper.UserMenu;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil.SecMenuLateralMobil;

public abstract class SecCabecera extends WebdrvWrapp {
	
	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final SecSearch secSearch;

	private final static String XPathLinkLogoMango = "//a[@class='logo-link' or @class[contains(.,'logo_')]]";

	abstract String getXPathNumberArtIcono();
	public abstract boolean isInStateIconoBolsa(StateElem state);
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
	
    public static void buscarTexto(String referencia, Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	MenusUserWrapper menusUser = MenusUserWrapper.getNew(channel, app, driver);
    	menusUser.clickMenuAndWait(UserMenu.lupa);
    	SecSearch secSearch = SecSearch.getNew(channel, app, driver);
    	secSearch.search(referencia);
    }
	
    public boolean clickLogoMango() throws Exception {
        if (isElementPresentUntil(driver, By.xpath(XPathLinkLogoMango), 2)) {
            clickAndWaitLoad(driver, By.xpath(XPathLinkLogoMango));
            return true;
        }
        return false;
    }
    
    public void hoverLogoMango() throws Exception {
        if (isElementPresent(driver, By.xpath(XPathLinkLogoMango))) {
        	moveToElement(By.xpath(XPathLinkLogoMango), driver);
        }
    }
    
    public boolean validaLogoMangoGoesToIdioma(IdiomaPais idioma) {
        String xpathLogoIdiom = XPathLinkLogoMango + "[@href[contains(.,'/" + idioma.getAcceso() + "')]]";
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
    	SecMenuLateralMobil secMenuLateral = SecMenuLateralMobil.getNew(app, driver);
        boolean menuVisible = secMenuLateral.isMenuInStateUntil(toOpenMenus, 1);
        int i=0;
        TypeOfClick typeClick = TypeOfClick.webdriver;
        while ((menuVisible!=toOpenMenus) && i<5) {
            try {
                isVisibleIconoMenuHamburguesaUntil(5);
                clickIconoMenuHamburguesaWhenReady(typeClick, driver);
                typeClick = TypeOfClick.next(typeClick);
                menuVisible = secMenuLateral.isMenuInStateUntil(toOpenMenus, 2);
            }
            catch (Exception e) {
                LogManager.getLogger(Log4jConfig.log4jLogger).warn("Exception in click icono Hamburguer", e);
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
