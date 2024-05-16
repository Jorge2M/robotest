package com.mng.robotest.tests.domains.compra.payments.multibanco;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoMultibanco extends PagoSteps {

	public PagoMultibanco() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		if (execPay) {
			checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
