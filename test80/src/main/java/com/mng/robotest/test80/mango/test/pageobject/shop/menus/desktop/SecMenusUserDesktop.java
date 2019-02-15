package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;


public class SecMenusUserDesktop extends WebdrvWrapp {

    static String XPathCapaMenus = "//div[@id='userMenuContainer' or @id[contains(.,'linksHeader')]]"; //Caso Shop y Outlet 
    static String XPathMenuAyuda = XPathCapaMenus + "//a[@href[contains(.,'/help/')]]";    
    static String XPathMenuMisCompras = XPathCapaMenus + "//a[@href[contains(.,'/mypurchases')]]";    
    static String XPathMenuPedidos = XPathCapaMenus + "//a[@href[contains(.,'account/orders')]]";    
    static String XPathMenuCerrarSesion = XPathCapaMenus + "//span[@class[contains(.,'__logout')]]";
    static String XPathMenuFavoritos = XPathCapaMenus + "//*[@class[contains(.,'userMenu__wishlist')] or @class[contains(.,'userMenu__favoritos')]]";
    static String XPathMenuIniciarSesion = XPathCapaMenus + "//a[@data-origin='login']";
    static String XPathMenuMiCuenta = XPathCapaMenus + "//*[@class[contains(.,'userMenu__cuenta')]]";
    static String XPathMenuRegistrate = XPathCapaMenus + "//a[@data-origin='register']";
    
    public static boolean isPresentCerrarSesion(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuCerrarSesion)));
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
    
    public static void clickRegistrate(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuRegistrate));
    }
    
    public static void clickIniciarSesion(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuIniciarSesion));
    }
    
    public static void MoveAndclickIniciarSesion(WebDriver driver) throws Exception {
    	moveToElement(By.xpath(XPathMenuIniciarSesion), driver);
        clickIniciarSesion(driver);
    }
    
    public static void clickMiCuenta(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuMiCuenta));
    }
    
    public static boolean isPresentMiCuentaUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathMenuMiCuenta), maxSecondsToWait));
    }
    
    public static void clickFavoritosAndWait(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathMenuFavoritos));
        PageFavoritos.isSectionArticlesVisibleUntil(2/*maxSecondsToWait*/, driver);
    }
    
    public static boolean isPresentFavoritos(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathMenuFavoritos)));
    }    
    
    public static boolean isVisibleCerrarSesion(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathMenuCerrarSesion)));
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
