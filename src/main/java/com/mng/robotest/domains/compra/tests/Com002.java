package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataCheckPedidos.CheckPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;


public class Com002 extends TestBase {

	private final DataCtxPago dataPago;
	
	public Com002() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkMisCompras()
				.emaiExists().build();
		
		dataPago = new DataCtxPago(dataTest, configCheckout);
	}
	
	@Override
	public void execute() throws Exception {
		checkout();
		checkPedido();
	}
	
	private void checkout() throws Exception {
		new BuilderCheckout(dataTest, dataPago, driver)
				.pago(getPagoRealCard())
				.build()
				.checkout(From.PREHOME);
	}
	
	private void checkPedido() throws Exception {
		List<CheckPedido> listChecks = Arrays.asList(
			CheckPedido.consultarBolsa, 
			CheckPedido.consultarPedido,
			CheckPedido.anular);
		
		CompraCommons.checkPedidosManto(listChecks, dataPago.getListPedidos(), app, driver);
	}
	
	private Pago getPagoRealCard() {
		Pago pago = dataTest.pais.getPago("VISA");
//		pago.setNumtarj("4940197085392651");
//		pago.setMescad("09");
//		pago.setAnycad("2022");
//		pago.setCvc("564");
		pago.setNumtarj("5555444433331111");
		pago.setMescad("03");
		pago.setAnycad("2030");
		pago.setCvc("737");		
		return pago;
	}

}
