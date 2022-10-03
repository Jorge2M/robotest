package com.mng.robotest.test.pageobject.shop.menus.device;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.utils.checkmenus.DataScreenMenu;

public class SecMenuLateralDevice extends PageBase {

	public enum TypeLocator { DATA_GA_LABEL_PORTION, HREF_PORTION }
	
	private final SecLineasDevice secLineasDevice = SecLineasDevice.make(app);
	private final SecMenusUserDevice secUserMenu = new SecMenusUserDevice();

	private static final String XPATH_LINEA_ITEM_OUTLET = "//ul[@class='menu-section-brands']/li[@class[contains(.,'menu-item')]]";
	private static final String XPATH_LINEA_ITEM_SHOP = "//button[@data-testid[contains(.,'header.menuItem')]]/..";
	
	private static final String XPATH_CAPA_2ON_LEVEL_MENU_OUTLET = "//div[@class[contains(.,'section-detail-list')]]";
	private static final String XPATH_CAPA_2ON_LEVEL_MENU_SHOP = "//div[@id='subMenuPortalContainer']";
	
	private static final String XPATH_LINK_MENU_VISIBLE_FROM_LI_OUTLET = 
		"//ul[@class='section-detail' or @class[contains(.,'dropdown-menu')]]" +
		"/li[not(@class[contains(.,'mobile-label-hidden')] or @class[contains(.,' label-hidden')] or @class[contains(.,' gap ')])]" +
		"/a[@class[contains(.,'menu-item-label')] and @href]";
	private static final String XPATH_LINK_MENU_VISIBLE_FROM_LI_SHOP = "//li/a[@data-testid[contains(.,'header.subMenu')]]";

	private String getXPathLineaItem() {
		if (app==AppEcom.outlet) {
			return XPATH_LINEA_ITEM_OUTLET;
		}
		return XPATH_LINEA_ITEM_SHOP;
	}
	private String getXPathCapa2onLevelMenu() {
		if (app==AppEcom.outlet) {
			return XPATH_CAPA_2ON_LEVEL_MENU_OUTLET;
		}
		return XPATH_CAPA_2ON_LEVEL_MENU_SHOP;		
	}
	private String getXPathLinkMenuVisibleFromLi() {
		if (app==AppEcom.outlet) {
			return XPATH_LINK_MENU_VISIBLE_FROM_LI_OUTLET;
		}
		return XPATH_LINK_MENU_VISIBLE_FROM_LI_SHOP;		
	}
	
	public SecLineasDevice getSecLineasDevice() {
		return secLineasDevice;
	}
	
	public SecMenusUserDevice getUserMenu() {
		return secUserMenu;
	}
	
	private String getXPathLinksMenus(SublineaType sublineaType) {
		if (sublineaType==null) {
			return getXPathCapa2onLevelMenu() + getXPathLinkMenuVisibleFromLi();
		}
		String divSublineaNinos = secLineasDevice.getXPathSublineaNinosLink(sublineaType); 
		return divSublineaNinos + "/.." + getXPathLinkMenuVisibleFromLi();
	}

	private String getXPathMenuByTypeLocator(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpath2oLevelMenuLink = getXPathLinksMenus(menu1rstLevel.getSublinea());
		switch (typeLocator) {
		case DATA_GA_LABEL_PORTION:
			return xpath2oLevelMenuLink.replace("@href", "@data-label[contains(.,'" + menu1rstLevel.getDataTestIdMenuSuperiorDesktop().toLowerCase() + "')]");			
		case HREF_PORTION:
		default:
			return xpath2oLevelMenuLink.replace("@href", "@href[contains(.,'" + menu1rstLevel.getDataTestIdMenuSuperiorDesktop().toLowerCase() + "')]");
		}
	}

	public boolean isMenus2onLevelDisplayed(SublineaType sublineaType) {
		List<WebElement> listMenus = getListMenusDisplayed(sublineaType);
		return (listMenus!=null && !listMenus.isEmpty());
	}

	public List<WebElement> getListMenusAfterSelectLinea(Linea linea, SublineaType sublineaType) throws Exception {
		secLineasDevice.selectLinea(linea.getType(), sublineaType);
		return (getListMenusDisplayed(sublineaType));
	}

	private List<WebElement> getListMenusDisplayed(SublineaType sublineaType) {
		String xpath2oLevelMenuLink = getXPathLinksMenus(sublineaType);
		if (app==AppEcom.outlet) {
			return getElementsVisible(xpath2oLevelMenuLink);
		} else {
			return getElements(xpath2oLevelMenuLink);
		}
	}

