package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoTpvVotf extends PagoSteps {
	
	public PagoTpvVotf(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		String nombrePago = this.dataPago.getDataPedido().getPago().getNombre(channel, app);
		pageCheckoutWrapperSteps.noClickIconoVotf(nombrePago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
