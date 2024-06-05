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
			"//ul/li//a[@data-testid[contains(.,'menu.family.')]]" +
			"//self::*[text()[contains(.,'" + capitalizeFirstLetter(menu.getMenu()) + "')]]";		
	}
	
	private String capitalizeFirstLetter(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
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
		state(PRESENT, getXPathMenu()).wait(1).check();
		String nameMenu = getNameMenu();
		click(getXPathMenu()).exec();
		return nameMenu;
	}	
	private String getNameMenu() {
		var menuElement = getElement(getXPathMenu());
		String nameMenu = menuElement.getText();
		if ("".compareTo(nameMenu)!=0) {
			return nameMenu;
		}
		
		var menuDivOpt = findElement(menuElement, "./div");
		if (menuDivOpt.isPresent()) {
			return menuDivOpt.get().getAttribute("innerHTML");
		} else {
			return menuElement.getAttribute("innerHTML");
		}
	}

}
