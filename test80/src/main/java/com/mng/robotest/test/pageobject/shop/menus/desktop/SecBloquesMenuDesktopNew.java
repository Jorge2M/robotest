package com.mng.robotest.test.pageobject.shop.menus.desktop;


import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap.GroupMenu;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;


public class SecBloquesMenuDesktopNew extends SecBloquesMenuDesktop {
	
	//Example row: "abrigos_she" / "prendas_she"
	private static final Map<String, String> storedMenus = new ConcurrentHashMap<>(); 
	
	public enum MenusFromGroup {Family, Subfamily, All}
	
	//TODO React. 15-diciembre-2021: identificadores react solicitados a David Massa por Teams
	// están listos, cuando suban a PRE se podrán cambiar:
	// -> JUhkW -> data-testid = 'section'
	// -> o3ud7 -> data-testid = 'sub-familys'
	private final static String XPathWrapperGlobal = "//div[@id='headerPortalContainer']";
	
	private final static String XPathCapaMenusFamily = "//ul[@data-testid[contains(.,'section.family')]]";
	private final static String XPathCapaMenusSubfamily = "//ul[@data-testid[contains(.,'section.subfamily')]]";
	private final static String XPathCapaMenus = "//ul[@data-testid[contains(.,'section.subfamily')] or @data-testid[contains(.,'section.family')]]";
	private final static String XPathContainerGroups = XPathWrapperGlobal + XPathCapaMenus + "/li[not(@id[contains(.,'sections')])]/..";
	
	private final static String XPathGroupSection = XPathContainerGroups + "/li[@data-testid[contains(.,'section.menu')]]";
	private final static String XPathGroupLink = XPathContainerGroups + "/li[@data-testid[contains(.,'section.link')]]";
	private final static String XPathMenuLink = XPathCapaMenus + "/li[@data-testid[contains(.,'link')]]";
	private final static String XPathRightImage = XPathWrapperGlobal + "//a/img/..";

	
	public SecBloquesMenuDesktopNew(AppEcom app, WebDriver driver) {
		super(app, driver);
	}
	
	private String getXPathLinkMenuGroup(Menu1rstLevel menu1rstLevel) {
		String idMenu = getHtmlIdMenu(menu1rstLevel);
		return XPathGroupLink + "//a[" + 
			"@data-testid[contains(.,'header.section.link." + idMenu + "')] or " +
		    "@data-testid[contains(.,'header.section.link." + idMenu.toLowerCase() + "')]]";
	}
	
	private String getHtmlIdMenu(Menu1rstLevel menu1rstLevel) {
		String id = "";
		if (menu1rstLevel.getId() != null) {
			id = menu1rstLevel.getId();
		} else {
			String labelLinea = getLabelLinea(menu1rstLevel.getLinea(), menu1rstLevel.getSublinea());
			id = menu1rstLevel.getDataGaLabelMenuSuperiorDesktop() + "_" + labelLinea;
		}
		
		if (id.contains("nuevo_")) {
			return  "nuevo";
		}
		return id;
	}
	
