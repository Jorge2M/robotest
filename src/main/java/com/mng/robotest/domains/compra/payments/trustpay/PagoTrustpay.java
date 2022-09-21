package com.mng.robotest.domains.compra.payments.trustpay;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.trustpay.steps.PageTrustPayResultSteps;
import com.mng.robotest.domains.compra.payments.trustpay.steps.PageTrustpaySelectBankSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoTrustpay extends PagoSteps {

	public PagoTrustpay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = this.dataPago.getDataPedido().getImporteTotal();
		
		PageTrustpaySelectBankSteps pageTrustpaySelectBankSteps = new PageTrustpaySelectBankSteps();
		pageTrustpaySelectBankSteps.checkIsPage(dataPago.getDataPedido().getPago().getNombre(channel, app), importeTotal);
		if (execPay) {
			pageTrustpaySelectBankSteps.selectTestBankAndPay(importeTotal);
			new PageTrustPayResultSteps().clickButtonContinue();
		}
	}	
}
