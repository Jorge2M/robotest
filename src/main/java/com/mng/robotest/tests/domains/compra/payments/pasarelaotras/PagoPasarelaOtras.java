package com.mng.robotest.tests.domains.compra.payments.pasarelaotras;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.PaymethodWithoutTestPayImplementedException;
import com.mng.robotest.tests.domains.compra.payments.pasarelaotras.steps.PagePasarelaOtrasSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPasarelaOtras extends PagoSteps {
	
	public PagoPasarelaOtras(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(false);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		new PagePasarelaOtrasSteps().validateIsPage(dataPago.getDataPedido().getImporteTotal());
		if (execPay) {
			throw new PaymethodWithoutTestPayImplementedException(MSG_NO_PAY_IMPLEMENTED);
		}
	}	
}
