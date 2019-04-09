package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;


public class SecMenusUserMobil extends WebdrvWrapp {

    static String XPathCapaMenus = "//ul[@class[contains(.,'menu-section-links')]]";
    static String XPathMenuAyuda = XPathCapaMenus + "//a[@href[contains(.,'/help/')]]";
    static String XPathMenuMisCompras = XPathCapaMenus + "//a[@href[contains(.,'/mypurchases')]]";
    static String XPathMenuPedidos = XPathCapaMenus + "//a[@href[contains(.,'account/orders')]]";
    static String XPathMenuCambioPais = XPathCapaMenus + "//a[@href[contains(.,'/preHome.faces')]]";
    static String XPathMenuCerrarSesion = XPathCapaMenus + "//a[@href[contains(.,'/logout')]]";
    static String XPathMenuFavoritos = XPathCapaMenus + "//a[@href[contains(.,'/favorites')]]";
    static String XPathMenuIniciarSesion = XPathCapaMenus + "//a[@href[contains(.,'/login?')]]";
    static String XPathMenuMiCuenta = XPathCapaMenus + "//a[@data-label='mi_cuenta']";
    static String XPathMenuRegistrate = XPathCapaMenus + "//a[@href[contains(.,'/signup?')]]";
    
    public static boolean isPresentCerrarSesion(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuCerrarSesion)));
    }
    
    public static boolean isVisibleCerrarSesion(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathMenuCerrarSesion)));
    }
    
    public static void clickCerrarSesion(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuCerrarSesion));
    }
    
    public static boolean clickCerrarSessionIfLinkExists(WebDriver driver) throws Exception {
        boolean menuClicado = false;
        if (isPresentCerrarSesion(driver)) {
        	moveToElement(By.xpath(XPathMenuCerrarSesion), driver);
            clickCerrarSesion(driver);
            menuClicado = true;
        }
        
        return menuClicado;
    }    
    
    public static boolean isPresentIniciarSesionUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathMenuIniciarSesion), maxSecondsToWait));
    }
    
    public static void clickIniciarSesion(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuIniciarSesion), TypeOfClick.javascript);
    }
    
    public static void MoveAndclickIniciarSesion(WebDriver driver) throws Exception {
    	moveToElement(By.xpath(XPathMenuIniciarSesion), driver);
        clickIniciarSesion(driver);
    }
    
    public static void clickRegistrate(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuRegistrate));
    }
    
    public static void clickMiCuenta(AppEcom appE, WebDriver driver) throws Exception {
    	boolean toOpen = true;
    	SecCabeceraMobil.getNew(appE, driver).clickIconoMenuHamburguer(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuMiCuenta), TypeOfClick.javascript);
    }
    
    public static void clickFavoritosAndWait(AppEcom appE, WebDriver driver) throws Exception {
    	boolean toOpen = true;
    	SecCabeceraMobil.getNew(appE, driver).clickIconoMenuHamburguer(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuFavoritos), 3);
        PageFavoritos.isSectionArticlesVisibleUntil(2/*maxSecondsToWait*/, driver);
    }
    
    public static boolean isPresentMiCuentaUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathMenuMiCuenta), maxSecondsToWait));
    }
    
    public static void clickCambioPais(AppEcom appE, WebDriver driver) throws Exception {
    	boolean toOpen = true;
        SecCabeceraMobil.getNew(appE, driver).clickIconoMenuHamburguer(toOpen);
        clickAndWaitLoad(driver, By.xpath(XPathMenuCambioPais));
    }
    
    public static boolean isPresentFavoritos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuFavoritos)));
    }    
    
    public static boolean isPresentPedidos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuPedidos)));
    }
    
    public static boolean isPresentMisCompras(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuMisCompras)));
    }    
    
    public static boolean isPresentAyuda(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuAyuda)));
    }
}
