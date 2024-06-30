package com.mng.robotest.testslegacy.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageBase { 
	
	private static final String XP_CAPA_MENUS = "//div[@id='account-menu']";
	
	public enum MenuUserDesktop implements ElementPage { 
		INICIAR_SESION (
			"//*[@data-testid='header.myAccount.login.button']"),
		
		//Pedir React ID
		REGISTRATE (
			XP_CAPA_MENUS + "//a[@data-testid='header.usermenu.register.click']"),
		
		MI_CUENTA (
			XP_CAPA_MENUS + "//a[@data-testid='header.userSubmenu.my_account']"),
		MIS_COMPRAS (
			XP_CAPA_MENUS + "//a[@data-testid='header.userSubmenu.my_purchases']"),
		
		MANGO_LIKES_YOU (
			XP_CAPA_MENUS + "//a[@data-testid='header.userSubmenu.mango_likes_you']"),
		AYUDA (
			XP_CAPA_MENUS + "//a[@data-testid='header.userSubmenu.help']"),
		CERRAR_SESION (
			XP_CAPA_MENUS + "//*[@data-testid='header.userSubmenu.logout']");
		
		By by;
		private MenuUserDesktop(String xpath) {
			by = By.xpath(xpath);
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
