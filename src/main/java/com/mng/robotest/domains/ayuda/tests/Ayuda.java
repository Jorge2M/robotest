package com.mng.robotest.domains.ayuda.tests;

import org.testng.annotations.Test;


public class Ayuda {
	
	public Ayuda() {}  
	
	@Test(
		groups = { "Ayuda", "Canal:all_App:shop" }, alwaysRun = true,
		description="Verificar que los elementos de la página ayuda están correctamente presentes")
	public void AYU001_Data() throws Exception {
		new Ayu001().execute();
	}
}
