package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;


public class SecMenusUserDesktop extends WebdrvWrapp {

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
    
    public static boolean isPresentCerrarSesion(WebDriver driver) {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static void clickCerrarSesion(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }

    public static boolean clickCerrarSessionIfLinkExists(WebDriver driver) throws Exception {
        boolean menuClicado = false;
        if (isPresentCerrarSesion(driver)) {
        	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        	moveToElement(By.xpath(xpath), driver);
            clickCerrarSesion(driver);
            menuClicado = true;
        }
        
        return menuClicado;
    }    
    
    public static boolean isPresentIniciarSesionUntil(int maxSecondsToWait, WebDriver driver) {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
        return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));
    }    
    
    public static void clickRegistrate(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.registrate.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public static void clickIniciarSesion(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public static void MoveAndclickIniciarSesion(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.iniciarSesion.getXPath();
    	moveToElement(By.xpath(xpath), driver);
        clickIniciarSesion(driver);
    }
    
    public static void clickMiCuenta(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.miCuenta.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public static boolean isPresentMiCuentaUntil(int maxSecondsToWait, WebDriver driver) {
    	String xpath = MenuUserDesktop.miCuenta.getXPath();
        return (isElementPresentUntil(driver, By.xpath(xpath), maxSecondsToWait));
    }
    
    public static void clickFavoritosAndWait(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.favoritos.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
        PageFavoritos.isSectionArticlesVisibleUntil(2/*maxSecondsToWait*/, driver);
    }
    
    public static boolean isPresentFavoritos(WebDriver driver) {
    	String xpath = MenuUserDesktop.favoritos.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }    
    
    public static boolean isVisibleCerrarSesion(WebDriver driver) {
    	String xpath = MenuUserDesktop.cerrarSesion.getXPath();
        return (isElementVisible(driver, By.xpath(xpath)));
    }
    
    public static boolean isPresentPedidos(WebDriver driver) {
    	String xpath = MenuUserDesktop.pedidos.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static boolean isPresentMisCompras(WebDriver driver) {
    	String xpath = MenuUserDesktop.misCompras.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }    
    
    public static boolean isPresentAyuda(WebDriver driver) {
    	String xpath = MenuUserDesktop.ayuda.getXPath();
        return (isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static void clickMangoLikesYou(WebDriver driver) throws Exception {
    	String xpath = MenuUserDesktop.mangoLikesYou.getXPath();
        clickAndWaitLoad(driver, By.xpath(xpath));
    }
    
    public static boolean isPresentMangoLikesYou(WebDriver driver) {
    	String xpath = MenuUserDesktop.mangoLikesYou.getXPath();
    	return (WebdrvWrapp.isElementPresent(driver, By.xpath(xpath)));
    }
    
    public static boolean isPresentLoyaltyPointsUntil(int maxSecondsWait, WebDriver driver) throws Exception {
    	//TODO Workarround for manage shadow-dom Elements. Remove when WebDriver supports shadow-dom
    	WebElement shadowHost = driver.findElement(By.tagName("loyalty-user-menu"));
    	if (shadowHost!=null) {
    		for (int i=0; i<maxSecondsWait; i++) {
		    	Object shadowLoyaltyPoints = ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowHost);
		    	if (shadowLoyaltyPoints instanceof WebElement) {
			    	WebElement loyaltyPoints = (WebElement)shadowLoyaltyPoints;
			    	//TODO pendiente el grupo de Loyalty nos proporcione un id
			    	//TODO eliminar la versión de PRO cuando suba la de PRE
			    	String innerHTML = loyaltyPoints.getAttribute("innerHTML");
			    	if (innerHTML.contains("likes-you-have") /*versión PRO*/ ||
			    		(innerHTML.contains("Hola") && innerHTML.contains("Likes")) /*versión PRE*/) {
			    		return true;
			    	}
		    	} else {
		    		if (shadowLoyaltyPoints.toString().contains("likes-you-have")) {
		    			return true;
					}
		    	}

		    	Thread.sleep(1000);
    		}
    	}
    	
    	return false;
    }
    
    public static void hoverLinkForShowMenu(WebDriver driver) {
    	By byElem = By.id(idUserMenuLink);
    	WebdrvWrapp.moveToElement(byElem, driver);
    }
}
