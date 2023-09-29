package com.mng.robotest.tests.domains.compra.payments.multibanco;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;


public class PagoMultibanco extends PagoSteps {

	public PagoMultibanco( DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
