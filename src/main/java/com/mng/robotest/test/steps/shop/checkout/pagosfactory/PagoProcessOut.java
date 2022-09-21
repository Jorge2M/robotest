package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.processout.PageProcessOutInputTrjSteps;

public class PagoProcessOut extends PagoSteps {

	public PagoProcessOut(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		PageProcessOutInputTrjSteps pageProcessOutSteps = new PageProcessOutInputTrjSteps();
		pageProcessOutSteps.checkIsPage(dataPedido.getImporteTotal());

		if (execPay) {
			pageProcessOutSteps.inputTrjAndClickPay(dataPedido.getPago());
			dataPedido.setCodtipopago("F");
		}
	}
}
