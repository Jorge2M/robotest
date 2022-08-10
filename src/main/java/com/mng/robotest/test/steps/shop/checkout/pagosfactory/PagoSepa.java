package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.sepa.PageSepa1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.sepa.PageSepaResultMobilSteps;


public class PagoSepa extends PagoSteps {
	
	PageSepa1rstSteps pageSepa1rstSteps = new PageSepa1rstSteps();
	
	public PagoSepa(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		Pago pago = this.dCtxPago.getDataPedido().getPago();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		String importeTotal = dCtxPago.getDataPedido().getImporteTotal();
		pageSepa1rstSteps.validateIsPage(pago.getNombre(dCtxSh.channel, dCtxSh.appE), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel);
		
		if (execPay) {
			pageSepa1rstSteps.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel);
			if (dCtxSh.channel.isDevice()) {
				new PageSepaResultMobilSteps().clickButtonPagar();
			}
		}
	}
}
