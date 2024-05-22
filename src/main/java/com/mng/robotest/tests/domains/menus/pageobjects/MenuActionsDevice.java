package com.mng.robotest.tests.domains.menus.pageobjects;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.FiltroMobil;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.mobil.SecFiltrosMobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class MenuActionsDevice extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	public String getXPathMenu() {
		return "(" + getXPathMenuActual() + " | " + getXPathMenuGenesis() + ")";
	}
	private String getXPathMenuActual() {
		return "//div[@id='subMenuPortalContainer']" +  getXPathMenuBase();
	}
	private String getXPathMenuGenesis() {
		return "//li[@class[contains(.,'Submenu_selected')]]" + getXPathMenuBase();
	}
	
	private String getXPathMenuBase() {
		String dataTestid = "menu.family";
		String sufix = ".link";
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}

		String nameMenu = menu.getMenu();
		String nameMenuInDataTestId = UtilsMenusPO.getMenuNameForDataTestId(nameMenu);
		String xpath =  
				"//ul/li//a[@data-testid='" + dataTestid + "." + 
				nameMenuInDataTestId + "_" + idLinea + sufix + "' or text()='" + menu.getMenu() + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			String menuInDataTestId = UtilsMenusPO.getMenuNameForDataTestId(menuIni);
			xpath+=" or @data-testid='" + dataTestid + "." + menuInDataTestId + "_" + idLinea + sufix + "'"; 
		}
		xpath+="]";
		
		return xpath;
	}	

	public MenuActionsDevice(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public String click() {
		clickGroup();
		return clickMenu();
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
		return new SecFiltrosMobil()
				.isAvailableFiltros(FiltroMobil.FAMILIA, menu.getSubMenus());
	}

	private void clickGroup() {
		new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup())
			.click();
	}
	
	private String clickMenu() {
		String menuItem = getElement(getXPathMenu()).getText();
		click(getXPathMenu()).exec();
		return menuItem;
	}
	
	private void clickSubLevelMenu() {
		new SecFiltrosMobil()
			.selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
	
}
