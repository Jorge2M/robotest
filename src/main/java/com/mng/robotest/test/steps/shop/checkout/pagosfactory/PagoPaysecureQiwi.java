package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.pageobject.shop.checkout.paysecureqiwi.PagePaysecureConfirm;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PagePaysecureQiwi1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PageQiwiConfirmSteps;
import com.mng.robotest.test.steps.shop.checkout.paysecureqiwi.PageQiwiInputTlfnSteps;


public class PagoPaysecureQiwi extends PagoSteps {

	public PagoPaysecureQiwi(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		
		PagePaysecureQiwi1rstSteps pagePaysecureQiwi1rstSteps = new PagePaysecureQiwi1rstSteps(dCtxSh.appE, driver);
		pagePaysecureQiwi1rstSteps.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel);
		pagePaysecureQiwi1rstSteps.clickIconPasarelaQiwi(dCtxSh.channel);
		
		if (execPay) {
			String tlfQiwi = dCtxPago.getDataPedido().getPago().getTelefqiwi();
			PageQiwiInputTlfnSteps.inputTelefono(tlfQiwi, driver);
			PageQiwiInputTlfnSteps.clickConfirmarButton(driver);
			if (PagePaysecureConfirm.isPage(driver)) {
				PageQiwiConfirmSteps.selectConfirmButton(driver);
			}
		}
	}	
}
