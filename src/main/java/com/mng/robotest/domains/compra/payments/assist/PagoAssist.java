package com.mng.robotest.domains.compra.payments.assist;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.assist.steps.PageAssist1rstSteps;
import com.mng.robotest.domains.compra.payments.assist.steps.PageAssistLastSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoAssist extends PagoSteps {
	
	private final PageAssist1rstSteps pageAssist1rstSteps = new PageAssist1rstSteps();
	private final PageAssistLastSteps pageAssistLastSteps = new PageAssistLastSteps();
	
	public PagoAssist(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		pageAssist1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal());
		
		if (execPay) {
			pageAssist1rstSteps.inputDataTarjAndPay(this.dataPago.getDataPedido().getPago());			
			pageAssistLastSteps.clickSubmit();
		}
	}
}
