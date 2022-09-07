package com.mng.robotest.test.pageobject.shop.menus.mobil;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.javascript;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;

public abstract class SecLineasDevice extends PageBase {

	public abstract String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException;
	
	private static final String XPATH_CAPA_MENU_LINEAS_TABLET = "//div[@class='menu-section-brands']";
	private static final String XPATH_CAPA_MENU_LINEAS_MOBIL = "//div[@class='section-detail-list']"; 
	
	private static final String INI_XPATH_LINK_SUBLINEA = "//div[@data-label[contains(.,'interior-"; 
	private static final String XPATH_LINK_SUBLINEA_NINA =  INI_XPATH_LINK_SUBLINEA + "nina')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINA = INI_XPATH_LINK_SUBLINEA + "bebe_nina')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINA = INI_XPATH_LINK_SUBLINEA + "chica')]]";
	private static final String XPATH_LINK_SUBLINEA_NINO = INI_XPATH_LINK_SUBLINEA + "nino')]]";
	private static final String XPATH_LINK_SUBLINEA_BEBE_NINO = INI_XPATH_LINK_SUBLINEA + "bebe_nino')]]";
	private static final String XPATH_LINK_SUBLINEA_TEEN_NINO = INI_XPATH_LINK_SUBLINEA + "chico')]]";
	
	public static SecLineasDevice make(Channel channel, AppEcom app, WebDriver driver) {
		switch (channel) {
		case tablet:
			return SecLineasTablet.getNew(app, driver); 
		case mobile:
		default:
			return SecLineasMobil.getNew(app);
		}
	}
	
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
	
	public void selectLinea(Linea linea, SublineaType sublineaType) {
		if (sublineaType==null) {
			selectLinea(linea);
		} else {
			selecSublineaNinosIfNotSelected(linea, sublineaType);
		}
	}
	
	public void selecSublineaNinosIfNotSelected(Linea linea, SublineaType sublineaType) {
		selectLinea(linea);
		if (!isSelectedSublineaNinos(sublineaType)) {
			click(getXPathSublineaNinosLink(sublineaType)).type(javascript).exec();
		}
	}

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