	public List<DataScreenMenu> getListDataScreenMenus(Linea linea, SublineaType sublineaType) throws Exception {
		List<DataScreenMenu> listDataMenus = new ArrayList<>();
		List<WebElement> listMenus = getListMenusAfterSelectLinea(linea, sublineaType);
		for (int i=0; i<listMenus.size(); i++) {
			DataScreenMenu dataMenu = DataScreenMenu.from(listMenus.get(i), channel);
			dataMenu.setDataTestId(listMenus.get(i).getAttribute("data-label"));
			if (dataMenu.isDataTestIdValid()) {
				listDataMenus.add(dataMenu);
			}
		}
		return listDataMenus;
	}

	public void clickMenuYetDisplayed(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		click(xpathMenu).state(Visible).waitLoadPage(0).exec();
	}

	public String getLiteralMenuVisible(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) throws Exception {
		By byMenu = By.xpath(getXPathMenuByTypeLocator(typeLocator, menu1rstLevel));
		state(Visible, byMenu).wait(1).check();
		moveToElement(byMenu);
		if (state(Visible, byMenu).check()) {
			return getElement(byMenu).getAttribute("innerHTML");
		}
		return "";
	}

	public boolean isMenuInStateUntil(boolean open, int seconds) {
		switch (channel) {
		case tablet:
			return isMenuTabletInStateUntil(open, seconds);
		case mobile:
		default:
			return isMenuMobilInStateUntil(open, seconds);
		}
	}
	
	private boolean isMenuTabletInStateUntil(boolean open, int seconds) {
		if (open) {
			return state(Visible, getXPathLineaItem()).wait(seconds).check();
		}
		return state(Invisible, getXPathLineaItem()).wait(seconds).check();
	}
	
	private boolean isMenuMobilInStateUntil(boolean open, int seconds) {
		try {
			String xpathLineaShe = secLineasDevice.getXPathLineaLink(LineaType.she);
			String xpathLineaNina = secLineasDevice.getXPathLineaLink(LineaType.nina);
			String xpathValidation = xpathLineaShe + " | " + xpathLineaNina;
			if (open) {
				return state(Visible, xpathValidation).wait(seconds).check();
			}
			return state(Invisible, xpathValidation).wait(seconds).check();
		}
		catch (Exception e) {
			//Si se produce una excepción continuamos porque existe la posibilidad que el menú se encuentre en el estado que necesitamos
		}
		return true;
	}

	/**
	 * Selecciona un menú lateral de 2o nivel de la aplicación en Móbil
	 * @param linea: línea al que pertenece el menú
	 * @param menu: nombre del menú a nivel del data-ga-label
	 */
	public void clickMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) {
		Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
		secLineasDevice.selectLinea(linea.getType(), menu1rstLevel.getSublinea());
		if (app==AppEcom.shop || app==AppEcom.votf) {
			unfoldMenuGroup(typeLocator, menu1rstLevel);
			SeleniumUtils.waitMillis(500);
		}
		clickMenuYetDisplayed(typeLocator, menu1rstLevel);
	}
	
	private String getXPathMenuGroup(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathLinkMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		return xpathLinkMenu + "/../../preceding-sibling::div[@class[contains(.,'dropdown')]]";
	}
	
	private boolean isMenuGroupOpen(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, int seconds) {
		By groupBy = By.xpath(getXPathMenuGroup(typeLocator, menu1rstLevel));
		if (state(State.Present, groupBy).check()) {
			for (int i=0; i<seconds; i++) {
				String classGroup = driver.findElement(groupBy).getAttribute("className");
				if (classGroup!=null && classGroup.contains("open")) {
					return true;
				}
				waitMillis(1000);
			}
		}
		return false;
	}
	
	public void unfoldMenuGroup(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel) {
		String xpathLinkMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		boolean menuVisible = state(State.Clickable, xpathLinkMenu).check();
		String xpathGroupMenu = getXPathMenuGroup(typeLocator, menu1rstLevel);
		List<WebElement> listGroups = getElements(xpathGroupMenu);
		Iterator<WebElement> itGroups = listGroups.iterator();
		while (!menuVisible && itGroups.hasNext()) {
			WebElement group = itGroups.next();
			int ii=0;
			while (!menuVisible && ii<2) {
				click(group).exec();
				isMenuGroupOpen(typeLocator, menu1rstLevel, 1);
				if (state(State.Present, xpathLinkMenu).wait(1).check()) {
					moveToElement(xpathLinkMenu);
					menuVisible = state(State.Clickable, xpathLinkMenu).wait(1).check();
				}
				ii+=1;
			}
		}
	}
	
	public boolean existsMenuLateral1rstLevel(TypeLocator typeLocator, Menu1rstLevel menu1rstLevel, Pais pais) {
		Linea linea = pais.getShoponline().getLinea(menu1rstLevel.getLinea());
		secLineasDevice.selectLinea(linea.getType(), menu1rstLevel.getSublinea());
		String xpathMenu = getXPathMenuByTypeLocator(typeLocator, menu1rstLevel);
		return state(Visible, xpathMenu).check();
	}
}