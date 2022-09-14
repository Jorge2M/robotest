package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.pasarelaotras.PagePasarelaOtrasSteps;

public class PagoPasarelaOtras extends PagoSteps {
	
	public PagoPasarelaOtras(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		PagePasarelaOtrasSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal(), dataTest.pais, channel, app, driver);
		
		if (execPay) {
			throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
		}
	}	
}
