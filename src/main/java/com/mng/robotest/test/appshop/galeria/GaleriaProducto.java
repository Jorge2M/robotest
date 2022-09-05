package com.mng.robotest.test.appshop.galeria;

import org.testng.annotations.*;

public class GaleriaProducto {
	
	@Test (
		groups={"GaleriaProducto", "Canal:mobile,tablet_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")
	public void GPO001_Galeria_Camisas() throws Exception {
		new Gpo001().execute();
	}

	@Test (
		groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado][Chrome] Acceder a galería camisas. Filtro color. Scroll")
	public void GPO004_Navega_Galeria() throws Exception {
		new Gpo004().execute();
	}
	
	@Test (
		groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario registrado] Acceder a galería. Navegación menú lateral de primer y segundo nivel. Selector de precios")
	public void GPO005_Galeria_Menu_Lateral() throws Exception {
		new Gpo005().execute();
	}
	
	@Test (
		groups={"GaleriaProducto", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description=
			"Acceder a galería y testear el slider. Testeamos secuencias de sliders en ambas direcciones y " + 
			"finalmente las combinamos con cambios de color")
	public void GPO006_SliderInDesktop() throws Exception {
		new Gpo006().execute();
	}

	@Test (
		groups={"GaleriaProducto", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="[Usuario registrado] Acceder a galería camisas. Forzar caso avisame en listado")
	public void GPO007_Galeria_Camisas() throws Exception {
		new Gpo007().execute();
	}
	
}