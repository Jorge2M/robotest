package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.base.PageBase;

public class PageHomeMarcas extends PageBase {

	public boolean isHomeMarcasMultimarcasDependingCountry() {
		//Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
		int numLineas = dataTest.getPais().getShoponline().getNumLineasTiendas(app);
		if (numLineas < 3 || 
			dataTest.getCodigoPais().equals("512") || 
			dataTest.getCodigoPais().equals("504") || 
			dataTest.getCodigoPais().equals("520")) {
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
