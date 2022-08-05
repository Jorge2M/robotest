package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.trustpay.PageTrustPayResultSteps;
import com.mng.robotest.test.steps.shop.checkout.trustpay.PageTrustpaySelectBankSteps;

public class PagoTrustpay extends PagoSteps {

	public PagoTrustpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
		PageTrustpaySelectBankSteps.validateIsPage(dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel, dCtxSh.appE), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		
		if (execPay) {
			PageTrustpaySelectBankSteps.selectTestBankAndPay(importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
			PageTrustPayResultSteps.clickButtonContinue(driver);
		}
	}	
}
