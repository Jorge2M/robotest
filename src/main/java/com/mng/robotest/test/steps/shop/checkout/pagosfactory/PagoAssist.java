package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.assist.PageAssist1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.assist.PageAssistLastSteps;

public class PagoAssist extends PagoSteps {
	
	private final PageAssist1rstSteps pageAssist1rstSteps = new PageAssist1rstSteps();
	private final PageAssistLastSteps pageAssistLastSteps = new PageAssistLastSteps();
	
	public PagoAssist(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		pageAssist1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal(), dataTest.pais);
		
		if (execPay) {
			pageAssist1rstSteps.inputDataTarjAndPay(this.dataPago.getDataPedido().getPago());			
			pageAssistLastSteps.clickSubmit();
		}
	}
}
