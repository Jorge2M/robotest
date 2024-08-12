package com.mng.robotest.tests.domains.menus.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleria;
import com.mng.robotest.tests.domains.galeria.pageobjects.SecFiltros;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.conf.Channel;

public class MenuActionsDevice extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	private String getXPathMenu() {
		return "//*[@data-testid[contains(.,'menu.subMenu')]]" + getXPathMenuBase();
	}
	
	private String getXPathMenuBase() {
		String dataTestid = "menu.family";
		String sufix = ".link";
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(AppEcom.shop);
		}

		String nameMenu = menu.getMenu();
		String nameMenuInDataTestId = UtilsMenusPO.getMenuNameForDataTestId(nameMenu);
		String xpath =  
				"//ul/li//a[@data-testid='" + dataTestid + "." + 
				nameMenuInDataTestId + "_" + idLinea + sufix + "' or text()[contains(.,'" + capitalizeFirstLetter(menu.getMenu()) + "')]";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			String menuInDataTestId = UtilsMenusPO.getMenuNameForDataTestId(menuIni);
			xpath+=" or @data-testid='" + dataTestid + "." + menuInDataTestId + "_" + idLinea + sufix + "'"; 
		}
		xpath+="]";
		
		return xpath;
	}	
	
	private String capitalizeFirstLetter(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	public MenuActionsDevice(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public String click() {
		clickGroup();
		String nameMenu = clickMenu();
		ifGalerySetGridOneColumn();
		return nameMenu;
	}

	private void ifGalerySetGridOneColumn() {
		var pageGaleria = PageGaleria.make(Channel.mobile);
		if (pageGaleria.isVisibleAnyArticle()) {
			pageGaleria.clickGridview("01");
		}
	}
	
	@Override
	public void clickSubMenu() {
		click();
		clickSubLevelMenu();
	}
	
	@Override
	public boolean isVisibleMenu() {
		return state(VISIBLE, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		return SecFiltros.make().isAvailableFiltrosFamilia(menu.getSubMenus());
	}

	private void clickGroup() {
		new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup())
			.click();
	}
	
	private String clickMenu() {
		String menuItem = getElement(getXPathMenu()).getAttribute("innerHTML");
		click(getXPathMenu()).exec();
		return menuItem;
	}
	
	private void clickSubLevelMenu() {
		SecFiltros.make().selectMenu2onLevelDevice(menu.getSubMenu());
	}
	
}
