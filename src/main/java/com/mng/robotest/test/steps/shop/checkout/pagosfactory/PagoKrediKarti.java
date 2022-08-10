package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.checkout.SecKrediKartiSteps;


public class PagoKrediKarti extends PagoSteps {
	
	public PagoKrediKarti(DataCtxShop dCtxSh, DataCtxPago dCtxPago) throws Exception {
		super(dCtxSh, dCtxPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void testPagoFromCheckout(boolean execPay) throws Exception {
		DataPedido dataPedido = dCtxPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dCtxPago, dCtxSh);
		
		SecKrediKartiSteps secKrediKartiSteps = pageCheckoutWrapperSteps.getSecKrediKartiSteps();
		secKrediKartiSteps.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		secKrediKartiSteps.clickOpcionPagoAPlazo(1);
		
		if (execPay) {
			dataPedido.setCodtipopago("O");
			pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dCtxPago);
		}
	}	
}
