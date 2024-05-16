package com.mng.robotest.tests.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.FactoryPagos;
import com.mng.robotest.testslegacy.beans.TypePago;
import com.mng.robotest.testslegacy.datastored.DataCheckPedidos;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.testslegacy.steps.navigations.manto.PedidoNavigations;

import static com.mng.robotest.testslegacy.datastored.DataCheckPedidos.CheckPedido.*;

public class CompraSteps extends StepBase {

	public void startPayment(boolean executePayment) throws Exception {
		var dataPago = dataTest.getDataPago();
		if (dataPago.getPago()==null) {
			dataPago.setPago(dataTest.getPais().getPago("VISA"));
		}
		
		TypePago typePago = dataPago.getDataPedido().getPago().getTypePago();
		var pagoSteps = FactoryPagos.makePagoSteps(typePago, dataTest.getPais(), app);
		pagoSteps.startPayment(executePayment);
	}
	
	@Override
	public void checkPedidosManto(List<DataPedido> listPedidos) throws Exception {
		var listChecks = Arrays.asList(CONSULTAR_BOLSA,	CONSULTAR_PEDIDO);
		checkPedidosManto(listChecks, listPedidos);
	}
	
	@Override
	public void checkPedidosManto(List<CheckPedido> listChecks, List<DataPedido> listPedidos) 
			throws Exception {
		var checksPedidos = DataCheckPedidos.newInstance(listPedidos, listChecks);
		new PedidoNavigations().testPedidosShopEnManto(checksPedidos);
	}
	
}
