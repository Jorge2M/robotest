package com.mng.robotest.domains.transversal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.utils.UtilsTest;

public class NavigationBase extends PageBase {

	protected DataTest dataTest;
	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final InputParamsMango inputParamsSuite;

	public NavigationBase() {
		this.dataTest = TestBase.DATA_TEST.get();
		this.driver = TestMaker.getDriverTestCase();
		this.inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = inputParamsSuite.getChannel();
	}
	
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
	protected void clickMenu(MenuWeb menu) {
		new MenuSteps().click(menu);
	}
	protected void clickGroup(GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(groupType));
	}
	public void clickGroup(LineaType linea, SublineaType sublinea, GroupType groupType) {
		new MenuSteps().clickGroup(new GroupWeb(linea, sublinea, groupType));
	}
	protected void clickSubMenu(MenuWeb menu) {
		new MenuSteps().clickSubMenu(menu);
	}
	
	protected List<GarmentCatalog> getArticles(int numArticles) throws Exception {
		return UtilsTest.getArticlesForTest(dataTest.getPais(), app, numArticles, driver);
	}
	
	public boolean isPRO() {
		if (TestCaseTM.getTestCaseInExecution().isEmpty()) {
			return false;
		}
		String urlBase = TestMaker.getInputParamsSuite().getUrlBase();
		if (isEntornoPRO(urlBase)) {
			return true;
		}
		return isEntornoPRO(driver.getCurrentUrl());
	}	
	
	public boolean isEntornoPRO(String url) {
		List<String> URLsProShop   = Arrays.asList("shop.mango.com", "shoptest.pro.mango.com");
		List<String> URLsProOutlet = Arrays.asList("www.mangooutlet.com", "outlettest.pro.mango.com");
		Iterator<String> itURLsPRO = null;
		if (app==AppEcom.outlet) {
			itURLsPRO = URLsProOutlet.iterator();
		} else {
			itURLsPRO = URLsProShop.iterator();
		}
		
		while (itURLsPRO.hasNext()) {
			String URL = itURLsPRO.next();
			if (url.contains(URL)) {
				return true;
			}
		}
		return false;
	}	
}
