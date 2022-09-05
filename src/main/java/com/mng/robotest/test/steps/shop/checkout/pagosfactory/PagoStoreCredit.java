package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoStoreCredit extends PagoSteps {
	
	public PagoStoreCredit(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().validateInitialStateOk(dataPago);
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().selectSaldoEnCuentaBlock(dataTest.pais, dataPago);
		pageCheckoutWrapperSteps.getSecStoreCreditSteps().selectSaldoEnCuentaBlock(dataTest.pais, dataPago);
		
		if (execPay) {
			dataPago = checkoutFlow.checkout(From.METODOSPAGO);
			this.dataPago.getDataPedido().setCodtipopago("U");
		}
	}
}
