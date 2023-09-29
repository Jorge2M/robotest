package com.mng.robotest.tests.domains.votfconsole.tests;

import org.testng.annotations.Test;

public class ConsolaVotf {

	@Test (
		groups={"Consola_Votf", "Canal:desktop_App:votf"},
		description="[PRE] Generar pedido mediante la consola de VOTF")
	public void VTF001_GenerarPedido() throws Exception {
		new Vtf001().execute();
	}

}