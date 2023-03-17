package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public class LineaActionsDesktop extends PageBase implements LineaActions {
	
	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String TAG_ID_LINEA = "@LineaId";
	private static final String XPATH_LINEA = "//li[@data-testid[contains(.,'header.menuItem')]]";
	private static final String XPATH_LINEA_WITH_TAG = XPATH_LINEA + "//self::*[@data-testid[contains(.,'Item." + TAG_ID_LINEA + "')]]";
	private static final String TAG_ID_SUBLINEA = "@SublineaId";
	private static final String XPATH_SUBLINEA_WITH_TAG = "//li[@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] and @data-testid[contains(.,'section')]]";
	private static final String TAG_ID_SUBLINEA2 = "@2SublineaId";
	private static final String XPATH_SUBLINEA_WITH_2TAG = "//li[(@id[contains(.,'" + TAG_ID_SUBLINEA+ "')] or @id[contains(.,'" + TAG_ID_SUBLINEA2 + "')]) and @data-testid[contains(.,'section')]]";
	
	public LineaActionsDesktop(LineaWeb lineaWeb) {
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}
	
	private String getXPathLinea() {
		return XPATH_LINEA_WITH_TAG.replace(TAG_ID_LINEA, getIdLineaEnDOM());
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
			return XPATH_SUBLINEA_WITH_2TAG
					.replace(TAG_ID_SUBLINEA, sublineaType.getId(app))
					.replace(TAG_ID_SUBLINEA2, "teenP") + 
					"//span";
		}
		return XPATH_SUBLINEA_WITH_TAG
				.replace(TAG_ID_SUBLINEA, sublineaType.getId(app)) + 
				"//span";
	}
	
	private String getXPathLineaSelected() {
		return getXPathLinea() + "//a[@aria-expanded='true']";
	}
	private String getXPathSublineaSelected() {
		return getXPathSublinea() + "//self::*[string-length(normalize-space(@class))>0]";
	}

	@Override
	public void clickLinea() {
		state(State.Present, getXPathLinea()).wait(1).check();
		click(getXPathLinea()).exec();
	}	
	@Override 
	public void clickSublinea() {
		state(State.Present, getXPathLinea()).wait(1).check();
		click(getXPathSublinea()).exec();
	}
	@Override
	public void hoverLinea() {
		state(State.Visible, getXPathLinea()).wait(1).check();
		moveToElement(getXPathLinea());
	}
	@Override 
	public void hoverSublinea() {
		moveToElement(getXPathSublinea());
	}
	@Override
	public boolean isLineaSelected(int seconds) {
		return state(Present, getXPathLineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaSelected(int seconds) {
		return state(Visible, getXPathSublineaSelected()).wait(seconds).check();
	}
	@Override
	public boolean isLineaPresent(int seconds) {
		return state(Visible, getXPathLinea()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(Visible, getXPathSublinea()).wait(seconds).check();
	}
	
}
