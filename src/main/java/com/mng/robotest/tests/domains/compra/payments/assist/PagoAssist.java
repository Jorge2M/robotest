package com.mng.robotest.tests.domains.compra.payments.assist;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.assist.steps.PageAssist1rstSteps;
import com.mng.robotest.tests.domains.compra.payments.assist.steps.PageAssistLastSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoAssist extends PagoSteps {
	
	private final PageAssist1rstSteps pageAssist1rstSteps = new PageAssist1rstSteps();
	private final PageAssistLastSteps pageAssistLastSteps = new PageAssistLastSteps();
	
	public PagoAssist(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
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
