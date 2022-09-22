package com.mng.robotest.domains.compra.payments.amazon;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.domains.compra.payments.amazon.steps.PageAmazonIdentSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoAmazon extends PagoSteps {

	public PagoAmazon(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		new PageAmazonIdentSteps().validateIsPage(dataPago.getDataPedido());
		if (execPay) {
			throw new PaymethodWithoutTestPayImplementedException(MsgNoPayImplemented);
		}
	}
	
}
