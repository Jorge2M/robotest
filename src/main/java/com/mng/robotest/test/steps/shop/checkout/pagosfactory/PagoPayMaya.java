package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageIdentPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageInitPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageOtpPaymayaSteps;
import com.mng.robotest.test.steps.shop.checkout.paymaya.PageResultPaymayaSteps;

public class PagoPayMaya extends PagoSteps {
	
	public PagoPayMaya(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PageIdentPaymayaSteps pageIdentPaymayaSteps = new PageIdentPaymayaSteps(driver);
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			PageInitPaymayaSteps pageInitPaymayaSteps = new PageInitPaymayaSteps(driver);
			pageInitPaymayaSteps.checkPage();
			pageIdentPaymayaSteps = pageInitPaymayaSteps.clickPaymayaButton();
		} else {
			pageIdentPaymayaSteps.checkPage();
		}
		
		if (execPay) {
			Pago pago = dCtxPago.getDataPedido().getPago();
			PageOtpPaymayaSteps pageOtpPaymayaSteps = 
				pageIdentPaymayaSteps.login(pago.getUsrpaymaya(), pago.getPasswordpaymaya());
			PageResultPaymayaSteps pageResultPaymanaSteps = 
				pageOtpPaymayaSteps.proceed(pago.getOtpdpaymaya());
			pageResultPaymanaSteps.confirmPayment();
			
			dCtxPago.getDataPedido().setCodtipopago("F");
		}
	}	
}
