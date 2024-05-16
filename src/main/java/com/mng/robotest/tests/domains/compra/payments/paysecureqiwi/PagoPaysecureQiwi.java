package com.mng.robotest.tests.domains.compra.payments.paysecureqiwi;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.pageobjects.PagePaysecureConfirm;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.steps.PagePaysecureQiwi1rstSteps;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.steps.PageQiwiConfirmSteps;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.steps.PageQiwiInputTlfnSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoPaysecureQiwi extends PagoSteps {

	public PagoPaysecureQiwi() {
		super();
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.selectDeliveryAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		
		var pgPaysecureQiwi1rstSteps = new PagePaysecureQiwi1rstSteps();
		pgPaysecureQiwi1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal());
		pgPaysecureQiwi1rstSteps.clickIconPasarelaQiwi();
		
		if (execPay) {
			String tlfQiwi = dataPago.getDataPedido().getPago().getTelefqiwi();
			var pgQiwiInputTlfnSteps = new PageQiwiInputTlfnSteps();
			pgQiwiInputTlfnSteps.inputTelefono(tlfQiwi);
			pgQiwiInputTlfnSteps.clickConfirmarButton();
			if (new PagePaysecureConfirm().isPage()) {
				new PageQiwiConfirmSteps().selectConfirmButton();
			}
		}
	}	
}
