package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.mng.robotest.domains.transversal.NavigationBase;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.manto.PedidoNavigations;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.FactoryPagos;
import com.mng.robotest.test.steps.shop.checkout.pagosfactory.PagoSteps;

public class CompraSteps extends NavigationBase {

	public CompraSteps() {}
	
	public void startPayment(DataPago dataPago, boolean executePayment) throws Exception {
		dataPago.setPago(dataTest.pais.getPago("VISA"));
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dataPago);
		pagoSteps.startPayment(executePayment);
	}
	
	public void checkPedidosManto(CopyOnWriteArrayList<DataPedido> listPedidos) throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido);
		
		checkPedidosManto(listChecks, listPedidos);
	}
	
	public void checkPedidosManto(List<CheckPedido> listChecks, CopyOnWriteArrayList<DataPedido> listPedidos) 
			throws Exception {
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(listPedidos, listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, app, driver);
	}
}
