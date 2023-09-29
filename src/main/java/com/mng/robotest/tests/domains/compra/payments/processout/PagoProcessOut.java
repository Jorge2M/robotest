package com.mng.robotest.tests.domains.compra.payments.processout;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.processout.steps.PageProcessOutInputTrjSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoProcessOut extends PagoSteps {

	public PagoProcessOut(DataPago dataPago) {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		var pageProcessOutSteps = new PageProcessOutInputTrjSteps();
		pageProcessOutSteps.checkIsPage(dataPedido.getImporteTotal());

		if (execPay) {
			pageProcessOutSteps.inputTrjAndClickPay(dataPedido.getPago());
			dataPedido.setCodtipopago("F");
		}
	}
}
