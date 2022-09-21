package com.mng.robotest.domains.compra.payments.kcp;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.kcp.steps.PageKcpMainSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoKCP extends PagoSteps {
	
	public PagoKCP(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		PageKcpMainSteps pageKcpMainSteps = new PageKcpMainSteps();
		pageKcpMainSteps.isPage(30);
		pageKcpMainSteps.isPresentTermAndConditions(30);
	}	
}
