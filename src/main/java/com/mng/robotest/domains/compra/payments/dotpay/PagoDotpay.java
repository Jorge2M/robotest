package com.mng.robotest.domains.compra.payments.dotpay;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.dotpay.steps.PageDotpay1rstSteps;
import com.mng.robotest.domains.compra.payments.dotpay.steps.PageDotpayAcceptSimulationSteps;
import com.mng.robotest.domains.compra.payments.dotpay.steps.PageDotpayPaymentChannelSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoDotpay extends PagoSteps {
	
	private final PageDotpay1rstSteps pageDotpay1rstSteps = new PageDotpay1rstSteps();
	private final PageDotpayPaymentChannelSteps pageDotpayPaymentChannelSteps = new PageDotpayPaymentChannelSteps();
	private final PageDotpayAcceptSimulationSteps pageDotpayAcceptSimulationSteps = new PageDotpayAcceptSimulationSteps();
	
	public PagoDotpay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}
	
	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageDotpay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataTest.getCodigoPais());
		
		if (execPay) {
			pageDotpay1rstSteps.clickToPay(dataPedido.getImporteTotal(), dataTest.getCodigoPais());
			pageDotpayPaymentChannelSteps.selectPayment(1);
			pageDotpayPaymentChannelSteps.inputNameAndConfirm("Jorge", "Muñoz");
			pageDotpayAcceptSimulationSteps.clickRedButtonAceptar();
			dataPedido.setCodtipopago("F");
		}
	}
}
