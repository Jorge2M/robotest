package com.mng.robotest.domains.personalizacion.tests;

import org.testng.annotations.Test;

public class Personalizacion {

	@Test (
		groups={"Personalizacion", "Canal:desktop_App:shop"},
		description="Checkea que la personalización está activa a nivel de las galerías de productos")
	public void PER001_Galeria_Personalizada() throws Exception {
		new Per001().execute();
	}
	
}
