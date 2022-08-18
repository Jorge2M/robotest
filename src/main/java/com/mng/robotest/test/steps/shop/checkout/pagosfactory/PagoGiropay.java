package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropay1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropayInputBankSteps;


public class PagoGiropay extends PagoSteps {

	private final PageGiropay1rstSteps pageGiropay1rstSteps;
	private final PageGiropayInputBankSteps pageGiropayInputBankSteps = new PageGiropayInputBankSteps(); 
	
	public PagoGiropay(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
		pageGiropay1rstSteps = new PageGiropay1rstSteps(channel);
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh.pais);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageGiropay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
		pageGiropay1rstSteps.clickButtonContinuePay();

		if (execPay) {
			dataPedido.setCodtipopago("F");
			Pago pago = dataPedido.getPago();
			String bankIdGirbopay = pago.getBankidgiropay();
			pageGiropayInputBankSteps.inputBankAndConfirm(bankIdGirbopay, channel); 
		}
	}
}
