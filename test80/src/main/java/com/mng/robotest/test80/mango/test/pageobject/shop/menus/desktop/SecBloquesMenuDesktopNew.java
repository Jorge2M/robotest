package com.mng.robotest.test80.mango.test.pageobject.shop.menus.desktop;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.Linea.LineaType;
import com.mng.robotest.test80.mango.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;
import com.mng.robotest.test80.mango.test.utils.checkmenus.DataScreenMenu;


public class SecBloquesMenuDesktopNew extends SecBloquesMenuDesktop {
	
	//Example row: "abrigos_she" / "prendas_she"
	private static final Map<String, String> storedMenus = new ConcurrentHashMap<>(); 
	
	//private final static String XPathContainerMenus = "//div[@class[contains(.,'section-detail-nav-container')]]";
	private final static String XPathGroupItemWithMenus = "//div[@role='menuitem' and not(@class[contains(.,'item-link')]) and not(@id[contains(.,'nav-sections')])]";
	private final static String XPathGroupItemLink = "//div[@role='menuitem' and @class[contains(.,'item-link')] and not(@id[contains(.,'nav-sections')])]";
	private final static String XPathLinkMenuGroup = XPathGroupItemLink + "//a[@class[contains(.,'menu-item')]]";
	
	public SecBloquesMenuDesktopNew(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	private String getXPathLinkMenuGroup(Menu1rstLevel menu1rstLevel) {
		return XPathLinkMenuGroup + "//self::*[@id='" + menu1rstLevel.getDataGaLabelMenuSuperiorDesktop().toLowerCase() + "']";
	}

	@Override
	public boolean goToMenuAndCheckIsVisible(Menu1rstLevel menu1rstLevel) throws Exception {
		hoverLineaMenu(menu1rstLevel);
		if (isMenuVisible(menu1rstLevel, 1)) {
			return true;
		}
		selectGroupMenu(menu1rstLevel);	
		return isMenuVisible(menu1rstLevel, 1);
	}
	
	@Override
	public void clickMenu(Menu1rstLevel menu1rstLevel) {
		if (isMenuLinkVisible(menu1rstLevel, 0)) {
			clickEstandarMenu(menu1rstLevel);
			return;
		}
		clickBlockMenu(menu1rstLevel);
	}
	
	@Override
	public void makeMenusGroupVisible(LineaType lineaType, GroupMenu bloque) {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, null);
		selectGroupMenu(lineaType.name(), bloque.name().toLowerCase() + "_" + lineaType.name().toLowerCase());
	}
	
	@Override
	public List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaType sublineaType) 
	throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
		List<DataScreenMenu> listMenus = new ArrayList<>();
		List<WebElement> groups = driver.findElements(By.xpath(XPathGroupItemWithMenus));
		for (WebElement group : groups) {
			List<WebElement> listMenusGroup = getMenusGroupAndStore(lineaType, sublineaType, group);
			listMenus.addAll(getDataListMenus(listMenusGroup));
		}
		return listMenus;
	}
	
	@Override
	public void seleccionarMenuXHref(Menu1rstLevel menu1rstLevel) throws Exception {
		goToMenuAndCheckIsVisible(menu1rstLevel);
		clickMenuInHref(menu1rstLevel);
	}
	
	private void clickBlockMenu(Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathLinkMenuGroup(menu1rstLevel);
		click(By.xpath(xpathMenu)).exec();
	}
	
	private boolean isMenuVisible(Menu1rstLevel menu1rstLevel, int maxSeconds) {
		if (isMenuGroupVisible(menu1rstLevel)) {
			return true;
		}
		return isMenuLinkVisible(menu1rstLevel, maxSeconds);
	}
	
	private boolean isMenuGroupVisible(Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathLinkMenuGroup(menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).check());
	}
	
	private boolean isMenuLinkVisible(Menu1rstLevel menu1rstLevel, int maxSeconds) {
		String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).wait(maxSeconds).check());
	}
	
	private void selectGroupMenu(Menu1rstLevel menu1rstLevel) throws Exception {
		String group_line = getGroupMenu(menu1rstLevel);
		selectGroupMenu(menu1rstLevel.getLinea().name(), group_line);
	}
	
	private void selectGroupMenu(String linea, String group_line) {
		click(By.xpath(XPathGroupItemWithMenus + "//self::*[@data-brand='" + group_line + "']")).exec();
	}
	
	private String getGroupMenu(Menu1rstLevel menu1rstLevel) throws Exception {		
		String keyMenu = getKeyMenu(menu1rstLevel);
		String group = storedMenus.get(keyMenu);
		if (group==null) {
			storeMenusGroups(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea());
		}
		return storedMenus.get(keyMenu);
	}

	
	
	private void storeMenusGroups(LineaType lineaType, SublineaType sublineaType) throws Exception {
		List<WebElement> groups = driver.findElements(By.xpath(XPathGroupItemWithMenus));
		for (WebElement group : groups) {
			getMenusGroupAndStore(lineaType, sublineaType, group);
		}
	}
	
	private List<WebElement> getMenusGroupAndStore(LineaType lineaType, SublineaType sublineaType, WebElement group) 
	throws Exception {
		click(group).exec();
		List<WebElement> menus = getListMenusLinea(lineaType, sublineaType);
		for (WebElement menuElem : menus) {
			storeMenu(lineaType.name(), group, menuElem);
		}
		return menus;
	}
	
	private List<WebElement> getListMenusLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		String XPathMenusVisibles = getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link);
		return (driver.findElements(By.xpath(XPathMenusVisibles)));
	}
	
	private void storeMenu(String linea, WebElement group, WebElement menu) {
		String menu_linea = menu.getAttribute("id");
		String group_linea = group.getAttribute("data-brand");
		storedMenus.put(menu_linea, group_linea);
	}
	
	private String getKeyMenu(Menu1rstLevel menu1rstLevel) {
		if (menu1rstLevel.getId()!=null) {
			return menu1rstLevel.getId();
		}
		return getKeyMenu(menu1rstLevel.getLinea().name(), menu1rstLevel.getNombre());
	}
	
	private String getKeyMenu(String linea, String nombre) {
		return nombre.toLowerCase() + "_" + linea.toLowerCase();
	}
	
}
