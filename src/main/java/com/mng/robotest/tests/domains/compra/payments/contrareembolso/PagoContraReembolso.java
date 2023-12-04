package com.mng.robotest.tests.domains.compra.payments.contrareembolso;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoContraReembolso extends PagoSteps {
	
	public PagoContraReembolso(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			this.dataPago.getDataPedido().setCodtipopago("U");
		}
	}
}
