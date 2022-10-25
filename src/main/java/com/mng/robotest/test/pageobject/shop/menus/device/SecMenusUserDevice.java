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
		AYUDA(
			"//*[@data-label='ayuda']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'help')]]"),
		MIS_COMPRAS(
			"//*[@data-label='mis-compras']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'mypurchases')]]"),
		PEDIDOS(
			"//*[@data-label='pedidos']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'mypurchases')]]"),
		CERRAR_SESION(
			"//*[@data-label='cerrar_sesion']",
			"//a[@href[contains(.,'/logout')]]"),
		FAVORITOS(
			"//*[@data-label='favoritos']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'favorites')]]"),
		INICIAR_SESION(
			"//*[@data-label='iniciar_sesion']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'login')]]"),
		REGISTRATE(
			"//*[@data-label='iniciar_sesion']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'login')]]"),
		MI_CUENTA(
			"//*[@data-label='mi_cuenta']",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'account')]]"),
		MANGO_LIKES_YOU(
			"//*[text()[contains(.,'Mango likes you')]]",
			"//a[@class[contains(.,'icon-outline')] and @href[contains(.,'mangolikesyou')]]"),
		CAMBIO_PAIS(
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
	
	public boolean isMenuInState(MenuUserDevice menu, State state) {
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

	public boolean clickMenuIfinState(MenuUserDevice menu, State stateExpected) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		if (isMenuInState(menu, stateExpected)) {
			moveToElement(menu.getBy(app));
			clickMenu(menu);
			return true;
		}
		return false;
	}	
	
	public void MoveAndclickMenu(MenuUserDevice menu) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		moveToElement(menu.getBy(app));
		clickMenu(menu);
	}

}
