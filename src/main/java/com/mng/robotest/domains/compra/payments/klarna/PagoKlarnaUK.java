package com.mng.robotest.domains.compra.payments.klarna;

import com.mng.robotest.domains.compra.payments.PagoSteps;
import com.mng.robotest.domains.compra.payments.klarna.steps.PageKlarnaSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;

public class PagoKlarnaUK extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps = new PageKlarnaSteps();

	public PagoKlarnaUK(DataPago dataPago) throws Exception {
		super(dataPago);
		super.setAvaliableExecPay(true);
	}

	@Override
	public void startPayment(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago);
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
