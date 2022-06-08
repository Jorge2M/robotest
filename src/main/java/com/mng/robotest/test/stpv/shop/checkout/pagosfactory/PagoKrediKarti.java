package com.mng.robotest.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.stpv.shop.checkout.SecKrediKartiStpV;

public class PagoKrediKarti extends PagoStpV {
	
	public PagoKrediKarti(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		super(dCtxSh, dCtxPago, driver);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		pageCheckoutWrapperStpV.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		
		SecKrediKartiStpV secKrediKartiStpV = pageCheckoutWrapperStpV.getSecKrediKartiStpV();
		secKrediKartiStpV.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		secKrediKartiStpV.clickOpcionPagoAPlazo(1);
		
		if (execPay) {
			dataPedido.setCodtipopago("O");
			this.dCtxPago.getFTCkout().trjGuardada = false;
			pageCheckoutWrapperStpV.inputDataTrjAndConfirmPago(dCtxPago);
		}
	}	
}