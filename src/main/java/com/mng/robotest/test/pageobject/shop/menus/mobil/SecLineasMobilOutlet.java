package com.mng.robotest.test.pageobject.shop.menus.mobil;

import com.mng.robotest.test.beans.Linea.LineaType;

public class SecLineasMobilOutlet extends SecLineasMobil {

	private static String IniXPathLinkLinea = XPathCapaLevelLinea + "//li/div[@class[contains(.,'menu-item-label')] and @id";
	private static String XPathLinkLineaMujer = IniXPathLinkLinea + "='outlet']";
	private static String XPathLinkLineaHombre = IniXPathLinkLinea + "='outletH']";
	private static String XPathLinkLineaNina = IniXPathLinkLinea + "='outletA']";
	private static String XPathLinkLineaNino =IniXPathLinkLinea + "='outletO']";
	private static String XPathLinkLineaVioleta = IniXPathLinkLinea + "='outletV']";
	
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
		case violeta: 
			return XPathLinkLineaVioleta;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the Outlet for the mobile channel");
		}
	}

}
