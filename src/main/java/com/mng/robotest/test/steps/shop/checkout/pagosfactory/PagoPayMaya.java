package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageIdentPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageInitPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageOtpPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageResultPaymayaSteps;

public class PagoPayMaya extends PagoSteps {
	
	private final PageIdentPaymayaSteps pageIdentPaymayaSteps = new PageIdentPaymayaSteps();
	private final PageInitPaymayaSteps pageInitPaymayaSteps = new PageInitPaymayaSteps();
	private final PageOtpPaymayaSteps pageOtpPaymayaSteps = new PageOtpPaymayaSteps();
	private final PageResultPaymayaSteps pageResultPaymayaSteps = new PageResultPaymayaSteps();
	
	public PagoPayMaya(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		if (!new UtilsMangoTest().isEntornoPRO()) {
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
