package com.mng.robotest.test.pageobject.shop.menus.device;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;


public class SecMenusUserDevice extends PageBase {

	private final SecCabecera secCabecera = SecCabecera.getNew(channel, app);
	
	public enum MenuUserDevice implements ElementPage {
		ayuda(
			"//*[@data-label='ayuda']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'help')]]"),
		miscompras(
			"//*[@data-label='mis-compras']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'mypurchases')]]"),
		pedidos(
			"//*[@data-label='pedidos']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'mypurchases')]]"),
		cerrarsesion(
			"//*[@data-label='cerrar_sesion']",
			"//a[@href[contains(.,'/logout')]]"),
		favoritos(
			"//*[@data-label='favoritos']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'favorites')]]"),
		iniciarsesion(
			"//*[@data-label='iniciar_sesion']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'login')]]"),
		registrate(
			"//*[@data-label='iniciar_sesion']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'login')]]"),
		micuenta(
			"//*[@data-label='mi_cuenta']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'account')]]"),
		mangolikesyou(
			"//*[text()[contains(.,'Mango likes you')]]",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'LOYALTY')]]"),
		cambiopais(
			"//*[@data-label='cambio_pais']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'preHome')]]");

		private static final String XPATH_CAPA_USER_MENU_OUTLET = "//ul[@class='menu-section-user-menu-links']";
		private static final String XPATH_CAPA_USER_MENU_SHOP = "//ul";
		private By byMenuOutlet;
		private By byMenuShop;
		MenuUserDevice(String xpathOutlet, String xpathShop) {
			byMenuOutlet = By.xpath(XPATH_CAPA_USER_MENU_OUTLET + xpathOutlet);
			byMenuShop = By.xpath(XPATH_CAPA_USER_MENU_SHOP + xpathShop);
		}

		@Override
		public By getBy() {
			throw new IllegalArgumentException("Undefined app");
		}
		
		@Override
		public By getBy(Enum<?> app) {
			if (app==AppEcom.outlet) {
				return byMenuOutlet;
			}
			return byMenuShop;
		}
		@Override
		public By getBy(Channel channel, Enum<?> app) {
			return getBy(app);
		}
	}
	
	public boolean isMenuInState(MenuUserDevice menu, State state) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return (state(state, menu.getBy(app)).check());
	}
	
	public boolean isMenuInStateUntil(MenuUserDevice menu, State state, int seconds) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return (state(state, menu.getBy(app)).wait(seconds).check());
	}
	
	public void clickMenu(MenuUserDevice menu) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		click(menu.getBy(app)).exec();
	}

	public boolean clickMenuIfinState(MenuUserDevice menu, State stateExpected) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		if (isMenuInState(menu, stateExpected)) {
			moveToElement(menu.getBy(app));
			clickMenu(menu);
			return true;
		}
		return false;
	}	
	
	public void MoveAndclickMenu(MenuUserDevice menu) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		moveToElement(menu.getBy(app));
		clickMenu(menu);
	}

}
