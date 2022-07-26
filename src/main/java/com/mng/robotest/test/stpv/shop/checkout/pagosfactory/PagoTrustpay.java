package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.trustpay.PageTrustPayResultStpV;
import com.mng.robotest.test.stpv.shop.checkout.trustpay.PageTrustpaySelectBankStpV;

public class PagoTrustpay extends PagoStpV {

	public PagoTrustpay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = this.dCtxPago.getDataPedido().getImporteTotal();
		PageTrustpaySelectBankStpV.validateIsPage(dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel, dCtxSh.appE), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		
		if (execPay) {
			PageTrustpaySelectBankStpV.selectTestBankAndPay(importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
			PageTrustPayResultStpV.clickButtonContinue(driver);
		}
	}	
}
