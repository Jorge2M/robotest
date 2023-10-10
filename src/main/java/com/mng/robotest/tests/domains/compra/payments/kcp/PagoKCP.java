package com.mng.robotest.tests.domains.compra.payments.kcp;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.kcp.steps.PageKcpMainSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoKCP extends PagoSteps {
	
	public PagoKCP(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		var pageKcpMainSteps = new PageKcpMainSteps();
		pageKcpMainSteps.isPage(30);
		pageKcpMainSteps.isPresentTermAndConditions(30);
	}	
}