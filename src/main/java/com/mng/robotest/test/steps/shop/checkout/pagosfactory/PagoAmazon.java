package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.amazon.PageAmazonIdentSteps;

public class PagoAmazon extends PagoSteps {

	public PagoAmazon(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		PageAmazonIdentSteps.validateIsPage(dataTest.pais, channel, dataPago.getDataPedido(), driver);
		
		if (execPay) {
			throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
		}
	}
	
}
