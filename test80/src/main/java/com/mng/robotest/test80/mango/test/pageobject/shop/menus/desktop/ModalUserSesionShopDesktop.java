package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalUserSesionShopDesktop extends PageObjTM { 
	
	final static String XPathCapaMenus = "//div[@class[contains(.,'user-sub-menu')]]";
	
	public enum MenuUserDesktop implements ElementPage { 
		iniciarSesion (XPathCapaMenus + "//div[@class[contains(.,'login-button')]]"),
		registrate (XPathCapaMenus + "//span[@class='login-register-link' or @class[contains(.,'register-link')]]"),
		miCuenta (XPathCapaMenus + "//div[@class[contains(.,'mi_cuenta')]]"),
		misCompras (XPathCapaMenus + "//div[@class[contains(.,'mis_compras')]]"),	 
		mangoLikesYou (XPathCapaMenus + "//div[@class[contains(.,'mango_likes_you')]]"),
		ayuda (XPathCapaMenus + "//div[@class[contains(.,'ayuda')]]"),   
		cerrarSesion(XPathCapaMenus + "//div[@class[contains(.,'logout')]]");
		
		By by;
		private MenuUserDesktop(String xPath) {
			by = By.xpath(xPath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
	
	private	ModalUserSesionShopDesktop(WebDriver driver) {
		super(driver);
	}
	
	public static ModalUserSesionShopDesktop getNew(WebDriver driver) {
		return (new ModalUserSesionShopDesktop(driver));
	}
	
	public boolean isVisible() {
		return (state(Visible, By.xpath(XPathCapaMenus)).check());
	}
	
	public boolean isMenuInState(MenuUserDesktop menu, State state) {
		return (state(state, menu.getBy()).check());
	}
	
	public boolean isMenuInStateUntil(MenuUserDesktop menu, State state, int maxSeconds) {
		return (state(state, menu.getBy()).wait(maxSeconds).check());
	}
	
	public void wait1sForItAndclickMenu(MenuUserDesktop menu) {
		isMenuInStateUntil(menu, Clickable, 1);
		click(menu.getBy()).exec();
	}
	
	public void clickMenu(MenuUserDesktop menu) {
		click(menu.getBy()).exec();
	}

	public boolean clickMenuIfinState(MenuUserDesktop menu, State stateExpected) throws Exception {
		if (isMenuInState(menu, stateExpected)) {
			moveToElement(menu.getBy(), driver);
			clickMenu(menu);
			return true;
		}
		return false;
	}	
	
	public void MoveAndclickMenu(MenuUserDesktop menu) throws Exception {
		moveToElement(menu.getBy(), driver);
		clickMenu(menu);
	}
}
