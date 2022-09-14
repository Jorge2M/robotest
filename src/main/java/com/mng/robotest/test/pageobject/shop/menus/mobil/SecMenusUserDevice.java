package com.mng.robotest.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;


public class SecMenusUserDevice extends PageBase {

	final Channel channel;
	final AppEcom app;
	final SecCabecera secCabecera;
	
	public SecMenusUserDevice(Channel channel, AppEcom app) {
		this.channel = channel;
		this.app = app;
		this.secCabecera = SecCabecera.getNew(channel, app);
	}
	
	public enum MenuUserDevice implements ElementPage {
		ayuda("//*[@data-label='ayuda']"),
		miscompras("//*[@data-label='mis-compras']"),
		pedidos("//*[@data-label='pedidos']"),
		cerrarsesion("//*[@data-label='cerrar_sesion']"),
		favoritos("//*[@data-label='favoritos']"),
		iniciarsesion("//*[@data-label='iniciar_sesion']"),
		registrate("//*[@data-label='iniciar_sesion']"),
		micuenta("//*[@data-label='mi_cuenta']"),
		mangolikesyou("//*[text()[contains(.,'Mango likes you')]]"),
		cambiopais("//*[@data-label='cambio_pais']");

		private static final String XPATH_CAPA_USER_NEW_MENU = "//ul[@class='menu-section-user-menu-links']";
		private static final String XPATH_CAPA_MENUS_OLD_MENU = "//ul[@class[contains(.,'menu-section-links')]]";
		private By byNewMenu;
		private By byOldMenu;
		MenuUserDevice(String xPath) {
			byNewMenu = By.xpath(XPATH_CAPA_USER_NEW_MENU + xPath);
			byOldMenu = By.xpath(XPATH_CAPA_MENUS_OLD_MENU + xPath);
		}

		@Override
		public By getBy() {
			throw new IllegalArgumentException("Undefined app");
		}
		
		@Override
		public By getBy(Channel channel, Enum<?> app) {
			if (app==AppEcom.outlet || channel==Channel.tablet) {
				return byOldMenu;
			}
			return byNewMenu;
		}
	}
	
	public boolean isMenuInState(MenuUserDevice menu, State state) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return (state(state, menu.getBy(channel, app)).check());
	}
	
	public boolean isMenuInStateUntil(MenuUserDevice menu, State state, int seconds) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return (state(state, menu.getBy(channel, app)).wait(seconds).check());
	}
	
	public void clickMenu(MenuUserDevice menu) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		click(menu.getBy(channel, app)).exec();
	}

	public boolean clickMenuIfinState(MenuUserDevice menu, State stateExpected) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		if (isMenuInState(menu, stateExpected)) {
			moveToElement(menu.getBy(channel, app));
			clickMenu(menu);
			return true;
		}
		return false;
	}	
	
	public void MoveAndclickMenu(MenuUserDevice menu) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		moveToElement(menu.getBy(channel, app));
		clickMenu(menu);
	}

}
