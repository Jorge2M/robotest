package com.mng.robotest.tests.domains.compra.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Com002 extends TestBase {

	private final DataPago dataPago;
	
	public Com002() throws Exception {
		var configCheckout = ConfigCheckout.config()
				.acceptCookies(false)
				.checkPagos()
				.checkMisCompras()
				.emaiExists().build();
		
		dataPago = getDataPago(configCheckout);
	}
	
	@Override
	public void execute() throws Exception {
		checkout();
	}
	
	private void checkout() throws Exception {
		new BuilderCheckout(dataPago)
				.pago(getPagoRealCard())
				.build()
				.checkout(From.PREHOME);
	}
	
	private Pago getPagoRealCard() {
		Pago pago = dataTest.getPais().getPago("VISA");
		pago.setNumtarj("5555444433331111");
		pago.setMescad("03");
		pago.setAnycad("2030");
		pago.setCvc("737");		
		return pago;
	}

}
