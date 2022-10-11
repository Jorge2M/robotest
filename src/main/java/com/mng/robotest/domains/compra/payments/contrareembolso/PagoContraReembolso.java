package com.mng.robotest.domains.compra.payments.contrareembolso;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoContraReembolso extends PagoSteps {
	
	public PagoContraReembolso(DataPago dataPago) throws Exception {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			this.dataPago.getDataPedido().setCodtipopago("U");
		}
	}
}
