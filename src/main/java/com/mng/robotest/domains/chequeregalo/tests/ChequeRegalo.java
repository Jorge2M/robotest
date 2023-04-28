package com.mng.robotest.domains.chequeregalo.tests;

import org.testng.annotations.*;

public class ChequeRegalo {

	@Test (
		groups={"Compra", "Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Consulta datos cheque existente y posterior compra Cheque regalo New (España)")
	public void COM004_Cheque_Regalo_New() throws Exception {
		new Reg001().execute();
	}
	
	@Test (
		groups={"Compra", "Pedidomanto", "Chequeregalo", "Canal:desktop_App:shop"}, alwaysRun=true,
		description="Compra cheque regalo Old (Francia)")
	public void COM007_Cheque_Regalo_Old() throws Exception {
		new Reg002().execute();
	}
	
	
}