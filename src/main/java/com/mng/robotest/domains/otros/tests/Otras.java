package com.mng.robotest.domains.otros.tests;

import org.testng.annotations.*;

public class Otras {
	
	@Test (
		groups={"Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar acceso url desde email")
	public void OTR001_check_Redirects() throws Exception {
		new Otr001().execute();
	}
	
	@Test (
		groups={"Otras", "Canal:desktop_App:shop"}, 
		description="Verificar en google la existencia de referencia Mango")
	public void OTR002_check_Busqueda_Google() throws Exception {
		new Otr002().execute();
	}
	
	@Test (
		groups={"Otras", "Canal:desktop_App:all"}, 
		description="Verificar el cambio de país a través del modal")
	public void OTR003_cambioPais() throws Exception {
		new Otr003().execute();
	}

	@Test (
		groups={"Otras", "Canal:desktop_App:shop"}, 
		description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
	public void OTR005_accesoJapon() throws Exception {
		new Otr005().execute();
	}	
	
	//TODO cuando lo activen en Tablet añadir ese canal
	@Test (
		groups={"Otras", "Canal:desktop,mobile_App:shop"}, 
		description="Chequear el ChatBot")
	public void OTR006_chatBot() throws Exception {
		new Otr006().execute();
	}
}