package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.kcp.PageKcpMainSteps;

public class PagoKCP extends PagoSteps {
	
	public PagoKCP(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		PageKcpMainSteps pageKcpMainSteps = new PageKcpMainSteps();
		pageKcpMainSteps.isPage(30);
		pageKcpMainSteps.isPresentTermAndConditions(30);
	}	
}
