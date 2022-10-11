package com.mng.robotest.domains.compra.payments.votftpv;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoTpvVotf extends PagoSteps {
	
	public PagoTpvVotf(DataPago dataPago) throws Exception {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		String nombrePago = this.dataPago.getDataPedido().getPago().getNombre(channel, app);
		pageCheckoutWrapperSteps.noClickIconoVotf(nombrePago);
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		}
	}	
}
