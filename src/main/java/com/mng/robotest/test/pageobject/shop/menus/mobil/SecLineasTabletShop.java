package com.mng.robotest.test.pageobject.shop.menus.mobil;

import com.mng.robotest.test.beans.Linea.LineaType;

public class SecLineasTabletShop extends SecLineasTablet {
	
	private static final String XPATH_CAPA_LEVEL_LINEA = "//ul[@class[contains(.,'menu-section-brands')]]";
	private static final String INI_XPATH_LINK_LINEA = XPATH_CAPA_LEVEL_LINEA + "//div[@class[contains(.,'menu-item-label')] and @id";
	private static final String XPATH_LINK_LINEA_MUJER = INI_XPATH_LINK_LINEA + "='she']";
	private static final String XPATH_LINK_LINEA_HOMBRE = INI_XPATH_LINK_LINEA + "='he']";
	private static final String XPATH_LINK_LINEA_NINA = INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_NINO =INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_KIDS =INI_XPATH_LINK_LINEA + "='kids']"; //p.e. Bolivia
	private static final String XPATH_LINK_LINEA_VIOLETA = INI_XPATH_LINK_LINEA + "='violeta']";
	private static final String XPATH_LINK_LINEA_HOME = INI_XPATH_LINK_LINEA + "='home']";
	
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

}
