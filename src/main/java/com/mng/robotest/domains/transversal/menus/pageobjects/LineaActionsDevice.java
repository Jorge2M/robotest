package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabeceraMostFrequent;

public class LineaActionsDevice extends PageBase implements LineaActions {

	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String XPATH_LINK_SUBLINEA_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_nina')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_babyNina')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINA = "//*[@data-testid[contains(.,'menu.subBrand.sections_teenQ')]]";
	private static final String XPATH_LINK_SUBLINEA_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_nino')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_babyNino')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINO = "//*[@data-testid[contains(.,'menu.subBrand.sections_teenP')]]";
	
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
	
	private String getXPathLineaLink(String dataTestid) throws IllegalArgumentException {
		switch (lineaType) {
		case SHE, HE, TEEN, HOME:
			if (app==AppEcom.outlet) {
				return "//*[@data-testid='" + dataTestid + "." + lineaType.getSufixOutlet(channel).trim() + "']";
			}
			return "//*[@data-testid='" + dataTestid + "." + lineaType.getId2() + "']";
		case NINA, NINO:
			if (app==AppEcom.outlet) {
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
			return XPATH_LINK_SUBLINEA_NINA;
		case TEEN_NINA:
			return XPATH_LINK_SUBLINEA_TEEN_NINA;
		case NINA_BEBE:
			return XPATH_LINK_SUBLINEA_BEBE_NINA;
		case NINO_NINO:
			return XPATH_LINK_SUBLINEA_NINO;
		case TEEN_NINO:
			return XPATH_LINK_SUBLINEA_TEEN_NINO;
		case NINO_BEBE:
		default:
			return XPATH_LINK_SUBLINEA_BEBE_NINO;
		}
	}
	
	@Override
	public void clickLinea() {
		boolean toOpenMenus = true;
		var secCabecera = new SecCabeceraMostFrequent();
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		Linea linea = Linea.getLinea(lineaType, dataTest.getPais());
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink()).type(javascript).exec();
		}
 	}
	@Override
	public void hoverLinea() {
		clickLinea();
	}
	
	@Override
	public void clickSublinea() {
		if (!isSublineaSelected(0)) {
			click(getXPathSublineaLink()).type(javascript).exec();
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
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(Present, xpathLineaWithFlagSelected).check()) {
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
		String xpathSublineaWithFlagOpen = getXPathSublineaLink();
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			xpathSublineaWithFlagOpen+="/..";
		}
		if (state(Present, xpathSublineaWithFlagOpen).check()) {
			String classDropdown = getElement(xpathSublineaWithFlagOpen).getAttribute("class");
			return (classDropdown.contains("open") || classDropdown.contains("-up"));
		}
		return false;
	}	

	@Override
	public boolean isLineaPresent(int seconds) {
		return state(Visible, getXPathLineaLink()).wait(seconds).check();
	}
	@Override
	public boolean isSublineaPresent(int seconds) {
		return state(Visible, getXPathSublineaLink()).wait(seconds).check();
	}	
	
}
