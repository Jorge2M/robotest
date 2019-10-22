package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.wrapper.ElementPage;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;

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
	
    public enum MenuUserMobil implements ElementPage {
    	ayuda("//a[@href[contains(.,'/help/')]]"),
		miscompras("//a[@href[contains(.,'/mypurchases')]]"),
    	pedidos("//a[@href[contains(.,'account/orders')]]"),
		cerrarsesion("//a[@href[contains(.,'/logout')]]"),
		favoritos("//a[@href[contains(.,'/favorites')]]"),
		iniciarsesion("//a[@href[contains(.,'/login?')]]"),
		registrate("//a[@href[contains(.,'/signup?')]]"),
		micuenta("//a[@data-label='mi_cuenta']"),
		mangolikesyou("//a[@href[contains(.,'/mangolikesyou')]]"),
		cambiopais("//a[@href[contains(.,'/preHome.faces')]]");

        private String XPathCapaMenus = "//ul[@class[contains(.,'menu-section-links')]]";
		private String xPath;
		MenuUserMobil(String xPath) {
			this.xPath = XPathCapaMenus + xPath;
		}

		public String getXPath() {
			return this.xPath;
		}
	}
    
    public boolean isMenuInState(MenuUserMobil menu, StateElem state) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (isElementInState(menu, state, driver));
    }
    
    public boolean isMenuInStateUntil(MenuUserMobil menu, StateElem state, int maxSecondsWait) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (isElementInStateUntil(menu, state, maxSecondsWait, driver));
    }
    
    public void clickMenu(MenuUserMobil menu) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	clickAndWait(menu, driver);
    }

    public boolean clickMenuIfinState(MenuUserMobil menu, StateElem stateExpected) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
        if (isMenuInState(menu, stateExpected)) {
        	moveToElementPage(menu, driver);
            clickMenu(menu);
            return true;
        }
        return false;
    }    
    
    public void MoveAndclickMenu(MenuUserMobil menu) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	moveToElementPage(menu, driver);
        clickMenu(menu);
    }

}
