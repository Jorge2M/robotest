package com.mng.robotest.testslegacy.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.tests.domains.base.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageBase { 
	
	public enum MenuUserDesktop implements ElementPage { 
		INICIAR_SESION ("//*[@data-testid='header.myAccount.login.button']"),
		REGISTRATE ("//a[@data-testid='header.usermenu.register.click']"),
		MI_CUENTA ("//a[@data-testid='header.userSubmenu.my_account']"),
		MIS_COMPRAS ("//a[@data-testid='header.userSubmenu.my_purchases']"),
		MANGO_LIKES_YOU ("//*[@data-testid='header.userSubmenu.mango_likes_you']"),
		AYUDA ("//a[@data-testid='header.userSubmenu.help']"),
		CERRAR_SESION ("//*[@data-testid='header.userSubmenu.logout']");
		
		By by;
		private MenuUserDesktop(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
	
	private static final String XP_USER_LOGGED = "//p[@class[contains(.,'userLogged')]]";
	
	private String getXPathUserLogged(String nameUser) {
		return XP_USER_LOGGED + "//self::*[text()[contains(.,'" + nameUser + "')]]";
	}
	
	public boolean isVisible() {
		return isVisibleUntil(0);
	}
	public boolean isVisibleUntil(int seconds) {
		return isMenuInStateUntil(MenuUserDesktop.AYUDA, VISIBLE, seconds);
	}	
	
	public boolean isMenuInState(MenuUserDesktop menu, State state) {
		return state(state, menu.getBy()).check();
	}
	
	public boolean isMenuInStateUntil(MenuUserDesktop menu, State state, int seconds) {
		return state(state, menu.getBy()).wait(seconds).check();
	}
	
	public boolean isNameVisible(String name, int seconds) {
		return state(VISIBLE, getXPathUserLogged(name)).wait(seconds).check();
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
