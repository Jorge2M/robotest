package com.mng.robotest.test.pageobject.shop.menus.mobil;

import com.mng.robotest.test.beans.Linea.LineaType;

public class SecLineasTabletOutlet extends SecLineasTablet {

	static String XPathCapaLevelLinea = "//ul[@class[contains(.,'menu-section-brands')]]";
	static String IniXPathLinkLinea = XPathCapaLevelLinea + "//div[@class[contains(.,'menu-item-label')] and @id";
	static String XPathLinkLineaMujer = IniXPathLinkLinea + "='outlet']";
	static String XPathLinkLineaHombre = IniXPathLinkLinea + "='outletH']";
	static String XPathLinkLineaNina = IniXPathLinkLinea + "='outletA']";
	static String XPathLinkLineaNino =IniXPathLinkLinea + "='outletO']";
	static String XPathLinkLineaKids =IniXPathLinkLinea + "='kids']"; //p.e. Bolivia
	static String XPathLinkLineaVioleta = IniXPathLinkLinea + "='outletV']";
	
	@Override
	public String getXPathLineaLink(LineaType lineaType) throws IllegalArgumentException {
		switch (lineaType) {
		case she: 
			return XPathLinkLineaMujer;
		case he: 
			return XPathLinkLineaHombre;
		case nina:
			return XPathLinkLineaNina;
		case nino: 
			return XPathLinkLineaNino;
		case kids: 
			return XPathLinkLineaKids;
		case violeta: 
			return XPathLinkLineaVioleta;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}
	
}
