package com.mng.robotest.domains.manto.tests;

import org.testng.annotations.Test;

import com.mng.robotest.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.test.steps.manto.PageGestorEstadisticasPedidoSteps;
import com.mng.robotest.test.steps.manto.PageLoginMantoSteps;

public class Manto {

	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de la información referente a varios pedidos")
	public void MAN002_Consulta_ID_EAN() throws Exception {
		new Man002().execute();
	}

	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta y gestión de clientes")
	public void MAN003_GestionarClientes() throws Exception {
        new Man003().execute();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de cheques")
	public void MAN004_GestorCheques() throws Exception {
		new Man004().execute();
	}
	
	@Test(
		//enabled=false, //La operativa siempre falla en pre por timeout			
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Consulta de estadísticas de pedidos")
	public void MAN005_GestorEstadisticasPedidos() throws Exception {
		new PageLoginMantoSteps().login();
		//new PageSelTdaMantoSteps().selectTienda(almacenEspanya, codigoEspanya);
		new PageMenusMantoSteps().goToGestorEstadisticasPedido();
		
		var pageGestorEstadisticasPedidoSteps = new PageGestorEstadisticasPedidoSteps();
		pageGestorEstadisticasPedidoSteps.searchZalandoOrdersInformation();
		pageGestorEstadisticasPedidoSteps.compareLastDayInformation();
	}
	
	@Test(
		groups={"Manto", "Canal:desktop_App:all"}, 
		description="Comprueba el correcto funcionamiento del ordenador de prendas")
	public void MAN008_Ordenador_de_Prendas() throws Exception {
		new Man008().execute();
	}
}
