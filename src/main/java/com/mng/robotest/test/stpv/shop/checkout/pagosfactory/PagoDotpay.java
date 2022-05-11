package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.checkout.Dotpay.PageDotpay1rstStpV;
import com.mng.robotest.test.stpv.shop.checkout.Dotpay.PageDotpayAcceptSimulationStpV;
import com.mng.robotest.test.stpv.shop.checkout.Dotpay.PageDotpayPaymentChannelStpV;

public class PagoDotpay extends PagoStpV {
	
	public PagoDotpay(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) throws Exception {
		super(dCtxSh, dataPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		PageDotpay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		
		if (execPay) {
			PageDotpay1rstStpV.clickToPay(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
			PageDotpayPaymentChannelStpV.selectPayment(1, driver);
			PageDotpayPaymentChannelStpV.inputNameAndConfirm("Jorge", "Muñoz", driver);
			PageDotpayAcceptSimulationStpV.clickRedButtonAceptar(driver);
			dataPedido.setCodtipopago("F");
		}
	}
}
