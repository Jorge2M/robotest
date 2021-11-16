package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;

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
	
	@Override
	public List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaType sublineaType) 
	throws Exception {
		for (int i=0; i<3; i++) {
			try {
				List<WebElement> listMenus = getListMenusLinea(lineaType, sublineaType);
				return getDataListMenus(listMenus);
			}
			catch (StaleElementReferenceException e) {
				Log4jTM.getLogger().info("Problem getting menus " + lineaType + " / " + sublineaType, e);
			}
		}
		return null;
	}
	
	@Override
	public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel) throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea());
		clickMenuInHref(menu1rstLevel);
	}
	
	private List<WebElement> getListMenusLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
		String XPathMenusVisibles = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link);
		return (driver.findElements(By.xpath(XPathMenusVisibles)));
	}
}
