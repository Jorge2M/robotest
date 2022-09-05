package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropay1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropayInputBankSteps;

public class PagoGiropay extends PagoSteps {

	private final PageGiropay1rstSteps pageGiropay1rstSteps;
	private final PageGiropayInputBankSteps pageGiropayInputBankSteps = new PageGiropayInputBankSteps(); 
	
	public PagoGiropay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
		pageGiropay1rstSteps = new PageGiropay1rstSteps(channel);
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageGiropay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataTest.pais.getCodigo_pais());
		pageGiropay1rstSteps.clickButtonContinuePay();

		if (execPay) {
			dataPedido.setCodtipopago("F");
			Pago pago = dataPedido.getPago();
			String bankIdGirbopay = pago.getBankidgiropay();
			pageGiropayInputBankSteps.inputBankAndConfirm(bankIdGirbopay, channel); 
		}
	}
}
