package com.mng.robotest.test.pageobject.shop.menus.mobil;

import com.mng.robotest.test.beans.Linea.LineaType;

public class SecLineasMobilShop extends SecLineasMobil {

	private static final String INI_XPATH_LINK_LINEA = XPATH_CAPA_LEVEL_LINEA + "//div[@class[contains(.,'brand-item')] and @id";
	private static final String XPATH_LINK_LINEA_MUJER = INI_XPATH_LINK_LINEA + "='she' or @id='prendas_she']";
	private static final String XPATH_LINK_LINEA_HOMBRE = INI_XPATH_LINK_LINEA + "='he']";
	private static final String XPATH_LINK_LINEA_NINA = INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_NINO = INI_XPATH_LINK_LINEA + "='kids']";
	private static final String XPATH_LINK_LINEA_TEEN = INI_XPATH_LINK_LINEA + "='teen']";
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
}
