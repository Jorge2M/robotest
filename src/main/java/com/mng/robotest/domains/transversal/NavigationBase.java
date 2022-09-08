package com.mng.robotest.domains.transversal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu2onLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;

public class NavigationBase {

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
	
	public enum TypeSelectMenu { STANDARD, XREF }
	
	protected void clickMenu(String menu) throws Exception {
		clickMenu(LineaType.she, null, menu);
	}

	protected void clickMenu(String menu, TypeSelectMenu typeSelect) throws Exception {
		clickMenu(LineaType.she, null, menu, typeSelect);
	}
	
	protected void clickMenu(LineaType linea, SublineaType sublinea, String menu) throws Exception {
		clickMenu(linea, sublinea, menu, TypeSelectMenu.STANDARD);
	}
	
	protected void clickMenu(LineaType linea, SublineaType sublinea, String menu, TypeSelectMenu typeSelect) 
			throws Exception {
		Menu1rstLevel menuToSelect = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(linea, sublinea, menu));
		if (typeSelect==TypeSelectMenu.XREF) {
			new SecMenusWrapperSteps().accesoMenuXRef(menuToSelect);
		} else {
			new SecMenusWrapperSteps().selectMenu1rstLevelTypeCatalog(menuToSelect);
		}
	}
	
	protected void clickMenuLateral(String menu1rstLevel) throws Exception {
		Menu1rstLevel menu1rstLevelObj = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, menu1rstLevel));
		new SecMenusWrapperSteps().selectMenuLateral1erLevelTypeCatalog(menu1rstLevelObj);
	}
	
	protected void clickMenuLateral(String menu1rstLevel, String menu2onLevel) throws Exception {
		clickMenuLateral(menu1rstLevel);
		Menu1rstLevel menu1rstLevelObj = MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(LineaType.she, null, menu1rstLevel));
		Menu2onLevel menu2onLevelToSelect = MenuTreeApp.getMenuLevel2From(menu1rstLevelObj, menu2onLevel);
		new SecMenusWrapperSteps().selectMenu2onLevel(menu2onLevelToSelect);
	}
}