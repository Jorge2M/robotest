package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpay1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpayAcceptSimulationSteps;
import com.mng.robotest.test.steps.shop.checkout.Dotpay.PageDotpayPaymentChannelSteps;

public class PagoDotpay extends PagoSteps {
	
	public PagoDotpay(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) throws Exception {
		super(dCtxSh, dataPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		PageDotpay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		
		if (execPay) {
			PageDotpay1rstSteps.clickToPay(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
			PageDotpayPaymentChannelSteps.selectPayment(1, driver);
			PageDotpayPaymentChannelSteps.inputNameAndConfirm("Jorge", "Muñoz", driver);
			PageDotpayAcceptSimulationSteps.clickRedButtonAceptar(driver);
			dataPedido.setCodtipopago("F");
		}
	}
}
