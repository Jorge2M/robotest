package com.mng.robotest.tests.domains.otros.tests;

import org.testng.annotations.*;

public class Otras {
	
	@Test (
		testName="OTR001",	
		groups={"Redirects", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar acceso url desde email")
	public void checkRedirects() throws Exception {
		new Otr001().execute();
	}
	
	@Test (
		testName="OTR002", 
		groups={"Google", "Canal:desktop_App:shop"}, 
		description="Verificar en google la existencia de referencia Mango")
	public void checkBusquedaGoogle() throws Exception {
		new Otr002().execute();
	}
	
	@Test (
		testName="OTR005",			
		groups={"Redirects", "Canal:desktop_App:shop"}, 
		description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
	public void accesoJapon() throws Exception {
		new Otr005().execute();
	}
	
}