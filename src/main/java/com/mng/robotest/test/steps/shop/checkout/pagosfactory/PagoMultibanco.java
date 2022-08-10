package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.multibanco.PageMultibanco1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.multibanco.PageMultibancoEnProgresoSteps;


public class PagoMultibanco extends PagoSteps {

	PageMultibanco1rstSteps pageMultibanco1rstSteps = new PageMultibanco1rstSteps();
	
	public PagoMultibanco(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		pageMultibanco1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout(), dCtxSh.pais.getCodigo_pais());
		pageMultibanco1rstSteps.continueToNextPage();
		
		if (execPay) {
			new PageMultibancoEnProgresoSteps().clickButtonNextStep();
		}
	}	
}
