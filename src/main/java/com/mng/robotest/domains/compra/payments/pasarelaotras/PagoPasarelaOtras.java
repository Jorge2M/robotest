package com.mng.robotest.domains.compra.payments.pasarelaotras;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.domains.compra.payments.pasarelaotras.steps.PagePasarelaOtrasSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoPasarelaOtras extends PagoSteps {
	
	public PagoPasarelaOtras(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		new PagePasarelaOtrasSteps().validateIsPage(dataPago.getDataPedido().getImporteTotal());
		if (execPay) {
			throw new PaymethodWithoutTestPayImplementedException(MsgNoPayImplemented);
		}
	}	
}