package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.assist.PageAssist1rstStpV;
import com.mng.robotest.test.stpv.shop.checkout.assist.PageAssistLastStpV;

public class PagoAssist extends PagoStpV {
	
	public PagoAssist(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		PageAssist1rstStpV.validateIsPage(dCtxPago.getDataPedido().getImporteTotal(), dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
		
		if (execPay) {
			PageAssist1rstStpV.inputDataTarjAndPay(this.dCtxPago.getDataPedido().getPago(), dCtxSh.channel, driver);			
			PageAssistLastStpV.clickSubmit(driver);
		}
	}
}
