package com.mng.robotest.tests.domains.compra.payments.votftpv;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoTpvVotf extends PagoSteps {
	
	public PagoTpvVotf(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		String nombrePago = this.dataPago.getDataPedido().getPago().getNombre(channel, app);
		checkoutSteps.noClickIconoVotf(nombrePago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
