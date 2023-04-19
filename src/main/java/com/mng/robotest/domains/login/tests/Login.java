package com.mng.robotest.domains.login.tests;

import org.testng.annotations.*;

public class Login {

	@Test (
		groups={"Login", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar inicio sesión con usuario incorrecto + recuperar password")
	public void LOG001_IniciarSesion_NOK() throws Exception {
		new Log001().execute();
	}

	@Test (
		groups={"Login", "Canal:desktop_App:shop,outlet"}, 
		description="Verificar inicio sesión usuario credenciales correctas")
	public void LOG002_IniciarSesion_OK() throws Exception {
		new Log002().execute();
	}
	
	@Test (
		groups={"Login", "Bolsa", "Compra", "Canal:mobile_App:shop"}, 
		description="Flujo bolsa -> identificación -> checkout")
	public void LOG003_Bolsa_Identificacion() throws Exception {
		new Log003().execute();
	}	
}