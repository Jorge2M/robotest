package com.mng.robotest.tests.domains.buscador.tests;

import org.testng.annotations.*;


public class Buscador {

	@Test (
		groups={"Buscador", "Smoke", "Canal:all_App:all"}, alwaysRun=true, 
		description="[Usuario no registrado] Búsqueda artículos existente / no existente")
	@Parameters({"categoriaProdExistente", "catProdInexistente"})
	public void BUS001_Buscador_NoReg(String categoriaProdExistente, String catProdInexistente) 
			throws Exception {
		new Bus001(categoriaProdExistente, catProdInexistente).execute();
	}
	

}