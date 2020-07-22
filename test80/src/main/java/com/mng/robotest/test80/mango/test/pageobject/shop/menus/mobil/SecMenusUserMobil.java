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
		this.secCabecera = SecCabecera.getNew(Channel.mobile, app, driver);
	}
	
	public static SecMenusUserMobil getNew(AppEcom app, WebDriver driver) {
		return (new SecMenusUserMobil(app, driver));
	}
	
	public enum MenuUserMobil implements ElementPage {
		ayuda("//span[@data-label='ayuda']", "//a[@href[contains(.,'/help/')]]"),
		miscompras("//span[@data-label='mis-compras']", "//a[@href[contains(.,'/mypurchases')]]"),
		pedidos("//span[@data-label='pedidos']", "//a[@href[contains(.,'account/orders')]]"),
		cerrarsesion("//span[@data-label='cerrar_sesion']", "//a[@href[contains(.,'/logout')]]"),
		favoritos("//span[@data-label='favoritos']", "//a[@href[contains(.,'/favorites')]]"),
		iniciarsesion("//span[@data-label='iniciar_sesion']", "//a[@href[contains(.,'/login?')]]"),
		registrate("//span[@data-label='iniciar_sesion']", "//a[@href[contains(.,'/signup?')]]"),
		micuenta("//span[@data-label='mi_cuenta']", "//a[@data-label='mi_cuenta']"),
		mangolikesyou("//span[text()[contains(.,'Mango likes you')]]", "//a[@href[contains(.,'/mangolikesyou')]]"),
		cambiopais("//span[@data-label='cambio_pais']", "//a[@href[contains(.,'/preHome.faces')]]");

		private String XPathCapaUserMenusShop = "//ul[@class='menu-section-user-menu-links']";
		private String XPathCapaMenusOutlet = "//ul[@class[contains(.,'menu-section-links')]]";
		private By byShop;
		private By byOutlet;
		MenuUserMobil(String xPathShop, String xPathOutlet) {
			byShop = By.xpath(XPathCapaUserMenusShop + xPathShop);
			byOutlet = By.xpath(XPathCapaMenusOutlet + xPathOutlet);
		}

		@Override
		public By getBy() {
			throw new IllegalArgumentException("Undefined app");
		}
		
		@Override
		public By getBy(Enum<?> app) {
			if (app==AppEcom.outlet) {
				return byOutlet;
			}
			return byShop;
		}
	}
    
    public boolean isMenuInState(MenuUserMobil menu, State state) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (state(state, menu.getBy(app)).check());
    }
    
    public boolean isMenuInStateUntil(MenuUserMobil menu, State state, int maxSeconds) {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	return (state(state, menu.getBy(app)).wait(maxSeconds).check());
    }
    
    public void clickMenu(MenuUserMobil menu) {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	click(menu.getBy(app)).exec();
    }

    public boolean clickMenuIfinState(MenuUserMobil menu, State stateExpected) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
        if (isMenuInState(menu, stateExpected)) {
        	moveToElement(menu.getBy(app), driver);
            clickMenu(menu);
            return true;
        }
        return false;
    }    
    
    public void MoveAndclickMenu(MenuUserMobil menu) throws Exception {
    	secCabecera.clickIconoMenuHamburguerMobil(true);
    	moveToElement(menu.getBy(app), driver);
        clickMenu(menu);
    }

}
