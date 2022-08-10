package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.pasarelaotras.PagePasarelaOtrasSteps;


public class PagoPasarelaOtras extends PagoSteps {
	
	public PagoPasarelaOtras(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = false;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		PagePasarelaOtrasSteps.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
		
		if (execPay) {
			throw new PaymethodWithoutTestPayImplemented(MsgNoPayImplemented);
		}
	}	
}
