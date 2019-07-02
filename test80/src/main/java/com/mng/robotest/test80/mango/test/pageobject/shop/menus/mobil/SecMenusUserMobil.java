package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;

public class SecMenusUserMobil extends WebdrvWrapp {

	final AppEcom app;
	final WebDriver driver;
	final SecCabecera secCabecera;
	
	private SecMenusUserMobil(AppEcom app, WebDriver driver) {
		this.app = app;
		this.driver = driver;
		this.secCabecera = SecCabecera.getNew(Channel.movil_web, app, driver);
	}
	
	public static SecMenusUserMobil getNew(AppEcom app, WebDriver driver) {
		return (new SecMenusUserMobil(app, driver));
	}
	
    private static String XPathCapaMenus = "//ul[@class[contains(.,'menu-section-links')]]";
    private static String XPathMenuAyuda = XPathCapaMenus + "//a[@href[contains(.,'/help/')]]";
    private static String XPathMenuMisCompras = XPathCapaMenus + "//a[@href[contains(.,'/mypurchases')]]";
    private static String XPathMenuPedidos = XPathCapaMenus + "//a[@href[contains(.,'account/orders')]]";
    private static String XPathMenuCambioPais = XPathCapaMenus + "//a[@href[contains(.,'/preHome.faces')]]";
    private static String XPathMenuCerrarSesion = XPathCapaMenus + "//a[@href[contains(.,'/logout')]]";
    private static String XPathMenuFavoritos = XPathCapaMenus + "//a[@href[contains(.,'/favorites')]]";
    private static String XPathMenuIniciarSesion = XPathCapaMenus + "//a[@href[contains(.,'/login?')]]";
    private static String XPathMenuMiCuenta = XPathCapaMenus + "//a[@data-label='mi_cuenta']";
    private static String XPathMenuRegistrate = XPathCapaMenus + "//a[@href[contains(.,'/signup?')]]";
    private static String XPathMenuMangoLikesYou = XPathCapaMenus + "//a[@href[contains(.,'/mangolikesyou')]]";
    
    public boolean isPresentCerrarSesion() {
        return (isElementPresent(driver, By.xpath(XPathMenuCerrarSesion)));
    }
    
    public boolean isVisibleCerrarSesion() {
        return (isElementVisible(driver, By.xpath(XPathMenuCerrarSesion)));
    }
    
    public void clickCerrarSesion() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuCerrarSesion));
    }
    
    public boolean clickCerrarSessionIfLinkExists() throws Exception {
        boolean menuClicado = false;
        if (isPresentCerrarSesion()) {
        	moveToElement(By.xpath(XPathMenuCerrarSesion), driver);
            clickCerrarSesion();
            menuClicado = true;
        }
        
        return menuClicado;
    }    
    
    public boolean isPresentIniciarSesionUntil(int maxSecondsToWait) {
        return (isElementPresentUntil(driver, By.xpath(XPathMenuIniciarSesion), maxSecondsToWait)); 
    }
    
    public void clickIniciarSesion() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuIniciarSesion), TypeOfClick.javascript);
    }
    
    public void MoveAndclickIniciarSesion() throws Exception {
    	moveToElement(By.xpath(XPathMenuIniciarSesion), driver);
        clickIniciarSesion();
    }
    
    public void clickRegistrate() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuRegistrate));
    }
    
    public void clickMiCuenta() throws Exception {
    	boolean toOpen = true;
    	secCabecera.clickIconoMenuHamburguerMobil(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuMiCuenta), TypeOfClick.javascript);
    }
    
    public void clickFavoritosAndWait() throws Exception {
    	boolean toOpen = true;
    	secCabecera.clickIconoMenuHamburguerMobil(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuFavoritos), 3);
        PageFavoritos.isSectionArticlesVisibleUntil(2/*maxSecondsToWait*/, driver);
    }
    
    public boolean isPresentMiCuentaUntil(int maxSecondsToWait) {
        return (isElementPresentUntil(driver, By.xpath(XPathMenuMiCuenta), maxSecondsToWait));
    }
    
    public void clickCambioPais() throws Exception {
    	boolean toOpen = true;
        secCabecera.clickIconoMenuHamburguerMobil(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuCambioPais));
    }
    
    public boolean isPresentFavoritos() {
        return (isElementPresent(driver, By.xpath(XPathMenuFavoritos)));
    }    
    
    public boolean isPresentPedidos() {
        return (isElementPresent(driver, By.xpath(XPathMenuPedidos)));
    }
    
    public boolean isPresentMisCompras() {
        return (isElementPresent(driver, By.xpath(XPathMenuMisCompras)));
    }    
    
    public boolean isPresentAyuda() {
        return (isElementPresent(driver, By.xpath(XPathMenuAyuda)));
    }
    
    public boolean isPresentMangoLikesYou() {
    	return (WebdrvWrapp.isElementPresent(driver, By.xpath(XPathMenuMangoLikesYou)));
    }
    
    public void clickMangoLikesYou() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuMangoLikesYou));
    }
}
