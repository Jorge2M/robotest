package com.mng.robotest.domains.compra.payments.paysecureqiwi;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureConfirm;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.steps.PagePaysecureQiwi1rstSteps;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.steps.PageQiwiConfirmSteps;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.steps.PageQiwiInputTlfnSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoPaysecureQiwi extends PagoSteps {

	public PagoPaysecureQiwi(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		var pagePaysecureQiwi1rstSteps = new PagePaysecureQiwi1rstSteps();
		pagePaysecureQiwi1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal());
		pagePaysecureQiwi1rstSteps.clickIconPasarelaQiwi();
		
		if (execPay) {
			String tlfQiwi = dataPago.getDataPedido().getPago().getTelefqiwi();
			var pageQiwiInputTlfnSteps = new PageQiwiInputTlfnSteps();
			pageQiwiInputTlfnSteps.inputTelefono(tlfQiwi);
			pageQiwiInputTlfnSteps.clickConfirmarButton();
			if (new PagePaysecureConfirm().isPage()) {
				new PageQiwiConfirmSteps().selectConfirmButton();
			}
		}
	}	
}
