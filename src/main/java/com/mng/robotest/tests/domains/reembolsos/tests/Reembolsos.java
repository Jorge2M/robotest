package com.mng.robotest.tests.domains.reembolsos.tests;

public class Reembolsos {
	
	/**
	 * Script correspondiente al caso de prueba que configura el reembolso vía transferencia y saldo en cuenta en arabia/inglés 
	 */
	//TODO conseguir reactivar el usuario mng_test_SA_pruebaSaldo@mango.com
//	@Test (
//		groups={"Reembolso", "Canal:desktop,mobile_App:shop"}, 
//		description="Configura el reembolso vía transferencia y saldo en cuenta para un país/idioma determinado")
	public void REE001_configureReembolso() throws Exception {
		new Ree001().execute();
	}
	
//	@Test (
//		groups={"Reembolso", "Canal:desktop,mobile_App:shop"},
//		description="Se realiza un Checkout utilizando Saldo en Cuenta. Se accede a la configuración al inicio y al final para comprobar que el saldo en cuenta se resta correctamente")
	public void REE002_checkoutWithSaldoCta() throws Exception {
		new Ree001().execute();
	}
}