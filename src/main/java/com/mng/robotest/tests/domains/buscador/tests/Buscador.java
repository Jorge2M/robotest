package com.mng.robotest.tests.domains.buscador.tests;

import org.testng.annotations.*;


public class Buscador {

	@Test (
		testName="BUS001",				
		groups={"Buscador", "Smoke", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado] Búsqueda artículos existente / no existente")
	@Parameters({"categoriaProdExistente", "catProdInexistente"})
	public void buscadorNoReg(String categoriaProdExistente, String catProdInexistente) 
			throws Exception {
		new Bus001(categoriaProdExistente, catProdInexistente).execute();
	}
	

}