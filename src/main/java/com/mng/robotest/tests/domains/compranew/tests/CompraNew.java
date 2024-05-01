package com.mng.robotest.tests.domains.compranew.tests;

import org.testng.annotations.*;

public class CompraNew {

	@Test (
		testName="CNW001",			
		groups={"Compra", "Smoke", "Checkout", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="[Registro Express][Compra] en Maldivas con nuevo checkout")  
	public void compraRegExpressMaldivas() throws Exception {
		new Cnw001().execute();
	}
	
}