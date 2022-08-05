package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.klarna.PageKlarnaSteps;


public class PagoKlarnaUK extends PagoSteps {
	
	private final PageKlarnaSteps pageKlarnaSteps;

	public PagoKlarnaUK(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
		pageKlarnaSteps = new PageKlarnaSteps(driver);
	}

	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		pageKlarnaSteps.checkIsPage(10);
		pageKlarnaSteps.clickComprar();
		
		//TODO estaría pendiente implementarlo, es bastante complejo
//		pageKlarnaSteps.checkModalInputPhoneNumber(5);
//		
//		if (execPay) {
//			pageKlarnaSteps.inputDataPhoneAndConfirm(dCtxSh.pais.getTelefono(), "123456");
//			this.dCtxPago.getDataPedido().setCodtipopago("K");
//		}
	}

}
