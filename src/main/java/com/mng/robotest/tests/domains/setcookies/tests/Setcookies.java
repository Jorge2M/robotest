package com.mng.robotest.tests.domains.setcookies.tests;

import org.testng.annotations.Test;

public class Setcookies {

	@Test (
		testName="COK001",			
		groups={"Setcookies", "Smoke", "Compra", "Pedidomanto", "Checkout", "Canal:all_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra sin permitir todas las cookies y con tarjeta real")
	public void fluxCompraWithoutCookies() throws Exception {
		new Cok001().execute();
	}

}
