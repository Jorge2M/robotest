package com.mng.robotest.tests.domains.compra.payments.trustpay;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.trustpay.steps.PageTrustPayResultSteps;
import com.mng.robotest.tests.domains.compra.payments.trustpay.steps.PageTrustpaySelectBankSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoTrustpay extends PagoSteps {

	public PagoTrustpay(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = this.dataPago.getDataPedido().getImporteTotal();
		
		var pageTrustpaySelectBankSteps = new PageTrustpaySelectBankSteps();
		pageTrustpaySelectBankSteps.checkIsPage(dataPago.getDataPedido().getPago().getNombre(channel, app), importeTotal);
		if (execPay) {
			pageTrustpaySelectBankSteps.selectTestBankAndPay(importeTotal);
			new PageTrustPayResultSteps().clickButtonContinue();
		}
	}	
}
