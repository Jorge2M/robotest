package com.mng.robotest.tests.domains.votfconsole.tests;

import org.testng.annotations.Test;

public class ConsolaVotf {

	@Test (
		testName="VTF001",			
		groups={"Consola_Votf", "Canal:desktop_App:votf"},
		description="[PRE] Generar pedido mediante la consola de VOTF")
	public void generarPedido() throws Exception {
		new Vtf001().execute();
	}

}