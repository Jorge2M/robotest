package com.mng.robotest.tests.domains.bolsa.tests;

import org.testng.annotations.*;

public class Bolsa {
	
	@Test (
		groups={"Bolsa", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="[Usuario no registrado] Añadir artículo a la bolsa")
	public void BOR001_AddBolsaFromGaleria_NoReg() throws Exception {
		new Bor001().execute();
	}

	@Test (
		groups={"Bolsa", "Canal:desktop_App:all"}, 
		description="[Usuario no registrado] Añadir y eliminar artículos de la bolsa")
	public void BOR005_Gest_Prod_Bolsa_Noreg() throws Exception {
		new Bor005().execute();
	}

	@Test (
		groups={"Bolsa", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="[Usuario registrado] Añadir artículo a la bolsa")
	public void BOR002_AnyadirBolsa_yCompra_SiReg() throws Exception {
		new Bor002().execute();
	}

	@Test (
		groups={"Bolsa", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="[Usuario registrado] Añadir y eliminar artículos de la bolsa")
	public void BOR006_Gest_Prod_Bolsa_Sireg() throws Exception {
		new Bor006().execute();
	}

}
