package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageObjTM { 
	
    final static String XPathCapaMenus = "//div[@class='user-icon-submenu']";
    
    public enum MenuUserDesktop implements ElementPage { 
        iniciarSesion (XPathCapaMenus + "//div[@class[contains(.,'login-button')]]"),
        registrate (XPathCapaMenus + "//span[@class='login-register-link']"),
        miCuenta (XPathCapaMenus + "//div[@class[contains(.,'mi_cuenta')]]"),
        misCompras (XPathCapaMenus + "//div[@class[contains(.,'mis_compras')]]"),     
        mangoLikesYou (XPathCapaMenus + "//div[@class[contains(.,'mango_likes_you')]]"),
        ayuda (XPathCapaMenus + "//div[@class[contains(.,'ayuda')]]"),   
        cerrarSesion(XPathCapaMenus + "//div[@class[contains(.,'logout')]]");
        
        String xpath;
        private MenuUserDesktop(String xpath) {
        	this.xpath = xpath;
        }
        
        public String getXPath() {
        	return this.xpath;
        }
    }
    
    private	ModalUserSesionShopDesktop(WebDriver driver) {
    	super(driver);
    }
    
    public static ModalUserSesionShopDesktop getNew(WebDriver driver) {
    	return (new ModalUserSesionShopDesktop(driver));
    }
    
    public boolean isVisible() {
    	return (state(Visible, By.xpath(XPathCapaMenus)).check());
    }
    
    public boolean isMenuInState(MenuUserDesktop menu, State state) {
    	return (isElementInState(menu, state, driver));
    }
    
    public boolean isMenuInStateUntil(MenuUserDesktop menu, State state, int maxSecondsWait) {
    	return (isElementInStateUntil(menu, state, maxSecondsWait, driver));
    }
    
    public void wait1sForItAndclickMenu(MenuUserDesktop menu) {
    	isMenuInStateUntil(menu, Clickable, 1);
    	clickAndWait(menu, driver);
    }
    
    public void clickMenu(MenuUserDesktop menu) throws Exception {
    	clickAndWait(menu, driver);
    }

    public boolean clickMenuIfinState(MenuUserDesktop menu, State stateExpected) throws Exception {
        if (isMenuInState(menu, stateExpected)) {
        	moveToElementPage(menu, driver);
            clickMenu(menu);
            return true;
        }
        return false;
    }    
    
    public void MoveAndclickMenu(MenuUserDesktop menu) throws Exception {
    	moveToElementPage(menu, driver);
        clickMenu(menu);
    }
}
