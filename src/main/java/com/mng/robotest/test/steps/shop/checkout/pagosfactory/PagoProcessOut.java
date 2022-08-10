package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.processout.PageProcessOutInputTrjSteps;


public class PagoProcessOut extends PagoSteps {

	public PagoProcessOut(DataCtxShop dCtxSh, DataCtxPago dataPago) throws Exception {
		super(dCtxSh, dataPago);
		super.isAvailableExecPay = true;
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		PageProcessOutInputTrjSteps pageProcessOutSteps = new PageProcessOutInputTrjSteps(driver);
		pageProcessOutSteps.checkIsPage(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());

		if (execPay) {
			pageProcessOutSteps.inputTrjAndClickPay(dataPedido.getPago());
			dataPedido.setCodtipopago("F");
		}
	}
}
