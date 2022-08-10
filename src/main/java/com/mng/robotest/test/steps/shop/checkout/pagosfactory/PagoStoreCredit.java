package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;


public class PagoStoreCredit extends PagoSteps {
	
	public PagoStoreCredit(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().validateInitialStateOk(dCtxPago);
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE);
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE);
		
		if (execPay) {
			dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
			this.dCtxPago.getDataPedido().setCodtipopago("U");
		}
	}
}
