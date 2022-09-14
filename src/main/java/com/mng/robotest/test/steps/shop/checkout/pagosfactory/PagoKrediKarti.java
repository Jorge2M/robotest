package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.datastored.DataPedido;
import com.mng.robotest.test.steps.shop.checkout.SecKrediKartiSteps;

public class PagoKrediKarti extends PagoSteps {
	
	public PagoKrediKarti(DataPago dataPago) throws Exception {
		super(dataPago);
		super.isAvailableExecPay = true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void startPayment(boolean execPay) throws Exception {
		DataPedido dataPedido = dataPago.getDataPedido();
		pageCheckoutWrapperSteps.fluxSelectEnvioAndClickPaymentMethod(dataPago, dataTest.pais);
		
		SecKrediKartiSteps secKrediKartiSteps = pageCheckoutWrapperSteps.getSecKrediKartiSteps();
		secKrediKartiSteps.inputNumTarjeta(dataPedido.getPago().getNumtarj());
		secKrediKartiSteps.clickOpcionPagoAPlazo(1);
		
		if (execPay) {
			dataPedido.setCodtipopago("O");
			pageCheckoutWrapperSteps.inputDataTrjAndConfirmPago(dataPago);
		}
	}	
}
