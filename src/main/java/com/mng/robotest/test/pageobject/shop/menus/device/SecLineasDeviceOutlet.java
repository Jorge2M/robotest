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

public class SecLineasDeviceOutlet extends PageBase implements SecLineasDevice {

	//private static final String[] TAGS_REBAJAS_MOBIL = {"mujer", "hombre", "ninos", "teen"};

	private static final String XPATH_HEADER_MOBILE = "//div[@id='headerMobile']";
	private static final String XPATH_CAPA_LEVEL_LINEA = "//div[@class[contains(.,'menu-section')]]";
	
	private static final String XPATH_CAPA_MENU_LINEAS_TABLET = "//div[@class='menu-section-brands']";
	private static final String XPATH_CAPA_MENU_LINEAS_MOBIL = "//div[@class='section-detail-list']"; 
	
	private static final String INI_XPATH_LINK_LINEA = XPATH_CAPA_LEVEL_LINEA + "//div[@class[contains(.,'brand-item')] and @id";
	private static final String XPATH_LINK_LINEA_MUJER = INI_XPATH_LINK_LINEA + "='she' or @id='prendas_she']";
	private static final String XPATH_LINK_LINEA_HOMBRE = INI_XPATH_LINK_LINEA + "='he']";
	private static final String XPATH_LINK_LINEA_NINA = INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_NINO = INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_TEEN = INI_XPATH_LINK_LINEA + "='teen']";
	private static final String XPATH_LINK_LINEA_KIDS =INI_XPATH_LINK_LINEA + "='kids']"; //p.e. Bolivia
	private static final String XPATH_LINK_LINEA_VIOLETA = INI_XPATH_LINK_LINEA + "='violeta']";
	private static final String XPATH_LINK_LINEA_HOME = INI_XPATH_LINK_LINEA + "='home']";	
	
	private static final String INI_XPATH_LINK_SUBLINEA = "//div[@data-label[contains(.,'interior-"; 
	private static final String XPATH_LINK_SUBLINEA_NINA =  INI_XPATH_LINK_SUBLINEA + "nina')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINA = INI_XPATH_LINK_SUBLINEA + "bebe_nina')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINA = INI_XPATH_LINK_SUBLINEA + "chica')]]";
	private static final String XPATH_LINK_SUBLINEA_NINO = INI_XPATH_LINK_SUBLINEA + "nino')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINO = INI_XPATH_LINK_SUBLINEA + "bebe_nino')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINO = INI_XPATH_LINK_SUBLINEA + "chico')]]";
	
	public SecLineasDeviceOutlet() {
		super();
	}
	
	public String getXPathHeaderMobile() {
		return XPATH_HEADER_MOBILE;
	}
	
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

	public boolean isLineaPresentUntil(LineaType lineaType, int seconds) {
		String xpathLinea = getXPathLineaLink(lineaType);
		return state(Present, xpathLinea).wait(seconds).check();
	}

	@Override
	public String getXPathSublineaNinosLink(SublineaType sublineaType) {
		switch (sublineaType) {
		case nina_nina:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_NINA;
		case teen_nina:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_TEEN_NINA;
		case nina_bebe:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_BEBE_NINA;
		case nino_nino:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_NINO;
		case teen_nino:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_TEEN_NINO;
		case nino_bebe:
		default:
			return getXPathCapaMenus() + XPATH_LINK_SUBLINEA_BEBE_NINO;
		}
	}
	
	private String getXPathCapaMenus() {
		if (channel==Channel.tablet) {
			return XPATH_CAPA_MENU_LINEAS_TABLET;
		}
		return XPATH_CAPA_MENU_LINEAS_MOBIL;
	}
	
	@Override
	public void selectLinea(Linea linea, SublineaType sublineaType) {
		if (sublineaType==null) {
			selectLinea(linea);
		} else {
			selecSublineaNinosIfNotSelected(linea, sublineaType);
		}
	}
	
	@Override
	public void selecSublineaNinosIfNotSelected(Linea linea, SublineaType sublineaType) {
		selectLinea(linea);
		if (!isSelectedSublineaNinos(sublineaType)) {
			click(getXPathSublineaNinosLink(sublineaType)).type(javascript).exec();
		}
	}

	@Override
	public void selectLinea(Linea linea) {
		boolean toOpenMenus = true;
		SecCabecera secCabecera = SecCabecera.getNew(channel, app);
		secCabecera.clickIconoMenuHamburguerMobil(toOpenMenus);
		if ("n".compareTo(linea.getExtended())==0) {
			click(getXPathLineaLink(linea.getType())).type(javascript).exec();
		}
 	}
	
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
