package com.mng.robotest.tests.domains.bolsa.tests;

import org.testng.annotations.*;

public class Bolsa {
	
	@Test (
		testName="BOR001",			
		groups={"Bolsa", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="[Usuario no registrado] Añadir artículo a la bolsa")
	public void addBolsaFromGaleriaNoReg() throws Exception {
		new Bor001().execute();
	}

	@Test (
		testName="BOR005",
		groups={"Bolsa", "Canal:desktop_App:all"}, 
		description="[Usuario no registrado] Añadir y eliminar artículos de la bolsa")
	public void gestProdBolsaNoreg() throws Exception {
		new Bor005().execute();
	}

	@Test (
		testName="BOR002",
		groups={"Bolsa", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="[Usuario registrado] Añadir artículo a la bolsa")
	public void anyadirBolsayCompraSiReg() throws Exception {
		new Bor002().execute();
	}

	@Test (
		testName="BOR006",
		groups={"Bolsa", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="[Usuario registrado] Añadir y eliminar artículos de la bolsa")
	public void gestProdBolsaSireg() throws Exception {
		new Bor006().execute();
	}

}
