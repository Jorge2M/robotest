package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;

public class PagoContraReembolso extends PagoStpV {
	
	public PagoContraReembolso(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		if (execPay) {
			dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
			this.dCtxPago.getDataPedido().setCodtipopago("U");
		}
	}
}
