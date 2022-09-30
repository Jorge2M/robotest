package com.mng.robotest.test.pageobject.shop.menus.device;

import com.mng.robotest.test.beans.Linea.LineaType;

public class SecLineasMobilOutlet extends SecLineasDeviceOutlet {

	private static final String XPATH_CAPA_LEVEL_LINEA = "//div[@class[contains(.,'menu-section')]]";
	private static final String INI_XPATH_LINK_LINEA = XPATH_CAPA_LEVEL_LINEA + "//li/div[@class[contains(.,'menu-item-label')] and @id";
	private static final String XPATH_LINK_LINEA_MUJER = INI_XPATH_LINK_LINEA + "='outlet']";
	private static final String XPATH_LINK_LINEA_HOMBRE = INI_XPATH_LINK_LINEA + "='outletH']";
	private static final String XPATH_LINK_LINEA_NINA = INI_XPATH_LINK_LINEA + "='outletA']";
	private static final String XPATH_LINK_LINEA_NINO =INI_XPATH_LINK_LINEA + "='outletO']";
	private static final String XPATH_LINK_LINEA_VIOLETA = INI_XPATH_LINK_LINEA + "='outletV']";
	
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
		case violeta: 
			return XPATH_LINK_LINEA_VIOLETA;
		default:
			throw new IllegalArgumentException("The line " + lineaType + " is not present in the Outlet for the mobile channel");
		}
	}

}
