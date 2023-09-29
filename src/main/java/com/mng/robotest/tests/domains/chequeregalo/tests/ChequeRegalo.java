package com.mng.robotest.tests.domains.chequeregalo.tests;

import org.testng.annotations.*;

public class ChequeRegalo {

	@Test (
		groups={"Compra", "Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void RGL001_Cheque_Regalo_New() throws Exception {
		new Rgl001().execute();
	}
	
	@Test (
		groups={"Compra", "Pedidomanto", "Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void RGL002_Cheque_Regalo_Old() throws Exception {
		new Rgl002().execute();
	}
	
	
}