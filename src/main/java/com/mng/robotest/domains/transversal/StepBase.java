package com.mng.robotest.domains.transversal;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus;
import com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.test.data.Color;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecFiltrosSteps;
import com.mng.robotest.test.utils.UtilsTest;

public abstract class StepBase extends PageBase {

	protected void access(boolean clearData) throws Exception {
		if (clearData) {
			accessAndClearData();
		} else {
			access();
		}
	}
	
	protected void access() throws Exception {
		new AccesoSteps().oneStep(false);
	}
	
	protected void accessAndClearData() throws Exception {
		new AccesoSteps().oneStep(true);
	}	
	
	protected void clickLinea(LineaType lineaType) {
		new MenuSteps().clickLinea(lineaType);
	}
	protected void clickLinea(LineaType lineaType, SublineaType sublinea) {
		new MenuSteps().clickLinea(lineaType, sublinea);
	}
	protected void clickMenu(String menuLabel) {
		new MenuSteps().clickMenu(menuLabel);
	}
	protected void clickMenu(MenuItem menuItem) {
		clickMenu(FactoryMenus.get(menuItem));
	}
	protected void clickMenu(MenuWeb menu) {
		new MenuSteps().click(menu);
	}
	protected void clickGroup(GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(groupType));
	}
	public void clickGroup(LineaType linea, SublineaType sublinea, GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(linea, sublinea, groupType));
	}
	protected void clickSubMenu(MenuItem menuItem) {
		clickSubMenu(FactoryMenus.get(menuItem));
	}
	protected void clickSubMenu(MenuWeb menu) {
		new MenuSteps().clickSubMenu(menu);
	}
	
	protected void filterGaleryByColor(MenuItem menu, Color color) {
		filterGaleryByColors(menu, Arrays.asList(color));
	}
	protected void filterGaleryByColors(MenuItem menu, List<Color> colors) {
		new SecFiltrosSteps().selectFiltroColores(colors, FactoryMenus.get(menu).getMenu());
	}	
	
	protected List<GarmentCatalog> getArticles(int numArticles) throws Exception {
		return UtilsTest.getArticlesForTest(dataTest.getPais(), app, numArticles, driver);
	}
}
