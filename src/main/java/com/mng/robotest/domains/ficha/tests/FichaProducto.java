package com.mng.robotest.domains.ficha.tests;

import org.testng.annotations.Test;


public class FichaProducto {
	
	@Test (
		groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Se testean las features principales de una ficha con origen el buscador: añadir a la bolsa, selección color/talla, buscar en tienda, añadir a favoritos")
	public void FIC001_FichaFromSearch_PrimaryFeatures_Reg() throws Exception {
		new Fic001().execute();
	}

	@Test (
		groups={"FichaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado] Se testean las features secundarias de una ficha con origen el buscador: guía de tallas, carrusel imágenes, imagen central, panel de opciones, total look")
	public void FIC002_FichaFromSearch_SecondaryFeatures_NoReg() throws Exception {
		new Fic002().execute();
	}
	
	@Test (
		groups={"FichaProducto", "Canal:desktop_App:shop"}, alwaysRun=true, 
		description="[Usuario no registrado] Desde Corea/coreano, se testea una ficha con origen la Galería validando el panel KcSafety")
	public void FIC003_FichaFromGalery_CheckKcSafety() throws Exception {
		new Fic003().execute();
	}
	
	@Test (
		groups={"FichaProducto", "Canal:desktop,mobile_App:shop"}, 
		alwaysRun=true, description="[Usario no registrado] Testeo Personalización bordados")
	public void FIC005_Articulo_Personalizable_Noreg() throws Exception {
		new Fic005().execute();
	}

}