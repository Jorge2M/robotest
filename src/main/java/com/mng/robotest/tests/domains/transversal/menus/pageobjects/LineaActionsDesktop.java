package com.mng.robotest.tests.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public class LineaActionsDesktop extends PageBase implements LineaActions {
	
	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String TAG_ID_LINEA = "@LineaId";
	private static final String XP_LINEA_WITH_TAG = "//li[@data-testid[contains(.,'menu.brand." + TAG_ID_LINEA + "')]]";
	private static final String TAG_ID_SUBLINEA = "@SublineaId";
	private static final String TAG_ID_SUBLINEA2 = "@2SublineaId";
	private static final String XP_SUBLINEA_WITH_2TAG = "//li[(@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] or @id[contains(.,'" + TAG_ID_SUBLINEA2 + "')]) and @data-testid[contains(.,'section')]]";
	
	public LineaActionsDesktop(LineaWeb lineaWeb) {
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}

	private String getXPathLinkLinea() {
		return getXPathLinea() + "/a";
	}
	
	private String getXPathLinea() {
		return XP_LINEA_WITH_TAG.replace(TAG_ID_LINEA, getIdLineaEnDOM());
	}
	
	public String getIdLineaEnDOM() {
		if (app==AppEcom.outlet) {
			return lineaType.getSufixOutlet(channel);
		}
		return lineaType.name(app).toLowerCase();
	}	
	
	private String getXPathSublinea() {
		if (sublineaType==SublineaType.TEEN_NINO) {
			//Existe un problema en la página y a veces es TeenO y otras veces TeenP
			return XP_SUBLINEA_WITH_2TAG
					.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
					.replace(TAG_ID_SUBLINEA2, "teenP") + 
					"//button";
		}
		//Existe un problema en la página y a veces es TeenA y otras veces TeenQ
		return XP_SUBLINEA_WITH_2TAG
				.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
				.replace(TAG_ID_SUBLINEA2, "teenQ") + 
				"//button";
	}
	
	private String getXPathSublineaSelected() {
		return getXPathSublinea() + "//self::*[@aria-expanded='true']";
	}

	@Override
	public void clickLinea() {
		state(PRESENT, getXPathLinea()).wait(1).check();
		click(getXPathLinea()).exec();
	}	
	@Override 
	public void clickSublinea() {
		state(PRESENT, getXPathLinea()).wait(1).check();
		click(getXPathSublinea()).exec();
	}
	@Override
	public void hoverLinea() {
		state(VISIBLE, getXPathLinea()).wait(1).check();
		moveToElement(getXPathLinea());
	}
	@Override 
	public void hoverSublinea() {
		moveToElement(getXPathSublinea());
	}
	@Override
	public boolean isLineaSelected(int seconds) {
		for (int i=0; i<seconds; i++) {
			String textDecoration = getElement(getXPathLinkLinea()).getCssValue("text-decoration");
			if (textDecoration.contains("underline")) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isSublineaSelected(int seconds) {
		return state(VISIBLE, getXPathSublineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return state(VISIBLE, getXPathLinea()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(VISIBLE, getXPathSublinea()).wait(seconds).check();
	}
	
}
