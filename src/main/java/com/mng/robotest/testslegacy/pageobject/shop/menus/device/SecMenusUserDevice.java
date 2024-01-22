package com.mng.robotest.testslegacy.pageobject.shop.menus.device;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;

public class SecMenusUserDevice extends PageBase {

	private final SecCabecera secCabecera = SecCabecera.make();

	private static final String XP_ITEM = "//a[@class[contains(.,'icon-outline')] or @class[contains(.,'UserLinks_item')]]";
	
	public enum MenuUserDevice implements ElementPage {
		
		AYUDA(
			XP_ITEM + "/self::*[@href[contains(.,'help')]]"),
		MIS_COMPRAS(
			XP_ITEM + "/self::*[@href[contains(.,'mypurchases')]]"),
		PEDIDOS(
			XP_ITEM + "/self::*[@href[contains(.,'mypurchases')]]"),
		CERRAR_SESION(
			"//a[@href[contains(.,'/logout')]]"),
		FAVORITOS(
			XP_ITEM + "/self::*[@href[contains(.,'favorites')]]"),
		INICIAR_SESION(
			XP_ITEM + "/self::*[@href[contains(.,'login')]]"),
		REGISTRATE(
			XP_ITEM + "/self::*[@href[contains(.,'login')]]"),
		MI_CUENTA(
			XP_ITEM + "/self::*[@href[contains(.,'mypurchases')]]"),
		MANGO_LIKES_YOU(
			XP_ITEM + "/self::*[@href[contains(.,'mangolikesyou')]]"),
		CAMBIO_PAIS(
			XP_ITEM + "/self::*[@href[contains(.,'preHome')]]");

		private static final String XP_CAPA_USER_MENU = "//ul";
		private By byMenu;
		MenuUserDevice(String xpath) {
			byMenu = By.xpath(XP_CAPA_USER_MENU + xpath);
		}

		@Override
		public By getBy() {
			return byMenu;
		}
		@Override
		public By getBy(Channel channel) {
			return getBy();
		}
	}
	
	public boolean isMenuInState(MenuUserDevice menu, State state) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return state(state, menu.getBy(app)).check();
	}
	
	public boolean isMenuInStateUntil(MenuUserDevice menu, State state, int seconds) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		return state(state, menu.getBy(app)).wait(seconds).check();
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
	
	public void moveAndclickMenu(MenuUserDevice menu) {
		secCabecera.clickIconoMenuHamburguerMobil(true);
		moveToElement(menu.getBy(app));
		clickMenu(menu);
	}

}
