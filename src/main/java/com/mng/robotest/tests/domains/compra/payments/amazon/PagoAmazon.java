package com.mng.robotest.tests.domains.compra.payments.amazon;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.tests.domains.compra.payments.amazon.steps.PageAmazonIdentSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoAmazon extends PagoSteps {

	public PagoAmazon() {
		super();
		super.setAvaliableExecPay(false);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		new PageAmazonIdentSteps().validateIsPage(dataPago.getDataPedido());
		if (execPay) {
			throw new PaymethodWithoutTestPayImplementedException(MSG_NO_PAY_IMPLEMENTED);
		}
	}
	
}
