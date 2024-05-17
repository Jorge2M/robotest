package com.mng.robotest.tests.domains.ayuda.tests;

import org.testng.annotations.Test;


public class Ayuda {
	
	@Test(
		testName="AYU001",				
		groups={"Ayuda", "Smoke", "Canal:all_App:shop,outlet"}, 
		description="Verificar que los elementos de la página ayuda están correctamente presentes")
	public void ayuda() throws Exception {
		new Ayu001().execute();
	}
	
}
