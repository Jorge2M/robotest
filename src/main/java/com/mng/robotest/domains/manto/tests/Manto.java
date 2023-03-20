package com.mng.robotest.domains.manto.tests;

import org.testng.annotations.Test;

public class Manto {

	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN001_Consulta_ID_EAN() throws Exception {
		new Man001().execute();
	}

	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta y gestión de clientes")
	public void MAN002_GestionarClientes() throws Exception {
        new Man002().execute();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de cheques")
	public void MAN003_GestorCheques() throws Exception {
		new Man003().execute();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de estadísticas de pedidos")
	public void MAN004_GestorEstadisticasPedidos() throws Exception {
		new Man004().execute();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN005_Ordenador_de_Prendas() throws Exception {
		new Man005().execute();
	}
}