	private String getLabelLinea(LineaType linea, SublineaType sublinea) {
		if (sublinea==null) {
			return linea.name();
		}

		//Está mal en el HTML, los menús de Teen en shop tienen los ids de Outlet
		if (sublinea==SublineaType.teen_nina || sublinea==SublineaType.teen_nino) {
			return sublinea.getId(AppEcom.outlet);
		} 
		return sublinea.getId(app);
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
	public String getXPathCapaMenusLinea(String idLinea) {
		return getXPathCapaMenusLinea(idLinea, MenusFromGroup.All);
	}
	
	private String getXPathCapaMenusLinea(String idLinea, MenusFromGroup group) {
		String liLinea = "//li[@id[contains(.,'_" + idLinea + "')]]/..";
		switch (group) {
		case Family:
			return XPathCapaMenusFamily + liLinea; 
		case Subfamily:
			return XPathCapaMenusSubfamily + liLinea; 
		default:
			return XPathCapaMenus + liLinea;
		}
	}
	
	@Override
	public String getXPathLinkMenuSuperiorRelativeToCapa(TypeMenuDesktop typeMenu) {
		switch (typeMenu) {
		case Link:
			return XPathMenuLink + "//a";
		case Banner:
		default:
			return XPathRightImage;
		}
	}
	
	@Override
	public List<DataScreenMenu> getListDataScreenMenus(LineaType lineaType, SublineaType sublineaType) 
	throws Exception {
		secLineasMenu.hoverLineaAndWaitForMenus(lineaType, sublineaType);
		List<DataScreenMenu> listMenus = new ArrayList<>();
		List<WebElement> groups = driver.findElements(By.xpath(XPathGroupSection));
		for (int i=1; i<=groups.size(); i++) {
			WebElement group = driver.findElement(By.xpath("(" + XPathGroupSection + ")[" + i + "]"));
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
	
	@Override
	public List<WebElement> getListMenusLineaBloque(LineaType lineaType, GroupMenu bloque) throws Exception {
		return getListMenusLineaBloque(lineaType, bloque, MenusFromGroup.All);
	}

	public List<WebElement> getListMenusLineaBloque(LineaType lineaType, GroupMenu bloque, MenusFromGroup group) throws Exception {
		makeMenusGroupVisible(lineaType, bloque);
		String xpathMenuLinea = getXPathCapaMenusLinea(lineaType.name(), group);
		String xpathEntradaMenu = "//li[@data-testid[contains(.,'section')] and @id='" + bloque + "_" + lineaType.name() + "']/../li[@data-testid[contains(.,'link')]]/a";
		List<WebElement> listMenus = driver.findElements(By.xpath(xpathMenuLinea + xpathEntradaMenu));
		return (listMenus);		
	}
	
	@Override
	public String getXPathMenuSuperiorLinkVisible(Menu1rstLevel menu1rstLevel) {
		LineaType lineaMenu = menu1rstLevel.getLinea();
		SublineaType sublineaMenu = menu1rstLevel.getSublinea();
		String id = menu1rstLevel.getId();
		if (id == null) {
			id = menu1rstLevel.getDataGaLabelMenuSuperiorDesktop();
		}
		String xpathMenuVisible = getXPathMenusSuperiorLinkVisibles(lineaMenu, sublineaMenu, TypeMenuDesktop.Link);
		if (id.contains("'")) {
			//En el caso de que el data_ga_label contenga ' 
			//no parece existir carácter de escape, así que hemos de desglosar en 2 bloques y aplicar el 'contains' en cada uno
			int posApostrophe = id.indexOf("'");
			String block1 = menu1rstLevel.getLinea() + "_" + id.substring(0, posApostrophe);
			String block2 = id.substring(posApostrophe + 1);
			return (
				xpathMenuVisible + 
				"[@data-testid[contains(.,'" + block1 + "')] and @data-testid[contains(.,'" + block2 + "')]]");
		}

		String idLineaMenu = id + "_" + menu1rstLevel.getLinea();
		return (
			xpathMenuVisible + 
			"[@data-testid[contains(.,'" + idLineaMenu + "')] or " + 
			"@data-testid[contains(.,'" + idLineaMenu.toLowerCase() + "')]]");
	}
	
	@Override
	public String getXPathCapaMenusLinea(LineaType lineaId) {
		return getXPathCapaMenusLinea(lineaId.getId3());
	}
	
	@Override
	public String getXPathCapaMenusSublinea(SublineaType sublineaType) {
		return (getXPathCapaMenusLinea(sublineaType.getId(app)));
	}
	
	@Override
	public String getXPathMenusSuperiorLinkVisibles(LineaType lineaType, SublineaType sublineaType, TypeMenuDesktop typeMenu) {
//		String xpathCapaMenuLinea = "";
//		if (sublineaType==null) {
//			xpathCapaMenuLinea = getXPathCapaMenusLinea(lineaType);
//		} else {
//			xpathCapaMenuLinea = getXPathCapaMenusSublinea(sublineaType);
//		}

		return getXPathLinkMenuSuperiorRelativeToCapa(typeMenu);
	}
	
//	private void makeMenusInvisible() {
//		WebElement wrapperMenus = driver.findElement(By.xpath(XPathWrapperGlobal));
//		List<WebElement> elementsAbove = driver.findElements(with(By.tagName("div")).below(wrapperMenus));
//		for (WebElement element : elementsAbove) {
//			moveToElement(element, driver);
//			if (state(State.Invisible, By.xpath(XPathContainerGroups)).wait(1).check()) {
//				break;
//			}
//		}
//	}

	
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
	
	private void clickBlockMenu(Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathLinkMenuGroup(menu1rstLevel);
		click(By.xpath(xpathMenu)).exec();
	}
	
	private boolean isMenuLinkVisible(Menu1rstLevel menu1rstLevel, int maxSeconds) {
		String xpathMenu = getXPathMenuSuperiorLinkVisible(menu1rstLevel);
		return (state(Visible, By.xpath(xpathMenu)).wait(maxSeconds).check());
	}
	
	private void selectGroupMenu(Menu1rstLevel menu1rstLevel) throws Exception {
		String group_line = getGroupMenu(menu1rstLevel);
		if (group_line!=null) {
			selectGroupMenu(menu1rstLevel.getLinea().name(), group_line);
		}
	}
	
	private void selectGroupMenu(String linea, String group_line) {
		waitMillis(500);
		click(By.xpath(XPathGroupSection + "//self::*[@id='" + group_line + "']/span")).exec();
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
		int numGroups = driver.findElements(By.xpath(XPathGroupSection)).size();
		for (int i=1; i<=numGroups; i++) {
			WebElement group = driver.findElement(By.xpath("(" + XPathGroupSection + ")[" + i + "]"));
			getMenusGroupAndStore(lineaType, sublineaType, group);
		}
	}
	
	private List<WebElement> getMenusGroupAndStore(LineaType lineaType, SublineaType sublineaType, WebElement group) 
	throws Exception {
		String group_linea = group.getAttribute("id");
		click(group).by(By.xpath("./span")).exec();
		List<WebElement> menus = getListMenusLinea(lineaType, sublineaType);
		for (WebElement menuElem : menus) {
			storeMenu(lineaType.name(), group_linea, menuElem);
		}
		return menus;
	}
	
	private List<WebElement> getListMenusLinea(LineaType lineaType, SublineaType sublineaType) throws Exception {
		String XPathMenusVisibles = 
				getXPathMenusSuperiorLinkVisibles(lineaType, sublineaType, TypeMenuDesktop.Link) + 
				"/..";
		return (driver.findElements(By.xpath(XPathMenusVisibles)));
	}
	
	private void storeMenu(String linea, String group_linea, WebElement menu) {
		String menu_linea = menu.getAttribute("id");
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
