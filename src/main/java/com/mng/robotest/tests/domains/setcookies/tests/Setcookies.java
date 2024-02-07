package com.mng.robotest.tests.domains.setcookies.tests;

import org.testng.annotations.Test;

public class Setcookies {

	@Test (
		groups={"Setcookies", "Smoke", "Compra", "Pedidomanto", "Checkout", "Canal:all_App:shop,outlet"}, alwaysRun=true,
		description="[Usuario no registrado] Compra sin permitir todas las cookies y con tarjeta real")
	public void COK001_Flux_Compra_Without_Cookies() throws Exception {
		new Cok001().execute();
	}

}
