package com.mng.robotest.tests.domains.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop;

public class MenuActionsDesktop extends PageBase implements MenuActions {

	private final MenuWeb menu;
	private final PageGaleriaDesktop pageGaleria = ((PageGaleriaDesktop)PageGaleria.make(channel, app, dataTest.getPais()));

	public MenuActionsDesktop(MenuWeb menu) {
		this.menu = menu;
	}
	
	private String getXPathMenu() {
		return 
			"(" + getXPathMenuStandard() + ") | " + 
			"(" + getXPathMenuAlternative() + ")";
	}
	
	private String getXPathMenuStandard() {
		String xpathMenuActual = getXPathMenuStandardActual();
		String xpathMenuGenesis = getXPathMenuStandardGenesis();
		return "(" + xpathMenuActual + " | " +xpathMenuGenesis + ")";
	}
	private String getXPathMenuAlternative() {
		String xpathMenuActual = getXPathMenuAlternativeActual();
		String xpathMenuGenesis = getXPathMenuAlternativeGenesis();
		return "(" + xpathMenuActual + " | " +xpathMenuGenesis + ")";
	}
	
	private String getXPathMenuStandardGenesis() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
			
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
			"//ul/li[@class[contains(.,'Submenu_selected')]]" + 
			"//a[@data-testid='menu.family." + 
			nameMenu + "_" + idLinea + ".link'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='menu.family." + menuIni + "_" + idLinea + ".link'"; 
		}
		xpath+="]";
		return xpath;		
	}
	
	private String getXPathMenuStandardActual() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
			
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
			"//ul[@data-family]/li//a[@data-testid='menu.family." + 
			nameMenu + "_" + idLinea + ".link'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='menu.family." + menuIni + "_" + idLinea + ".link'"; 
		}
		xpath+="]";
		return xpath;
	}	
	
	private String getXPathMenuAlternativeGenesis() {
		return 
			"//ul/li[@class[contains(.,'Submenu_selected')]]" + 
			"//a[@data-testid[contains(.,'menu.family.')]]" +
			"//self::*[text()[contains(.,'" + menu.getMenu() + "')]]";		
	}
	private String getXPathMenuAlternativeActual() {
		return 
			"//ul[@data-family]/li//a[" + 
			"@data-testid[contains(.,'menu.family.')]]" +
		    "//self::*[text()[contains(.,'" + menu.getMenu() + "')]]";
	}	
	
	@Override
	public String click() {
		hoverGroup();
		return clickMenuSuperior();
	}
	@Override
	public void clickSubMenu() {
		pageGaleria.clickSubMenu(menu.getSubMenu());
	}
	@Override
	public boolean isVisibleMenu() {
		return state(VISIBLE, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		for (String subMenu : menu.getSubMenus()) {
			if (!pageGaleria.isVisibleSubMenu(subMenu)) {
				return false;
			}
		}
		return true;
	}

	private void hoverGroup() {
		new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup())
			.hover();
	}	
	
	private String clickMenuSuperior() {
		state(VISIBLE, getXPathMenu()).wait(1).check();
		String nameMenu = getElement(getXPathMenu()).getText(); 
		click(getXPathMenu()).exec();
		return nameMenu;
	}	

}