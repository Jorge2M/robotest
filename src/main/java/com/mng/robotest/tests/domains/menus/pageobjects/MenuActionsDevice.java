package com.mng.robotest.tests.domains.menus.pageobjects;

import java.util.Arrays;
import java.text.Normalizer;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.FiltroMobil;
import com.mng.robotest.tests.domains.galeria.pageobjects.filters.device.SecMultiFiltrosDevice;

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
		String nameMenuInDataTestId = getMenuNameForDataTestId(nameMenu);
		String xpath =  
				"//ul/li//a[@data-testid='" + dataTestid + "." + 
				nameMenuInDataTestId + "_" + idLinea + sufix + "' or text()='" + menu.getMenu() + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='" + dataTestid + "." + menuIni + "_" + idLinea + sufix + "'"; 
		}
		xpath+="]";
		
		return xpath;
	}	

	private String getMenuNameForDataTestId(String menuName) {
		String menuWithoutSpaces = menu.getMenu().toLowerCase().replace(" ", "_");
		return removeAccents(menuWithoutSpaces);
	}
	
	private String removeAccents(String value) {
		String normalizedString = Normalizer.normalize(value, Normalizer.Form.NFD);
        return normalizedString.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
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
		return SecMultiFiltrosDevice.make(app, dataTest.getPais())
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
		SecMultiFiltrosDevice.make(app, dataTest.getPais())
			.selectMenu2onLevel(Arrays.asList(menu.getSubMenu()));
	}
}
