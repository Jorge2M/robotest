package com.mng.robotest.test.pageobject.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;


public class PageHomeMarcas extends PageObjTM {

	private final AppEcom app;
	
	public PageHomeMarcas(AppEcom app, WebDriver driver) {
		super(driver);
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
