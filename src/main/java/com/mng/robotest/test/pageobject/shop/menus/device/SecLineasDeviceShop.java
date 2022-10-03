package com.mng.robotest.test.pageobject.shop.menus.device;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public class SecLineasDeviceShop extends PageBase implements SecLineasDevice {

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
	
	@Override
	public String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException {
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
	
	@Override
	public String getXPathSublineaNinosLink(SublineaType sublineaType) {
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
	public void selectLinea(LineaType lineaType, SublineaType sublineaType) {
		if (sublineaType==null) {
			selectLinea(lineaType);
		} else {
			selecSublineaNinosIfNotSelected(lineaType, sublineaType);
		}
	}
	
	@Override
	public void selecSublineaNinosIfNotSelected(LineaType lineaType, SublineaType sublineaType) {
		selectLinea(lineaType);
		if (!isSelectedSublineaNinos(sublineaType)) {
			click(getXPathSublineaNinosLink(sublineaType)).type(javascript).exec();
		}
	}

	@Override
	public void selectLinea(LineaType lineaType) {
		boolean toOpenMenus = true;
		SecCabecera secCabecera = SecCabecera.getNew(channel, app);
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		Linea linea = Linea.getLinea(lineaType, dataTest.getPais());
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink(lineaType)).type(javascript).exec();
		}
 	}
	
	@Override
	public boolean isLineaPresent(LineaType lineaType) {
		return state(Present, getXPathLineaLink(lineaType)).check();
	}
	
	@Override
	public boolean isSelectedLinea(LineaType lineaType) {
		String xpathLineaWithFlagSelected = getXPathLineaLink(lineaType);
		if (app==AppEcom.outlet || channel==Channel.tablet) {
			xpathLineaWithFlagSelected+="/..";
		}
		if (state(Present, xpathLineaWithFlagSelected).check()) {
			return getElement(xpathLineaWithFlagSelected).getAttribute("class").contains("selected");
		}
		return false;
	}
	
	public boolean isSelectedSublineaNinos(SublineaType sublineaNinosType) {
		String xpathSublineaWithFlagOpen = getXPathSublineaNinosLink(sublineaNinosType);
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
	public boolean isVisibleBlockSublineasNinos(LineaType lineaNinosType) {
		String xpathBlockSublineas = "";
		switch (lineaNinosType) {
		case nina: 
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.nina_nina);
			break;
		case teen:
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.teen_nina);
			break;
		default:
		case nino:
			xpathBlockSublineas = getXPathBlockSublineasNinos(SublineaType.nino_nino);
			break;
		}
		return state(Visible, xpathBlockSublineas).check();
	}
	
	private String getXPathLiSublineaNinos(SublineaType sublineaType) {
		return getXPathSublineaNinosLink(sublineaType) + "/..";		
	}
	
	private String getXPathBlockSublineasNinos(SublineaType sublineaType) {
		return getXPathLiSublineaNinos(sublineaType) + "/..";		
	}
	
}
