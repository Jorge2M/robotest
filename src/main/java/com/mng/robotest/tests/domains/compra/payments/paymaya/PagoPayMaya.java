package com.mng.robotest.tests.domains.compra.payments.paymaya;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.paymaya.steps.PageIdentPaymayaSteps;
import com.mng.robotest.tests.domains.compra.payments.paymaya.steps.PageInitPaymayaSteps;
import com.mng.robotest.tests.domains.compra.payments.paymaya.steps.PageOtpPaymayaSteps;
import com.mng.robotest.tests.domains.compra.payments.paymaya.steps.PageResultPaymayaSteps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPayMaya extends PagoSteps {
	
	private final PageIdentPaymayaSteps pageIdentPaymayaSteps = new PageIdentPaymayaSteps();
	private final PageInitPaymayaSteps pageInitPaymayaSteps = new PageInitPaymayaSteps();
	private final PageOtpPaymayaSteps pageOtpPaymayaSteps = new PageOtpPaymayaSteps();
	private final PageResultPaymayaSteps pageResultPaymayaSteps = new PageResultPaymayaSteps();
	
	public PagoPayMaya() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		
		if (!isPRO()) {
			pageInitPaymayaSteps.checkPage();
			pageInitPaymayaSteps.clickPaymayaButton();
		} else {
			pageIdentPaymayaSteps.checkPage();
		}
		
		if (execPay) {
			Pago pago = dataPago.getDataPedido().getPago();
			pageIdentPaymayaSteps.login(pago.getUsrpaymaya(), pago.getPasswordpaymaya());
			pageOtpPaymayaSteps.proceed(pago.getOtpdpaymaya());
			pageResultPaymayaSteps.confirmPayment();
			
			dataPago.getDataPedido().setCodtipopago("F");
		}
	}	
}
