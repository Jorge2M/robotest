package com.mng.robotest.domains.compra.tests;

import java.util.Arrays;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.beans.Pago;
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
		dataTest.setGenericChecksDisabled(Arrays.asList(GenericCheck.ANALITICA));
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
