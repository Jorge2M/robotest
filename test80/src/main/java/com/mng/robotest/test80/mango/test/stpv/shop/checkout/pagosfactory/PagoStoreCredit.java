package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;


public class PagoStoreCredit extends PagoStpV {
	
	public PagoStoreCredit(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.getSecStoreCreditStpV().validateInitialStateOk(dCtxPago);
		pageCheckoutWrapperStpV.getSecStoreCreditStpV().selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE);
		pageCheckoutWrapperStpV.getSecStoreCreditStpV().selectSaldoEnCuentaBlock(dCtxSh.pais, dCtxPago, dCtxSh.appE);
		
		if (execPay) {
			dCtxPago = checkoutFlow.checkout(From.MetodosPago);
			this.dCtxPago.getDataPedido().setCodtipopago("U");
		}
	}
}
