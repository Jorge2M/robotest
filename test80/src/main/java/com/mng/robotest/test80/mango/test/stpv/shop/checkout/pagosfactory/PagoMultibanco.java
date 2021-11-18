package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibanco1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.multibanco.PageMultibancoEnProgresoStpv;

public class PagoMultibanco extends PagoStpV {

	public PagoMultibanco(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		DataPedido dataPedido = this.dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		PageMultibanco1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dataPedido.getEmailCheckout(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		PageMultibanco1rstStpV.continueToNextPage(dCtxSh.channel, driver);
		
		if (execPay) {
			PageMultibancoEnProgresoStpv.clickButtonNextStep(driver);
		}
	}	
}
