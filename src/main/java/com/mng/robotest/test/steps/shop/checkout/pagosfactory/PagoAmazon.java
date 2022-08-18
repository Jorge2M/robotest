package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.amazon.PageAmazonIdentSteps;


public class PagoAmazon extends PagoSteps {

	public PagoAmazon(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		PageAmazonIdentSteps.validateIsPage(dCtxSh.pais, channel, dCtxPago.getDataPedido(), driver);
		
		if (execPay) {
			throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
		}
	}
	
}
