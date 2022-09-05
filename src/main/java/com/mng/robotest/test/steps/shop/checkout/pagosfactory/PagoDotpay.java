package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpay1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpayAcceptSimulationSteps;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpayPaymentChannelSteps;

public class PagoDotpay extends PagoSteps {
	
	private final PageDotpay1rstSteps pageDotpay1rstSteps = new PageDotpay1rstSteps();
	private final PageDotpayPaymentChannelSteps pageDotpayPaymentChannelSteps = new PageDotpayPaymentChannelSteps();
	private final PageDotpayAcceptSimulationSteps pageDotpayAcceptSimulationSteps = new PageDotpayAcceptSimulationSteps();
	
	public PagoDotpay(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dataPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(channel, app);
		pageDotpay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataTest.pais.getCodigo_pais());
		
		if (execPay) {
			pageDotpay1rstSteps.clickToPay(dataPedido.getImporteTotal(), dataTest.pais.getCodigo_pais());
			pageDotpayPaymentChannelSteps.selectPayment(1);
			pageDotpayPaymentChannelSteps.inputNameAndConfirm("Jorge", "Muñoz");
			pageDotpayAcceptSimulationSteps.clickRedButtonAceptar();
			dataPedido.setCodtipopago("F");
		}
	}
}
