package com.mng.robotest.tests.domains.compranew.tests;

import org.testng.annotations.*;

public class CompraNew {

	@Test (
		groups={"Compra", "Smoke", "Checkout", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="[Registro Express][Compra] en Maldivas con nuevo checkout")  
	public void CNW001_Compra_RegExpress_Maldivas() throws Exception {
		new Cnw001().execute();
	}
	
}