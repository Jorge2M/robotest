package com.mng.robotest.testslegacy.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageBase { 
	
	private static final String XP_WRAPPER_USER_MENU = 
			"//*[@data-testid[contains(.,'header.userMenu.login')] or " + 
			"@class[contains(.,'user-icon-button')]]";
	
	private static final String XP_CAPA_MENUS = XP_WRAPPER_USER_MENU + "/div[@role='button']";
	private static final String XP_CAPA_MENUS_GENESIS = "//div[@class[contains(.,'AccountMenu')]]";
	
	//TODO pedidos data-testids para Genesis a Marcos (23-01-2024)
	public enum MenuUserDesktop implements ElementPage { 
		INICIAR_SESION (
			XP_CAPA_MENUS + "//button[@micro='Fukku']",
			"//*[@data-testid='header.myAccount.login.button']"),
		
		//Pedir React ID
		REGISTRATE (
			XP_CAPA_MENUS + "//span[@class='login-register-link' or @class[contains(.,'register-link')] or text()='Regístrate' or @class[contains(.,'RuqlF')]]",
			XP_CAPA_MENUS_GENESIS + "//a[@href[contains(.,'/signup?tab=register')]]"),
		
		MI_CUENTA (
			XP_CAPA_MENUS + "//div[@data-testid[contains(.,'mi_cuenta')]]",
			XP_CAPA_MENUS_GENESIS + "//a[@href[contains(.,'/account')]]"),
		MIS_COMPRAS (
			XP_CAPA_MENUS + "//div[@data-testid[contains(.,'mis_compras')] or @data-testid[contains(.,'my_purchases')]]",
			XP_CAPA_MENUS_GENESIS + "//a[@href[contains(.,'/mypurchases')]]"),
		
		MANGO_LIKES_YOU (
			XP_CAPA_MENUS + "//div[@data-testid[contains(.,'mango_likes_you')]]",
			""),
		AYUDA (
			XP_CAPA_MENUS + "//div[@data-testid[contains(.,'ayuda')]]",
			XP_CAPA_MENUS_GENESIS + "//a[@href[contains(.,'/help')]]"),
		CERRAR_SESION (
			XP_CAPA_MENUS + "//div[@data-testid[contains(.,'logout')] or @class[contains(.,'logout')]]",
			"(" + XP_CAPA_MENUS_GENESIS + "//*[@data-testid='next-s'])[last()]");
		
		By by;
		private MenuUserDesktop(String xPath, String xpathGenesis) {
			String xpathResult = xPath;
			if ("".compareTo(xpathGenesis)!=0) {
				xpathResult+= " | " + xpathGenesis;
			}
			by = By.xpath(xpathResult);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
	
	public boolean isVisible() {
		return isVisibleUntil(0);
	}
	public boolean isVisibleUntil(int seconds) {
		return state(VISIBLE, XP_CAPA_MENUS).wait(seconds).check();
	}	
	
	public boolean isMenuInState(MenuUserDesktop menu, State state) {
		return state(state, menu.getBy()).check();
	}
	
	public boolean isMenuInStateUntil(MenuUserDesktop menu, State state, int seconds) {
		return state(state, menu.getBy()).wait(seconds).check();
	}
	
	public void wait1sForItAndclickMenu(MenuUserDesktop menu) {
		isMenuInStateUntil(menu, CLICKABLE, 1);
		click(menu.getBy()).exec();
	}
	
	public void clickMenu(MenuUserDesktop menu) {
		click(menu.getBy()).exec();
	}

	public boolean clickMenuIfinState(MenuUserDesktop menu, State stateExpected) {
		if (isMenuInState(menu, stateExpected)) {
			moveToElement(menu.getBy());
			clickMenu(menu);
			return true;
		}
		return false;
	}	
	
	public void moveAndclickMenu(MenuUserDesktop menu) {
		moveToElement(menu.getBy());
		clickMenu(menu);
	}
}
