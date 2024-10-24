package com.mng.robotest.tests.domains.compra.payments.paytrail;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.paytrail.steps.PagePaytrail1rstSteps;
import com.mng.robotest.tests.domains.compra.payments.paytrail.steps.PagePaytrailEpaymentSteps;
import com.mng.robotest.tests.domains.compra.payments.paytrail.steps.PagePaytrailIdConfirmSteps;
import com.mng.robotest.tests.domains.compra.payments.paytrail.steps.PagePaytrailResultadoOkSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPaytrail extends PagoSteps {
	
	public PagoPaytrail() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		
		var pagePaytrail1rstSteps = new PagePaytrail1rstSteps();
		pagePaytrail1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal());
		pagePaytrail1rstSteps.selectBancoAndContinue();
		if (execPay) {
			this.dataPago.getDataPedido().setCodtipopago("F");
			String codigoPais = this.dataPago.getDataPedido().getCodigoPais();
			new PagePaytrailEpaymentSteps().clickCodeCardOK(dataPago.getDataPedido().getImporteTotal(), codigoPais);
			new PagePaytrailIdConfirmSteps().inputIDAndClickConfirmar("V2360A71", dataPago.getDataPedido().getImporteTotal(), codigoPais);
			new PagePaytrailResultadoOkSteps().clickVolverAMangoButton();
		}
	}
}
