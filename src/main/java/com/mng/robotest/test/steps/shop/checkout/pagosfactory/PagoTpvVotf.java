package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;


public class PagoTpvVotf extends PagoSteps {
	
	public PagoTpvVotf(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		String nombrePago = this.dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		pageCheckoutWrapperSteps.noClickIconoVotf(nombrePago);
		if (execPay) {
			dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
