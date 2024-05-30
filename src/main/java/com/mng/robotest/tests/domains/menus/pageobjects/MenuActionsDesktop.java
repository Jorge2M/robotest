package com.mng.robotest.tests.domains.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;

public class MenuActionsDesktop extends PageBase implements MenuActions {

	private final MenuWeb menu;
	private final PageGaleria pageGaleria = PageGaleria.make(channel, app, dataTest.getPais());

	public MenuActionsDesktop(MenuWeb menu) {
		this.menu = menu;
	}
	
	private String getXPathMenu() {
		return "(" + getXPathMenuStandard() + ") | (" + getXPathMenuAlternative() + ")";
	}
	
	private String getXPathMenuStandard() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(AppEcom.shop);
		}
			
		String nameMenu = menu.getMenu().toLowerCase();
		String testId = getDataTestId(idLinea, nameMenu);
		String xpath = "//ul/li//a[@data-testid='" + testId + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			String testId2 = getDataTestId(idLinea, menuIni);
			xpath+=" or @data-testid='" + testId2 + "'"; 
		}
		xpath+="]";
		return xpath;		
	}
	
	private String getDataTestId(String idLinea, String nameMenu) {
		String nameMenuInDataTestId = UtilsMenusPO.getMenuNameForDataTestId(nameMenu);
		return "menu.family." + nameMenuInDataTestId + "_" + idLinea + ".link";
	}
	
	private String getXPathMenuAlternative() {
		return 
			"//ul/li[@class[contains(.,'Submenu_selected')]]" + 
			"//a[@data-testid[contains(.,'menu.family.')]]" +
			"//self::*[text()[contains(.,'" + removeFirstCharacter(menu.getMenu()) + "')]]";		
	}
	
	private String removeFirstCharacter(String value) {
        if (value == null || value.length() <= 1) {
        	return "";
        }
        return value.substring(1); 
	}
	
	@Override
	public String click() {
		hoverGroup();
		return clickMenuSuperior();
	}
	@Override
	public void clickSubMenu() {
		pageGaleria.clickSubMenuDesktop(menu.getSubMenu());
	}
	@Override
	public boolean isVisibleMenu() {
		return state(VISIBLE, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		for (String subMenu : menu.getSubMenus()) {
			if (!pageGaleria.isVisibleSubMenuDesktop(subMenu)) {
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
		String nameMenu = "";
		state(PRESENT, getXPathMenu()).wait(1).check();
		var menuElement = getElement(getXPathMenu());
		if (isOutlet()) {
			nameMenu = getElement(menuElement, "./div").getAttribute("innerHTML");
		} else {
			nameMenu = menuElement.getText();
		}
		
		click(getXPathMenu()).exec();
		return nameMenu;
	}	

}
