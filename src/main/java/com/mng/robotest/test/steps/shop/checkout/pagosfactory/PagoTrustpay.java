package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.trustpay.PageTrustPayResultSteps;
import com.mng.robotest.test.steps.shop.checkout.trustpay.PageTrustpaySelectBankSteps;

public class PagoTrustpay extends PagoSteps {

	public PagoTrustpay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = this.dataPago.getDataPedido().getImporteTotal();
		
		PageTrustpaySelectBankSteps pageTrustpaySelectBankSteps = new PageTrustpaySelectBankSteps();
		pageTrustpaySelectBankSteps.validateIsPage(dataPago.getDataPedido().getPago().getNombre(channel, app), importeTotal, dataTest.pais.getCodigo_pais());
		if (execPay) {
			pageTrustpaySelectBankSteps.selectTestBankAndPay(importeTotal, dataTest.pais.getCodigo_pais());
			new PageTrustPayResultSteps().clickButtonContinue();
		}
	}	
}
