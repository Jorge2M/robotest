package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.kcp.PageKcpMainSteps;


public class PagoKCP extends PagoSteps {
	
	public PagoKCP(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		PageKcpMainSteps pageKcpMainSteps = new PageKcpMainSteps();
		pageKcpMainSteps.isPage(30);
		pageKcpMainSteps.isPresentTermAndConditions(30);
	}	
}
