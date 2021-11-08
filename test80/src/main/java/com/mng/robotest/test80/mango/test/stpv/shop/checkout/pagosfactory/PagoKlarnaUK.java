package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.klarna.PageKlarnaStpV;


public class PagoKlarnaUK extends PagoStpV {
	
	private final PageKlarnaStpV pageKlarnaStpV;

	public PagoKlarnaUK(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
		pageKlarnaStpV = new PageKlarnaStpV(driver);
	}

	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		pageKlarnaStpV.checkIsPage(10);
		pageKlarnaStpV.clickComprar();
		pageKlarnaStpV.checkModalInputPhoneNumber(5);
		
		if (execPay) {
			pageKlarnaStpV.inputDataPhoneAndConfirm(dCtxSh.pais.getTelefono(), "123456");
			this.dCtxPago.getDataPedido().setCodtipopago("K");
		}
	}

}
