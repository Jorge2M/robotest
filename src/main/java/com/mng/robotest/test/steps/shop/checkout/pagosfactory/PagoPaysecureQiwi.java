package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureConfirm;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PageQiwiConfirmSteps;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PageQiwiInputTlfnSteps;

public class PagoPaysecureQiwi extends PagoSteps {

	public PagoPaysecureQiwi(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PagePaysecureQiwi1rstSteps pagePaysecureQiwi1rstSteps = new PagePaysecureQiwi1rstSteps();
		pagePaysecureQiwi1rstSteps.validateIsPage(dataPago.getDataPedido().getImporteTotal(), dataTest.pais.getCodigo_pais(), channel);
		pagePaysecureQiwi1rstSteps.clickIconPasarelaQiwi(channel);
		
		if (execPay) {
			String tlfQiwi = dataPago.getDataPedido().getPago().getTelefqiwi();
			PageQiwiInputTlfnSteps pageQiwiInputTlfnSteps = new PageQiwiInputTlfnSteps();
			pageQiwiInputTlfnSteps.inputTelefono(tlfQiwi);
			pageQiwiInputTlfnSteps.clickConfirmarButton();
			if (new PagePaysecureConfirm().isPage()) {
				new PageQiwiConfirmSteps().selectConfirmButton();
			}
		}
	}	
}
