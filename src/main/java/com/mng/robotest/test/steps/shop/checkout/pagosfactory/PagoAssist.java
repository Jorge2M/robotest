package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.assist.PageAssist1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.assist.PageAssistLastSteps;


public class PagoAssist extends PagoSteps {
	
	private final PageAssist1rstSteps pageAssist1rstSteps = new PageAssist1rstSteps();
	private final PageAssistLastSteps pageAssistLastSteps = new PageAssistLastSteps();
	
	public PagoAssist(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		pageAssist1rstSteps.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais);
		
		if (execPay) {
			pageAssist1rstSteps.inputDataTarjAndPay(this.dCtxPago.getDataPedido().getPago());			
			pageAssistLastSteps.clickSubmit();
		}
	}
}
