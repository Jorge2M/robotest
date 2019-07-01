package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;

public class SecMenusUserDesktop extends WebdrvWrapp { 

	private final WebDriver driver;
	
    final static String idUserMenuLink = "userMenuTrigger";
    final static String XPathCapaMenus = "//div[@id='userMenuContainer' or @id[contains(.,'linksHeader')]]"; //Caso Shop y Outlet 
    
    public enum MenuUserDesktop { 
        ayuda (XPathCapaMenus + "//a[@href[contains(.,'/help/')]]"),    
        misCompras (XPathCapaMenus + "//a[@href[contains(.,'/mypurchases')]]"),    
        pedidos(XPathCapaMenus + "//a[@href[contains(.,'account/orders')]]"),    
        cerrarSesion(XPathCapaMenus + "//span[@class[contains(.,'__logout')]]"),
        favoritos(XPathCapaMenus + "//*[@class[contains(.,'userMenu__wishlist')] or @class[contains(.,'userMenu__favoritos')]]"),
        iniciarSesion (XPathCapaMenus + "//a[@data-origin='login']"),
        miCuenta (XPathCapaMenus + "//*[@class[contains(.,'userMenu__cuenta')]]"),
        registrate (XPathCapaMenus + "//a[@data-origin='register']"),
        mangoLikesYou (XPathCapaMenus + "//a[@href[contains(.,'mangolikesyou')]]");
        
        String xpath;
        private MenuUserDesktop(String xpath) {
        	this.xpath = xpath;
        }
        
        public String getXPath() {
        	return this.xpath;
        }
    }
    
    private	SecMenusUserDesktop(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static SecMenusUserDesktop getNew(WebDriver driver) {
    	return (new SecMenusUserDesktop(driver));
    }
    
    public boolean isPresentCerrarSesion() {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public void clickCerrarSesion() throws Exception {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }

    public boolean clickCerrarSessionIfLinkExists() throws Exception {
        boolean menuClicado = false;
        if (isPresentCerrarSesion()) {
        	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        	moveToElement(By.xpath(xpath), driver);
            clickCerrarSesion();
            menuClicado = true;
        }
        
        return menuClicado;
    }    
    
    public boolean isPresentIniciarSesionUntil(int maxSecondsToWait) {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
        return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));
    }    
    
    public void clickRegistrate() throws Exception {
    	String xpath = MenuUserDesktop.registrate.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public void clickIniciarSesion() throws Exception {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public void MoveAndclickIniciarSesion() throws Exception {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
    	moveToElement(By.xpath(xpath), driver);
        clickIniciarSesion();
    }
    
    public void clickMiCuenta() throws Exception {
    	String xpath = MenuUserDesktop.miCuenta.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public boolean isPresentMiCuentaUntil(int maxSecondsToWait) {
    	String xpath = MenuUserDesktop.miCuenta.getXPath();
        return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));
    }
    
    public void clickFavoritosAndWait() throws Exception {
    	String xpath = MenuUserDesktop.favoritos.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
        PageFavoritos.isSectionArticlesVisibleUntil(2, driver);
    }
    
    public boolean isPresentFavoritos() {
    	String xpath = MenuUserDesktop.favoritos.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }    
    
    public boolean isVisibleCerrarSesion() {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        return (isElementVisible(driver, By.xpath(xpath)));
    }
    
    public boolean isPresentPedidos() {
    	String xpath = MenuUserDesktop.pedidos.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public boolean isPresentMisCompras() {
    	String xpath = MenuUserDesktop.misCompras.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }    
    
    public boolean isPresentAyuda() {
    	String xpath = MenuUserDesktop.ayuda.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public void clickMangoLikesYou() throws Exception {
    	String xpath = MenuUserDesktop.mangoLikesYou.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public boolean isPresentMangoLikesYou() {
    	String xpath = MenuUserDesktop.mangoLikesYou.getXPath();
    	return (WebdrvWrapp.isElementPresent(driver, By.xpath(xpath)));
    }
    
    public void hoverLinkForShowMenu() {
    	By byElem = By.id(idUserMenuLink);
    	WebdrvWrapp.moveToElement(byElem, driver);
    }
}
