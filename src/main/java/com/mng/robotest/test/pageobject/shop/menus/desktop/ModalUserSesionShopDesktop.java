package com.mng.robotest.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageBase { 
	
	private static final String XPATH_WRAPPER_USER_MENU = 
			"//*[@data-testid[contains(.,'header.userMenu.login')] or " + 
			"@class[contains(.,'user-icon-button')]]";
	
	private static final String XPATH_CAPA_MENUS = XPATH_WRAPPER_USER_MENU + "/div[@role='button']";
	
	public enum MenuUserDesktop implements ElementPage { 
		INICIAR_SESION (XPATH_CAPA_MENUS + "//div[@class[contains(.,'login-button')]]"),
		
		//Pedir React ID
		REGISTRATE (XPATH_CAPA_MENUS + "//span[@class='login-register-link' or @class[contains(.,'register-link')] or text()='Regístrate' or @class[contains(.,'RuqlF')]]"),
		MI_CUENTA (XPATH_CAPA_MENUS + "//div[@data-testid[contains(.,'mi_cuenta')]]"),
		MIS_COMPRAS (XPATH_CAPA_MENUS + "//div[@data-testid[contains(.,'mis_compras')]]"),	 
		MANGO_LIKES_YOU (XPATH_CAPA_MENUS + "//div[@data-testid[contains(.,'mango_likes_you')]]"),
		AYUDA (XPATH_CAPA_MENUS + "//div[@data-testid[contains(.,'ayuda')]]"),   
		CERRAR_SESION(XPATH_CAPA_MENUS + "//div[@data-testid[contains(.,'logout')] or @class[contains(.,'logout')]]");
		
		By by;
		private MenuUserDesktop(String xPath) {
			by = By.xpath(xPath);
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
		return state(Visible, XPATH_CAPA_MENUS).wait(seconds).check();
	}	
	
	public boolean isMenuInState(MenuUserDesktop menu, State state) {
		return state(state, menu.getBy()).check();
	}
	
	public boolean isMenuInStateUntil(MenuUserDesktop menu, State state, int seconds) {
		return state(state, menu.getBy()).wait(seconds).check();
	}
	
	public void wait1sForItAndclickMenu(MenuUserDesktop menu) {
		isMenuInStateUntil(menu, Clickable, 1);
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
	
	public void MoveAndclickMenu(MenuUserDesktop menu) {
		moveToElement(menu.getBy());
		clickMenu(menu);
	}
}
