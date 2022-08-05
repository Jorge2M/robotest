package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropay1rstSteps;
import com.mng.robotest.test.steps.shop.checkout.giropay.PageGiropayInputBankSteps;

public class PagoGiropay extends PagoSteps {

	private final PageGiropay1rstSteps pageGiropay1rstSteps;
	private final PageGiropayInputBankSteps pageGiropayInputBankSteps = new PageGiropayInputBankSteps(); 
	
	public PagoGiropay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
		pageGiropay1rstSteps = new PageGiropay1rstSteps(dCtxSh.channel);
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.METODOSPAGO);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		pageGiropay1rstSteps.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());
		pageGiropay1rstSteps.clickButtonContinuePay();

		if (execPay) {
			dataPedido.setCodtipopago("F");
			Pago pago = dataPedido.getPago();
			String bankIdGirbopay = pago.getBankidgiropay();
			pageGiropayInputBankSteps.inputBankAndConfirm(bankIdGirbopay, dCtxSh.channel); 
		}
	}
}
