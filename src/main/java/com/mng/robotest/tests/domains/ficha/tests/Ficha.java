package com.mng.robotest.tests.domains.ficha.tests;

import org.testng.annotations.Test;

import com.mng.robotest.testslegacy.utils.UtilsTest;


public class Ficha {
	
	@Test (
		testName="FIC001",			
		groups={"Ficha", "Smoke", "Canal:all_App:all"},
		description="Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
	public void primaryFeaturesReg() throws Exception {
		new Fic001().execute();
	}
	
	@Test (
		testName="FIC006",			
		groups={"Ficha", "Smoke", "Canal:all_App:all"},
		description="[Liechtenstein][versión Genesis] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
	public void primaryFeaturesGenesisReg() throws Exception {
		new Fic006().execute();
	}	

	@Test (
		testName="FIC002",			
		groups={"Ficha", "Smoke", "Canal:all_App:all"},
		description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones, total look")
	public void fichaFromSearchSecondaryFeaturesNoReg() throws Exception {
		new Fic002().execute();
	}

	@Test (
		testName="FIC003",			
		groups={"Ficha", "Smoke", "Canal:desktop_App:shop"}, 
		description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
	public void fichaFromGaleryCheckKcSafety() throws Exception {
		new Fic003().execute();
	}
	
	@Test (
		testName="FIC005",			
		groups={"Bordados", "Canal:desktop,mobile_App:shop"}, 
		alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
	public void articuloPersonalizableNoreg() throws Exception {
		//Todavía no se ha implementado en la ficha Genesis 
		//el tema de la personalización, le damos un margen de tiempo
		if (!UtilsTest.todayBeforeDate("2025-06-01")) {
			new Fic005().execute();
		}
	}

}