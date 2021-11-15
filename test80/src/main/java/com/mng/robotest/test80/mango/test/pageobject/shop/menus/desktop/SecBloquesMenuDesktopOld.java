package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;

public class SecBloquesMenuDesktopOld extends SecBloquesMenuDesktop {
	
	public SecBloquesMenuDesktopOld(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	@Override
	public boolean goToMenuAndCheckIsVisible(Menu1rstLevel menu1rstLevel) {
		hoverLineaMenu(menu1rstLevel);
		String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).wait(1).check());
	}
	
	@Override
	public void clickMenu(Menu1rstLevel menu1rstLevel) {
		clickEstandarMenu(menu1rstLevel);
	}
	
	@Override
	public void makeMenusGroupVisible(LineaType lineaType, GroupMenu bloque) {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, null);
	}
}
