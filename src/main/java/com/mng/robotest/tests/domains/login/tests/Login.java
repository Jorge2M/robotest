package com.mng.robotest.tests.domains.login.tests;

import org.testng.annotations.*;

public class Login {

	@Test (
		testName="LOG001",			
		groups={"Login", "Smoke", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar inicio sesión con usuario incorrecto + recuperar password")
	public void iniciarSesionNok() throws Exception {
		new Log001().execute();
	}

	@Test (
		testName="LOG002",			
		groups={"Login", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar inicio sesión usuario credenciales correctas")
	public void iniciarSesionOk() throws Exception {
		new Log002().execute();
	}
	
	@Test (
		testName="LOG003",
		groups={"Login", "Bolsa", "Checkout", "Compra", "Canal:mobile_App:shop,outlet"}, 
		description="Flujo bolsa -> identificación -> checkout")
	public void bolsaIdentificacion() throws Exception {
		new Log003().execute();
	}	
	
//	@Test (
//		testName="LOG004",	
//		groups={"SoftLogin", "Canal:desktop_App:shop"}, 
//		description="Forzar estado SoftLogin para usuario y chequear funcionalidades")
//	public void softLogin() throws Exception {
//		new Log004().execute();
//	}	
}