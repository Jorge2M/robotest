package com.mng.robotest.tests.domains.ayuda.tests;

import org.testng.annotations.Test;


public class Ayuda {
	
	@Test(
		groups={"Ayuda", "Smoke", "Canal:all_App:shop,outlet"}, alwaysRun = true,
		description="Verificar que los elementos de la página ayuda están correctamente presentes")
	public void AYU001() throws Exception {
		new Ayu001().execute();
	}
	
}
