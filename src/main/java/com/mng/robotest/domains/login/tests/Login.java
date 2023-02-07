package com.mng.robotest.domains.login.tests;

import org.testng.annotations.*;

public class Login {

	@Test (
		groups={"Login", "Canal:desktop_App:shop,outlet"}, /*dependsOnMethods = {"SES002_Registro_OK"},*/ alwaysRun=true, 
		description="Verificar inicio sesión con usuario incorrecto")
	public void SES001_IniciarSesion_NOK() throws Exception {
		new Log001().execute();
	}

	@Test (
		groups={"Login", "Canal:desktop_App:shop,outlet"}, alwaysRun=true, 
		description="[Usuario registrado] Verificar inicio sesión")
	public void SES002_IniciarSesion_OK() throws Exception {
		new Ses002().execute();
	}
}