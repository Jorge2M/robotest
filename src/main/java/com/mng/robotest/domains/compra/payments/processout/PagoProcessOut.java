package com.mng.robotest.domains.compra.payments.processout;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.processout.steps.PageProcessOutInputTrjSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

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