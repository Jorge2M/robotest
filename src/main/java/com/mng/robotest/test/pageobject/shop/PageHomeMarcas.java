package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.transversal.PageBase;

public class PageHomeMarcas extends PageBase {

	public boolean isHomeMarcasMultimarcasDependingCountry() {
		//Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
		int numLineas = dataTest.pais.getShoponline().getNumLineasTiendas(app);
		if (numLineas < 3 || 
			dataTest.pais.getCodigo_pais().equals("512") || 
			dataTest.pais.getCodigo_pais().equals("504") || 
			dataTest.pais.getCodigo_pais().equals("520")) {
			if(!driver.getPageSource().contains("home")) {
				return false;
			}
		}  else {
			if (!driver.getPageSource().contains("homeMarcas")) {
				return false;
			}
		}
		return true;
	}
}
