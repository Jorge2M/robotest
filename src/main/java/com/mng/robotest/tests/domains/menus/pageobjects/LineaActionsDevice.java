package com.mng.robotest.tests.domains.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;

public class LineaActionsDevice extends PageBase implements LineaActions {

	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String XP_LINK_SUBLINEA_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_nina')]]";
	private static final String XP_LINK_SUBLINEA_BEBE_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_babyNina')]]";
	private static final String XP_LINK_SUBLINEA_TEEN_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_teenQ')]]";
	private static final String XP_LINK_SUBLINEA_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_nino')]]";
	private static final String XP_LINK_SUBLINEA_BEBE_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_babyNino')]]";
	private static final String XP_LINK_SUBLINEA_TEEN_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_teenP')]]";
	
	public LineaActionsDevice(LineaWeb lineaWeb) {
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}

	//TODO eliminar el OLD ("header.menuItem") cuando suba la nueva versión a PRO (31-05-2023)
	private String getXPathLineaLink() throws IllegalArgumentException {
		return "(" + 
				getXPathLineaLink("header.menuItem") + " | " + 
				getXPathLineaLink("menu.brand") + ")";
	}
	
	private String getXPathMenusSublinea() {
		var idSublinea = sublineaType.getId(app);
		return "//ul[@data-testid[contains(.,'menu.family.sections_" + idSublinea + "')]]";
	}	
	
	private String getXPathLineaLink(String dataTestid) throws IllegalArgumentException {
		switch (lineaType) {
		case SHE, HE, TEEN, HOME:
			if (isOutlet()) {
				return "//*[@data-testid='" + dataTestid + "." + lineaType.getSufixOutlet(channel).trim() + "']";
			}
			return "//*[@data-testid='" + dataTestid + "." + lineaType.getId2() + "']";
		case NINA, NINO:
			if (isOutlet()) {
				return "//*[@data-testid='" + dataTestid + "." + lineaType.getSufixOutlet(channel) + "']";
			}
		case KIDS:			
			return "//*[@data-testid='" + dataTestid + ".kids']";
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}	
	
	private String getXPathSublineaLink() {
		switch (sublineaType) {
		case NINA_NINA:
			return XP_LINK_SUBLINEA_NINA;
		case TEEN_NINA:
			return XP_LINK_SUBLINEA_TEEN_NINA;
		case NINA_BEBE:
			return XP_LINK_SUBLINEA_BEBE_NINA;
		case NINO_NINO:
			return XP_LINK_SUBLINEA_NINO;
		case TEEN_NINO:
			return XP_LINK_SUBLINEA_TEEN_NINO;
		case NINO_BEBE:
		default:
			return XP_LINK_SUBLINEA_BEBE_NINO;
		}
	}
	
	@Override
	public void clickLinea() {
		boolean toOpenMenus = true;
		var secCabecera = SecCabecera.make();
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		Linea linea = Linea.getLinea(lineaType, dataTest.getPais());
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink()).type(JAVASCRIPT).exec();
		}
 	}
	@Override
	public void hoverLinea() {
		clickLinea();
	}
	
	@Override
	public void clickSublinea() {
		if (!isSublineaSelected(0)) {
			click(getXPathSublineaLink()).type(JAVASCRIPT).exec();
		}
	}
	@Override
	public void hoverSublinea() {
		clickSublinea();
	}
	
	@Override
	public boolean isLineaSelected(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isLineaSelected()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	public boolean isLineaSelected() {
		String xpathLineaWithFlagSelected = getXPathLineaLink();
		if (isOutlet() || isTablet()) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(PRESENT, xpathLineaWithFlagSelected).check()) {
			return getElement(xpathLineaWithFlagSelected).getAttribute("class").contains("selected");
		}
		return false;
	}

	@Override
	public boolean isSublineaSelected(int seconds) {
		for (int i=0; i<seconds; i++) {
			if (isSublineaSelected()) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}
	
	public boolean isSublineaSelected() {
		return state(VISIBLE, getXPathMenusSublinea()).check();
	}	

	@Override
	public boolean isLineaPresent(int seconds) {
		return state(VISIBLE, getXPathLineaLink()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(VISIBLE, getXPathSublineaLink()).wait(seconds).check();
	}	
	
}
