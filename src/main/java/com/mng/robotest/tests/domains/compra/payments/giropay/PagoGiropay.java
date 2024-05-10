package com.mng.robotest.tests.domains.compra.payments.giropay;

import com.mng.robotest.tests.domains.compra.payments.PagoSteps;
import com.mng.robotest.tests.domains.compra.payments.giropay.steps.PageGiropay1rstSteps;
import com.mng.robotest.tests.domains.compra.payments.giropay.steps.PageGiropayInputBankSteps;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.datastored.DataPedido;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class PagoGiropay extends PagoSteps {

	private final PageGiropay1rstSteps pageGiropay1rstSteps = new PageGiropay1rstSteps();
	private final PageGiropayInputBankSteps pageGiropayInputBankSteps = new PageGiropayInputBankSteps(); 
	
	public PagoGiropay() {
		super();
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		checkoutSteps.fluxSelectEnvioAndClickPaymentMethod();
		checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageGiropay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal());
		pageGiropay1rstSteps.clickButtonContinuePay();

		if (execPay) {
			dataPedido.setCodtipopago("F");
			Pago pago = dataPedido.getPago();
			String bankIdGirbopay = pago.getBankidgiropay();
			pageGiropayInputBankSteps.inputBankAndConfirm(bankIdGirbopay); 
		}
	}
}
