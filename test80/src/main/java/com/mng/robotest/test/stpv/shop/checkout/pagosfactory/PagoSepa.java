package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.sepa.PageSepa1rstStpV;
import com.mng.robotest.test.stpv.shop.checkout.sepa.PageSepaResultMobilStpV;

public class PagoSepa extends PagoStpV {
	
	public PagoSepa(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		Pago pago = this.dCtxPago.getDataPedido().getPago();
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(this.dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		String importeTotal = dCtxPago.getDataPedido().getImporteTotal();
		PageSepa1rstStpV.validateIsPage(pago.getNombre(dCtxSh.channel, dCtxSh.appE), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		
		if (execPay) {
			PageSepa1rstStpV.inputDataAndclickPay(pago.getNumtarj(), pago.getTitular(), importeTotal, dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
			if (dCtxSh.channel.isDevice()) {
				PageSepaResultMobilStpV.clickButtonPagar(driver);
			}
		}
	}
}
