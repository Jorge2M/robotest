package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageCheckoutWrapperStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.processout.PageProcessOutInputTrjStpV;

public class PagoProcessOut extends PagoStpV {

	public PagoProcessOut(DataCtxShop dCtxSh, DataCtxPago dataPago, WebDriver driver) {
		super(dCtxSh, dataPago, driver);
		super.isAvailableExecPay = true;
	}

	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		PageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh, driver);
		PagoNavigationsStpV.aceptarCompraDesdeMetodosPago(dCtxPago, dCtxSh.channel, driver);
		DataPedido dataPedido = dCtxPago.getDataPedido(); 
		PageProcessOutInputTrjStpV pageProcessOutStpV = new PageProcessOutInputTrjStpV(driver);
		pageProcessOutStpV.checkIsPage(dataPedido.getImporteTotal(), dCtxSh.pais.getCodigo_pais());

		if (execPay) {
			pageProcessOutStpV.inputTrjAndClickPay(dataPedido.getPago());
			dataPedido.setCodtipopago("F");
		}
	}
}
