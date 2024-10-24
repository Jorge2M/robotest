package com.mng.robotest.tests.domains.setcookies.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Cok001 extends TestBase {

	public Cok001() throws Exception {
		var configCheckout = ConfigCheckout.config()
				.acceptCookies(false)
				.checkPagos()
				.checkMisCompras()
				.emaiExists().build();
		
		dataTest.setDataPago(configCheckout);
	}
	
	@Override
	public void execute() throws Exception {
		checkout();
	}
	
	private void checkout() throws Exception {
		new BuilderCheckout(dataTest.getDataPago())
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
