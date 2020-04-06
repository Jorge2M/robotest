package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;

public class SecMenusUserMobil extends PageObjTM {

	final AppEcom app;
	final SecCabecera secCabecera;
	
	private SecMenusUserMobil(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
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
		private By by;
		MenuUserMobil(String xPath) {
			by = By.xpath(XPathCapaMenus + xPath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}
    
    public boolean isMenuInState(MenuUserMobil menu, State state) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (state(state, menu.getBy()).check());
    }
    
    public boolean isMenuInStateUntil(MenuUserMobil menu, State state, int maxSeconds) {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (state(state, menu.getBy()).wait(maxSeconds).check());
    }
    
    public void clickMenu(MenuUserMobil menu) {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	click(menu.getBy()).exec();
    }

    public boolean clickMenuIfinState(MenuUserMobil menu, State stateExpected) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
        if (isMenuInState(menu, stateExpected)) {
        	moveToElement(menu.getBy(), driver);
            clickMenu(menu);
            return true;
        }
        return false;
    }    
    
    public void MoveAndclickMenu(MenuUserMobil menu) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	moveToElement(menu.getBy(), driver);
        clickMenu(menu);
    }

}
