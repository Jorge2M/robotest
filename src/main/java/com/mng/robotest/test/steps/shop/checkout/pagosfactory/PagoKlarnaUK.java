package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.klarna.PageKlarnaSteps;

public class PagoKlarnaUK extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps = new PageKlarnaSteps();

	public PagoKlarnaUK(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		dataPago = checkoutFlow.checkout(From.METODOSPAGO);
		pageKlarnaSteps.checkIsPage(10);
		pageKlarnaSteps.clickComprar();
		
		//TODO estaría pendiente implementarlo, es bastante complejo
//		pageKlarnaSteps.checkModalInputPhoneNumber(5);
//		
//		if (execPay) {
//			pageKlarnaSteps.inputDataPhoneAndConfirm(dataTest.pais.getTelefono(), "123456");
//			this.dataPago.getDataPedido().setCodtipopago("K");
//		}
	}

}
