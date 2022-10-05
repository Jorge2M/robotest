package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public class LineaActionsDevice extends PageBase implements LineaActions {

	private final LineaWeb lineaWeb;
	private final LineaType lineaType;
	private final SublineaType sublineaType;
	
	private static final String XPATH_LINK_LINEA_MUJER = "//*[@data-testid='header.menuItem.she']";
	private static final String XPATH_LINK_LINEA_HOMBRE = "//*[@data-testid='header.menuItem.he']";
	private static final String XPATH_LINK_LINEA_NINA = "//*[@data-testid='header.menuItem.kids']";
	private static final String XPATH_LINK_LINEA_NINO = XPATH_LINK_LINEA_NINA;
	private static final String XPATH_LINK_LINEA_TEEN = "//*[@data-testid='header.menuItem.teen']";
	private static final String XPATH_LINK_LINEA_KIDS = XPATH_LINK_LINEA_NINA; //p.e. Bolivia
	private static final String XPATH_LINK_LINEA_VIOLETA = "//*[@data-testid='header.menuItem.violeta']";
	private static final String XPATH_LINK_LINEA_HOME = "//*[@data-testid='header.menuItem.home']";
	
	private static final String XPATH_LINK_SUBLINEA_NINA = "//*[@data-testid[contains(.,'header.tabButton.sections_nina')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINA = "//*[@data-testid[contains(.,'header.tabButton.sections_babyNina')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINA = "//*[@data-testid[contains(.,'header.tabButton.sections_teenQ')]]";
	private static final String XPATH_LINK_SUBLINEA_NINO = "//*[@data-testid[contains(.,'header.tabButton.sections_nino')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINO = "//*[@data-testid[contains(.,'header.tabButton.sections_babyNino')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINO = "//*[@data-testid[contains(.,'header.tabButton.sections_teenP')]]";
	
	public LineaActionsDevice(LineaWeb lineaWeb) {
		this.lineaWeb = lineaWeb;
		this.lineaType = lineaWeb.getLinea();
		this.sublineaType = lineaWeb.getSublinea();
	}
	
	private String getXPathLineaLink() throws IllegalArgumentException {
		switch (lineaType) {
		case she: 
			return XPATH_LINK_LINEA_MUJER;
		case he: 
			return XPATH_LINK_LINEA_HOMBRE;
		case nina:
			return XPATH_LINK_LINEA_NINA;
		case nino: 
			return XPATH_LINK_LINEA_NINO;
		case teen:
			return XPATH_LINK_LINEA_TEEN;
		case kids: 
			return XPATH_LINK_LINEA_KIDS;
		case violeta: 
			return XPATH_LINK_LINEA_VIOLETA;
		case home:
			return XPATH_LINK_LINEA_HOME;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}	
	
	private String getXPathSublineaLink() {
		switch (sublineaType) {
		case nina_nina:
			return XPATH_LINK_SUBLINEA_NINA;
		case teen_nina:
			return XPATH_LINK_SUBLINEA_TEEN_NINA;
		case nina_bebe:
			return XPATH_LINK_SUBLINEA_BEBE_NINA;
		case nino_nino:
			return XPATH_LINK_SUBLINEA_NINO;
		case teen_nino:
			return XPATH_LINK_SUBLINEA_TEEN_NINO;
		case nino_bebe:
		default:
			return XPATH_LINK_SUBLINEA_BEBE_NINO;
		}
	}
	
	@Override
	public void clickLinea() {
		boolean toOpenMenus = true;
		SecCabecera secCabecera = SecCabecera.getNew(channel, app);
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		Linea linea = Linea.getLinea(lineaType, dataTest.getPais());
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink()).type(javascript).exec();
		}
 	}
	
	@Override
	public void clickSublinea() {
		if (!isSublineaSelected(0)) {
			click(getXPathSublineaLink()).type(javascript).exec();
		}
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
//	
//	public boolean isLineaPresent() {
//		return state(Present, getXPathLineaLink()).check();
//	}
//	

//	
//	public boolean isVisibleBlockSublineas(LineaType lineaNinosType) {
//		String xpathBlockSublineas = "";
//		switch (lineaNinosType) {
//		case nina: 
//			xpathBlockSublineas = getXPathBlockSublineas(SublineaType.nina_nina);
//			break;
//		case teen:
//			xpathBlockSublineas = getXPathBlockSublineas(SublineaType.teen_nina);
//			break;
//		default:
//		case nino:
//			xpathBlockSublineas = getXPathBlockSublineas(SublineaType.nino_nino);
//			break;
//		}
//		return state(Visible, xpathBlockSublineas).check();
//	}
//	
//	private String getXPathLiSublinea(SublineaType sublineaType) {
//		return getXPathSublineaNinosLink(sublineaType) + "/..";		
//	}
//	
//	private String getXPathBlockSublineas(SublineaType sublineaType) {
//		return getXPathLiSublinea(sublineaType) + "/..";		
//	}
	
}