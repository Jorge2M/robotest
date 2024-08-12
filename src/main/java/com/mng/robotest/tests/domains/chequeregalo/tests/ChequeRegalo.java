package com.mng.robotest.tests.domains.chequeregalo.tests;

import org.testng.annotations.*;

public class ChequeRegalo {

	@Test (
		testName="RGL001",			
		groups={"Compra", "Smoke", "Chequeregalo", "Canal:desktop_App:shop"},
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void chequeRegaloNew() throws Exception {
		new Rgl001().execute();
	}
	
	@Test (
		testName="RGL002",
		groups={"Compra", "Pedidomanto", "Chequeregalo", "Canal:desktop_App:shop"},
		description="Compra cheque regalo Old (Francia)")
	public void chequeRegaloOld() throws Exception {
		new Rgl002().execute();
	}
	
	@Test (
		testName="RGL003",			
		groups={"Compra", "Smoke", "Chequeregalo", "Checkout", "NewCheckout", "Canal:all_App:shop"},
		description="[India] Crea un cheque regalo y realiza una compra haciendo uso de él")
	public void useChequeRegalo() throws Exception {
		new Rgl003().execute();
	}	
	
}