package com.mng.robotest.test80.mango.test.pageobject.shop.menus.mobil;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;

public class SecLineasMobilOutlet extends SecLineasMobil {

	static String IniXPathLinkLinea = XPathCapaLevelLinea + "//li/div[@class[contains(.,'menu-item-label')] and @id";
	static String XPathLinkLineaMujer = IniXPathLinkLinea + "='outlet']";
	static String XPathLinkLineaHombre = IniXPathLinkLinea + "='outletH']";
	static String XPathLinkLineaNina = IniXPathLinkLinea + "='outletA']";
	static String XPathLinkLineaNino =IniXPathLinkLinea + "='outletO']";
	static String XPathLinkLineaVioleta = IniXPathLinkLinea + "='outletV']";

	static String IniXPathLinkSublinea = XPathCapa2onLevelMenu + "//div[@data-label[contains(.,'interior-"; 
	static String XPathLinkSublineaNina =  IniXPathLinkSublinea + "nina')]]";
	static String XPathLinkSublineaBebeNina = IniXPathLinkSublinea + "bebe_nina')]]";
	static String XPathLinkSublineaNino = IniXPathLinkSublinea + "nino')]]";
	static String XPathLinkSublineaBebeNino = IniXPathLinkSublinea + "bebe_nino')]]";
	
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
		case bebe_nina:
			return XPathLinkSublineaBebeNina;
		case nino:
			return XPathLinkSublineaNino;
		case bebe_nino:
		default:
			return XPathLinkSublineaBebeNino;
		}
	}
}
