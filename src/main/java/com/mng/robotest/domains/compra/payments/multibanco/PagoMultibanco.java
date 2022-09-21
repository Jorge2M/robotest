package com.mng.robotest.domains.compra.payments.multibanco;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.multibanco.steps.PageMultibanco1rstSteps;
import com.mng.robotest.domains.compra.payments.multibanco.steps.PageMultibancoEnProgresoSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoMultibanco extends PagoSteps {

	PageMultibanco1rstSteps pageMultibanco1rstSteps = new PageMultibanco1rstSteps();
	
	public PagoMultibanco( DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = this.dataPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageMultibanco1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout());
		pageMultibanco1rstSteps.continueToNextPage();
		
		if (execPay) {
			new PageMultibancoEnProgresoSteps().clickButtonNextStep();
		}
	}	
}
