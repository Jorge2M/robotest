package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.beans.Pago;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropay1rstStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay.PageGiropayInputBankStpV;

public class PagoGiropay extends PagoStpV {

	public PagoGiropay(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		dCtxPago = checkoutFlow.checkout(From.MetodosPago);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		String nombrePago = dataPedido.getPago().getNombre(dCtxSh.channel, dCtxSh.appE);
		PageGiropay1rstStpV.validateIsPage(nombrePago, dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais(), dCtxSh.channel, driver);
		PageGiropay1rstStpV.clickButtonContinuePay(dCtxSh.channel, driver);

		if (execPay) {
			dataPedido.setCodtipopago("F");
			Pago pago = dataPedido.getPago();
			String bankIdGirbopay = pago.getBankidgiropay();
			PageGiropayInputBankStpV.inputBankAndConfirm(bankIdGirbopay, dCtxSh.channel, driver); 
		}
	}
}
