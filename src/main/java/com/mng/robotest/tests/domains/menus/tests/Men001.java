package com.mng.robotest.tests.domains.menus.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.tests.domains.menus.entity.Sublinea;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb;
import com.mng.robotest.tests.domains.menus.steps.MenuSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;

public class Men001 extends TestBase {

	private final Linea line;
	private final Sublinea subline;
	private final GroupType group;
	
	public Men001(Pais country, IdiomaPais idiom, Linea line, Sublinea subline, GroupType group) {
		this.dataTest.setPais(country);
		this.dataTest.setIdioma(idiom);
		this.line = line;
		this.subline = subline;
		this.group = group;
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().accessFromPreHome();
		new LandingSteps().checkIsCountryWithCorrectLineas(2);
		if (new UtilsMangoTest().isLineActive(line)) {
			if (subline==null) {
				checkLineGroupMenus();
			} else {
				checkSublineGroupMenus();
			}
		}
	}
	
	private void checkLineGroupMenus() {
		clickLinea(line.getType());
		if (isTestMenus(line, group)) {
			clickMenusLinea();
		}
	}
	
	private void checkSublineGroupMenus() {
		clickLinea(line.getType(), subline.getTypeSublinea());
		if (isTestMenus(line, subline, group)) {
			clickMenusSublinea();
		}
	}
	
	private void clickMenusLinea() {
		new MenuSteps().clickAllMenus(LineaWeb.make(line.getType(), null, dataTest.getPais(), app), group);
	}
	
	private void clickMenusSublinea() {
		new MenuSteps().clickAllMenus(LineaWeb.make(line.getType(), subline.getTypeSublinea(), dataTest.getPais(), app), group);
	}
	
	private boolean isTestMenus(Linea line, GroupType group) {
		if (!group.isInLinea(line.getType())) {
			return false;
		}
		return line.getMenus().compareTo("s")==0;
	}
	
	private boolean isTestMenus(Linea line, Sublinea subline, GroupType group) {
		if (!group.isInLinea(line.getType())) {
			return false;
		}
		return subline.getMenus().compareTo("s")==0;
	}

}
