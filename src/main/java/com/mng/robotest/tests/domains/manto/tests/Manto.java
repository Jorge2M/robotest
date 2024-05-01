package com.mng.robotest.tests.domains.manto.tests;

import org.testng.annotations.Test;

public class Manto {

	@Test(
		testName="MAN001",			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de la información referente a varios pedidos")
	public void consultaIdEAN() {
		new Man001().execute();
	}

	@Test(
		testName="MAN002",			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta y gestión de clientes")
	public void gestionarClientes() {
        new Man002().execute();
	}
	
	@Test(
		testName="MAN003",			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de cheques")
	public void gestorCheques() {
		new Man003().execute();
	}
	
	@Test(
		testName="MAN004",			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de estadísticas de pedidos")
	public void gestorEstadisticasPedidos() {
		new Man004().execute();
	}
	
	@Test(
		testName="MAN005",			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void ordenadorPrendas() {
		new Man005().execute();
	}
	
}
