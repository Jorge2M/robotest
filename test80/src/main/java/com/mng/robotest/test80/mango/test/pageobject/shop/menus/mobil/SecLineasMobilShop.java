package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;


import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;

public class SecLineasMobilShop extends SecLineasMobil {

	static String IniXPathLinkLinea = XPathCapaLevelLinea + "//div[@class[contains(.,'brand-item')] and @id";
	static String XPathLinkLineaMujer = IniXPathLinkLinea + "='she']";
	static String XPathLinkLineaHombre = IniXPathLinkLinea + "='he']";
	static String XPathLinkLineaNina = IniXPathLinkLinea + "='kids']";
	static String XPathLinkLineaNino = IniXPathLinkLinea + "='kids']";
	static String XPathLinkLineaTeen = IniXPathLinkLinea + "='teen']";
	static String XPathLinkLineaKids =IniXPathLinkLinea + "='kids']"; //p.e. Bolivia
	static String XPathLinkLineaVioleta = IniXPathLinkLinea + "='violeta']";
	static String XPathLinkLineaHome = IniXPathLinkLinea + "='home']";

	public SecLineasMobilShop(WebDriver driver) {
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
		case teen:
			return XPathLinkLineaTeen;
		case kids: 
			return XPathLinkLineaKids;
		case violeta: 
			return XPathLinkLineaVioleta;
		case home:
			return XPathLinkLineaHome;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the movil channel");
		}
	}
}
