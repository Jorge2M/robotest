package com.mng.robotest.tests.domains.galeria.tests;

import org.testng.annotations.*;

public class Galeria {
	
	@Test (
		testName="GPO001",			
		groups={"Galeria", "Canal:mobile,tablet_App:all"}, 
		description="[Usuario reistrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")
	public void galeriaCamisasFiltros() throws Exception {
		new Gpo001().execute();
	}

	@Test (
		testName="GPO004",			
		groups={"Galeria", "Canal:all_App:all"},
		description="[Usuario no registrado][Chrome] Acceder a galería camisas. Filtro color. Scroll")
	public void navegaGaleria() throws Exception {
		new Gpo004().execute();
	}
	
	@Test (
		testName="GPO005",			
		groups={"Galeria", "Smoke", "Canal:all_App:all"}, 
		description="[Logged][ Acceder a galería. Navegación menú lateral de primer y segundo nivel. Selector de precios")
	public void galeriaMenuLateral() throws Exception {
		new Gpo005().execute();
	}
	
	@Test (
		testName="GPO009",			
		groups={"Galeria", "Smoke", "Canal:all_App:all"}, 
		description="[Logged][Monaco][Galeria new][Filtro precios, colores, tallas][Filtro submenús]")
	public void galeriaGenesisMonaco() throws Exception {
		new Gpo009().execute();
	}	
	
	@Test (
		testName="GPO006",			
		groups={"Galeria", "Canal:desktop_App:shop,outlet"}, 
		description=
			"Acceder a galería y testear el slider. Testeamos secuencias de sliders en ambas direcciones y " + 
			"finalmente las combinamos con cambios de color")
	public void sliderInDesktop() throws Exception {
		new Gpo006().execute();
	}

	@Test (
		testName="GPO007",			
		groups={"Galeria", "Smoke", "Avisame", "Canal:desktop,mobile_App:shop,outlet"},
		description="[Usuario registrado] Acceder a galería camisas. Forzar caso avisame en listado")
	public void galeriaCamisasAvisame() throws Exception {
		new Gpo007().execute();
	}
	
}