package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

public class SecLineasMobilOutlet extends SecLineasMobil {

	private static String IniXPathLinkLinea = XPathCapaLevelLinea + "//li/div[@class[contains(.,'menu-item-label')] and @id";
	private static String XPathLinkLineaMujer = IniXPathLinkLinea + "='outlet']";
	private static String XPathLinkLineaHombre = IniXPathLinkLinea + "='outletH']";
	private static String XPathLinkLineaNina = IniXPathLinkLinea + "='outletA']";
	private static String XPathLinkLineaNino =IniXPathLinkLinea + "='outletO']";
	private static String XPathLinkLineaVioleta = IniXPathLinkLinea + "='outletV']";

	private static String IniXPathLinkSublinea = XPathCapa2onLevelMenu + "//div[@data-label[contains(.,'interior-"; 
	private static String XPathLinkSublineaNina =  IniXPathLinkSublinea + "nina')]]";
	private static String XPathLinkSublineaTeenNina =  IniXPathLinkSublinea + "teen')]]";
	private static String XPathLinkSublineaBebeNina = IniXPathLinkSublinea + "bebe_nina')]]";
	
	private static String XPathLinkSublineaNino = IniXPathLinkSublinea + "nino')]]";
	private static String XPathLinkSublineaTeenNino =  IniXPathLinkSublinea + "teen')]]";
	private static String XPathLinkSublineaBebeNino = IniXPathLinkSublinea + "bebe_nino')]]";
	
	public SecLineasMobilOutlet(WebDriver driver) {
		super(AppEcom.outlet, driver);
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
		case violeta: 
			return XPathLinkLineaVioleta;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the Outlet for the mobile channel");
		}
	}

	@Override
	public String getXPathSublineaNinosLink(SublineaNinosType sublineaType) {
		switch (sublineaType) {
		case nina:
			return XPathLinkSublineaNina;
		case teen_nina:
			return XPathLinkSublineaTeenNina;
		case bebe_nina:
			return XPathLinkSublineaBebeNina;
		case nino:
			return XPathLinkSublineaNino;
		case teen_nino:
			return XPathLinkSublineaTeenNino;
		case bebe_nino:
		default:
			return XPathLinkSublineaBebeNino;
		}
	}
}
