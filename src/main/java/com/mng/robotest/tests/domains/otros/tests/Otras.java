package com.mng.robotest.tests.domains.otros.tests;

import org.testng.annotations.*;

public class Otras {
	
	@Test (
		groups={"Redireccion", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar acceso url desde email")
	public void OTR001_check_Redirects() throws Exception {
		new Otr001().execute();
	}
	
	@Test (
		groups={"Google", "Canal:desktop_App:shop"}, 
		description="Verificar en google la existencia de referencia Mango")
	public void OTR002_check_Busqueda_Google() throws Exception {
		new Otr002().execute();
	}
	
	@Test (
		groups={"Acceso_Japon", "Canal:desktop_App:shop"}, 
		description="Acceso al país Japón desde la preHome y comprobación de que redirige a la shop específica de este país")
	public void OTR005_accesoJapon() throws Exception {
		new Otr005().execute();
	}
	
}