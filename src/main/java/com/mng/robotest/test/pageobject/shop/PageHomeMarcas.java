package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;


public class PageHomeMarcas extends PageBase {

	private final AppEcom app;
	
	public PageHomeMarcas(AppEcom app) {
		this.app = app;
	}	
	
	public boolean isHomeMarcasMultimarcasDependingCountry(Pais pais) {
		//Comprobamos el número de líneas e incluimos una excepción en Chile/Perú/Paraguay (códigos 512/504/520) el cual tiene 4 líneas pero se define como "home"
		int numLineas = pais.getShoponline().getNumLineasTiendas(app);
		if (numLineas < 3 || pais.getCodigo_pais().equals("512") || pais.getCodigo_pais().equals("504") || pais.getCodigo_pais().equals("520")) {
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
