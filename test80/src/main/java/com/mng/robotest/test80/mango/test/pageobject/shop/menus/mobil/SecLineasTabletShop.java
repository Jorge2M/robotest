package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;

public class SecLineasTabletShop extends SecLineasTablet {
	
	static String XPathCapaLevelLinea = "//ul[@class[contains(.,'menu-section-brands')]]";
	static String IniXPathLinkLinea = XPathCapaLevelLinea + "//div[@class[contains(.,'menu-item-label')] and @id";
	static String XPathLinkLineaMujer = IniXPathLinkLinea + "='she']";
	static String XPathLinkLineaHombre = IniXPathLinkLinea + "='he']";
	static String XPathLinkLineaNina = IniXPathLinkLinea + "='kids']";
	static String XPathLinkLineaNino =IniXPathLinkLinea + "='kids']";
	static String XPathLinkLineaKids =IniXPathLinkLinea + "='kids']"; //p.e. Bolivia
	static String XPathLinkLineaHome = IniXPathLinkLinea + "='home']";
	
	public SecLineasTabletShop(WebDriver driver) {
		super(AppEcom.shop, driver);
	}
	
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
		case home:
			return XPathLinkLineaHome;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}

}
