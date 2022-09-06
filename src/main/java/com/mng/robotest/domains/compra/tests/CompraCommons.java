package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.manto.PedidoNavigations;


public class CompraCommons {

	private CompraCommons() {}
	
	public static void checkPedidosManto(CopyOnWriteArrayList<DataPedido> listPedidos, AppEcom app, WebDriver driver) 
			throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		
		checkPedidosManto(listChecks, listPedidos, app, driver);
	}
	
	public static void checkPedidosManto(
			List<CheckPedido> listChecks, CopyOnWriteArrayList<DataPedido> listPedidos, AppEcom app, WebDriver driver) 
					throws Exception {
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(listPedidos, listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, app, driver);
	}
}
