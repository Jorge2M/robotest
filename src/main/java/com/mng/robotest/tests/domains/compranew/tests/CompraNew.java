package com.mng.robotest.tests.domains.compranew.tests;

import org.testng.annotations.*;

public class CompraNew {

	@Test (
		testName="CNW001",	
		groups={"Compra", "Smoke", "Checkout", "NewCheckout", "Canal:desktop,mobile_App:shop"}, 
		description="[Compra][Egypt] Borrado/Adición/Uso de tarjeta guardada")  
	public void compraSerbiaTrjSaved() throws Exception {
		new Cnw001().execute();
	}
	
	@Test (
		testName="CNW002",	
		groups={"Compra", "Smoke", "Checkout", "NewCheckout", "Canal:desktop,mobile_App:shop"}, 
		description="[Compra][Usuario logado] Serbia con nuevo checkout")  
	public void compraSerbiaLogged() throws Exception {
		new Cnw002().execute();
	}

}