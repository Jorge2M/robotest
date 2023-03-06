package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.compra.payments.FactoryPagos;
import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.datastored.DataCheckPedidos;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.manto.PedidoNavigations;

public class CompraSteps extends StepBase {

	public CompraSteps() {}
	
	public void startPayment(DataPago dataPago, boolean executePayment) throws Exception {
		if (dataPago.getPago()==null) {
			dataPago.setPago(dataTest.getPais().getPago("VISA"));
		}
		PagoSteps pagoSteps = FactoryPagos.makePagoSteps(dataPago);
		pagoSteps.startPayment(executePayment);
	}
	
	public void checkPedidosManto(List<DataPedido> listPedidos) throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.CONSULTAR_BOLSA, 
			CheckPedido.CONSULTAR_PEDIDO);
		
		checkPedidosManto(listChecks, listPedidos);
	}
	
	public void checkPedidosManto(List<CheckPedido> listChecks, List<DataPedido> listPedidos) 
			throws Exception {
		DataCheckPedidos checksPedidos = DataCheckPedidos.newInstance(listPedidos, listChecks);
		PedidoNavigations.testPedidosEnManto(checksPedidos, app, driver);
	}
	
}
