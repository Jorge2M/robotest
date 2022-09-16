package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class Com002 extends TestBase {

	private final DataPago dataPago;
	
	public Com002() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.acceptCookies(false)
				.checkPagos()
				.checkMisCompras()
				.emaiExists().build();
		
		dataPago = getDataPago(configCheckout);
		dataTest.genericChecksDisabled = Arrays.asList(GenericCheck.Analitica);
	}
	
	@Override
	public void execute() throws Exception {
		checkout();
		checkPedido();
	}
	
	private void checkout() throws Exception {
		new BuilderCheckout(dataPago)
				.pago(getPagoRealCard())
				.build()
				.checkout(From.PREHOME);
	}
	
	private void checkPedido() throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.CONSULTAR_BOLSA, 
			CheckPedido.CONSULTAR_PEDIDO,
			CheckPedido.ANULAR);
		
		new CompraSteps().checkPedidosManto(listChecks, dataPago.getListPedidos());
	}
	
	private Pago getPagoRealCard() {
		Pago pago = dataTest.pais.getPago("VISA");
//		pago.setNumtarj("4940197085392651");
//		pago.setMescad("09");
//		pago.setAnycad("2022");
//		pago.setCvc("XXX");
		pago.setNumtarj("5555444433331111");
		pago.setMescad("03");
		pago.setAnycad("2030");
		pago.setCvc("737");		
		return pago;
	}

}
